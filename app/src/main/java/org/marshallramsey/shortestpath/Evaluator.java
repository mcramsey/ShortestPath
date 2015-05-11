package org.marshallramsey.shortestpath;

/**
 * Created by marshallramsey on 5/9/15.
 */
public interface Evaluator<T> {

    public void setTarget(T target);

    /**
     * Compares the candidate to the target and returns a score. Zero means the candidate is
     * completely different. One means they are exactly the same.
     * @param candidate
     * @return score between 0 and 1, inclusive.
     */
    public double getScore(T candidate);

    /**
     * Checks if the candidate is the same as the target.
     * @param candidate
     * @return true if the candidate is the same as the target, otherwise false.
     */
    public boolean isMatch(T candidate);

}
