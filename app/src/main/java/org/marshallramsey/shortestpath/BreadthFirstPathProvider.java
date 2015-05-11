package org.marshallramsey.shortestpath;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by marshallramsey on 5/10/15.
 */
public class BreadthFirstPathProvider<T> implements PathProvider<T> {

    private LinkedList<List<Node<T>>> mPaths;

    public BreadthFirstPathProvider() {
        mPaths = new LinkedList<List<Node<T>>>();
    }

    @Override
    public void addPath(List<Node<T>> path) {
        mPaths.add(path);
    }

    public void addPaths(List<List<Node<T>>> paths) {
        mPaths.addAll(paths);
    }

    @Override
    public List<Node<T>> getNextPath() {
        return mPaths.poll();
    }

    @Override
    public boolean isEmpty() {
        return mPaths.isEmpty();
    }
}
