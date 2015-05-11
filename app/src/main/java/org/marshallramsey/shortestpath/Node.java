package org.marshallramsey.shortestpath;

import java.util.List;

/**
 * Created by marshallramsey on 5/10/15.
 */
public class Node<T> {

    private T mValue;
    private ChildCreator<Node<T>> mChildCreator;

    public Node(T value, ChildCreator<Node<T>> childCreator) {
        mValue = value;
        mChildCreator = childCreator;
    }

    public T getValue() {
        return mValue;
    }

    public List<Node<T>> getChildren() {
        return mChildCreator.getChildren(this);
    }

    public boolean hasValue(T value) {
        return mValue.equals(value);
    }

    @Override
    public boolean equals(Object node) {
        return mValue.equals(((Node<T>)node).getValue());
    }

    @Override
    public String toString() {
        return mValue.toString();
    }
}
