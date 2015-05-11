package org.marshallramsey.shortestpath;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marshallramsey on 5/10/15.
 */
public class PathFinder<T> {

    private static final String TAG = "PathFinder";

    private ChildCreator<Node<T>> mChildCreator;
    private PathProvider<T> mPathProvider;
    private HashMap<T, Integer> mVisitedNodeValues;

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
        mVisitedNodeValues = new HashMap<T, Integer>();
        List<Node<T>> foundPath = null;

        List<Node<T>> curPath = new ArrayList<Node<T>>();
        curPath.add(new Node<T>(startValue, mChildCreator));
        mPathProvider.addPath(curPath);

        while (!mPathProvider.isEmpty()) {
            curPath = mPathProvider.getNextPath();
            // prune long branches that cannot be shortest found path
            if (foundPath != null && foundPath.size() <= curPath.size()) {
                // skip partial paths that are equal-to or longer than found shortest path
                continue;
            }
            Node<T> lastNode = curPath.get(curPath.size()-1);
            if (lastNode.hasValue(endValue)) {
                foundPath = curPath;
            } else {
                // Skip previously visited children that were visted with shorter paths.
                Integer shortestPathToNode = mVisitedNodeValues.get(lastNode.getValue());
                if (shortestPathToNode != null && shortestPathToNode.intValue() <= curPath.size()) {
                    // already found a shorter path to this node so skip
                    continue;
                }
                mVisitedNodeValues.put(lastNode.getValue(), curPath.size());

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

        return foundPath != null ? adaptPath(foundPath) : null;
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
