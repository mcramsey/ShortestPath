package org.marshallramsey.shortestpath;

import java.util.List;

/**
 * Created by marshallramsey on 5/10/15.
 */
public interface ChildCreator<T> {

    public List<T> getChildren(T parent);

}
