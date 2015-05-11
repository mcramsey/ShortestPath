package org.marshallramsey.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by marshallramsey on 5/10/15.
 */
public class PathFinder<T> {

    private ChildCreator<Node<T>> mChildCreator;
    private PathProvider<T> mPathProvider;

    public PathFinder(PathProvider<T> pathProvider, ChildCreator<Node<T>> childCreator) {
        mPathProvider = pathProvider;
        mChildCreator = childCreator;
    }

    /**
     * Finds the shortest path between startValue and endValue.
     * @param startValue
     * @param endValue
     * @return shortest path or null if no path found
     */
    public List<T> getShortestPath(T startValue, T endValue) {
        List<List<Node<T>>> foundPaths = new ArrayList<List<Node<T>>>();

        List<Node<T>> curPath = new ArrayList<Node<T>>();
        curPath.add(new Node<T>(startValue, mChildCreator));
        mPathProvider.addPath(curPath);

        while (!mPathProvider.isEmpty()) {
            curPath = mPathProvider.getNextPath();
            Node<T> lastNode = curPath.get(curPath.size()-1);
            if (lastNode.hasValue(endValue)) {
                foundPaths.add(curPath);
            } else {
                List<Node<T>> children = lastNode.getChildren();
                // Remove circular links
                List<Node<T>> childList = new ArrayList<Node<T>>();
                for (Node<T> child : children) {
                    childList.add(child);
                }
                for (Node<T> child : childList) {
                    if (curPath.contains(child)) {
                        children.remove(child);
                    }
                }

                List<List<Node<T>>> childPaths = makeChildPaths(curPath, children);
                mPathProvider.addPaths(childPaths);
            }
        }

        List<Node<T>> minLengthPath = getMinLengthPath(foundPaths);
        return minLengthPath != null ? adaptPath(minLengthPath) : null;
    }

    private List<Node<T>> getMinLengthPath(List<List<Node<T>>> paths) {
        List<Node<T>> minLengthPath = null;
        int minLength = Integer.MAX_VALUE;
        for (List<Node<T>> path : paths) {
            if (path.size() < minLength) {
                minLengthPath = path;
                minLength = minLengthPath.size();
            }
        }
        return minLengthPath;
    }

    private List<List<Node<T>>> makeChildPaths(List<Node<T>> curPath, List<Node<T>> children) {
        ArrayList<List<Node<T>>> childPaths = new ArrayList<List<Node<T>>>(children.size());
        for (Node<T> child : children) {
            // only add child if it doesn't create a circular path
            if (!curPath.contains(child)) {
                List<Node<T>> path = new ArrayList<Node<T>>(curPath.size() + 1);
                for (Node<T> pathNode : curPath) {
                    path.add(pathNode);
                }
                path.add(child);
                childPaths.add(path);
            }
        }
        return childPaths;
    }

    private List<T> adaptPath(List<Node<T>> path) {
        List<T> adaptedPath = new ArrayList<T>(path.size());
        for (Node<T> node : path) {
            adaptedPath.add(node.getValue());
        }
        return adaptedPath;
    }
}
