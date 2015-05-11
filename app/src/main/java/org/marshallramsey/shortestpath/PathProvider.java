package org.marshallramsey.shortestpath;

import java.util.List;

/**
 * Created by marshallramsey on 5/10/15.
 */
public interface PathProvider<T> {

    public void addPath(List<Node<T>> path);

    public void addPaths(List<List<Node<T>>> paths);

    public List<Node<T>> getNextPath();

    public boolean isEmpty();

}
