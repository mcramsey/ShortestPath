package org.marshallramsey.shortestpath;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by marshallramsey on 5/10/15.
 */
public class WeightedPathProvider<T> implements PathProvider<T> {

    private Evaluator<T> mEvaluator;
    private LinkedList<List<Node<T>>> mPaths;

    public WeightedPathProvider(Evaluator<T> evaluator) {
        mEvaluator = evaluator;
        mPaths = new LinkedList<List<Node<T>>>();
    }

    @Override
    public void addPath(List<Node<T>> path) {
        int insertPos = 0;
        for (List<Node<T>> curPath : mPaths) {
            if (getScore(curPath) < getScore(path)) {
                break;
            }
            insertPos++;
        }
        mPaths.add(insertPos, path);
    }

    public void addPaths(List<List<Node<T>>> paths) {
        for (List<Node<T>> path : paths) {
            addPath(path);
        }
    }

    private double getScore(List<Node<T>> path) {
        return mEvaluator.getScore(path.get(path.size()-1).getValue());
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
