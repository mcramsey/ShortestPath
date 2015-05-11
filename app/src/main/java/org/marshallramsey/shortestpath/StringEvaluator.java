package org.marshallramsey.shortestpath;

/**
 * Created by marshallramsey on 5/9/15.
 */
public class StringEvaluator implements Evaluator<String> {

    protected String mTarget;

    @Override
    public void setTarget(String target) {
        mTarget = target;
    }

    @Override
    public double getScore(String candidate) {
        int minLen = Math.min(mTarget.length(), candidate.length());
        int mMatchingChars = 0;
        int mNonMatchingChars = Math.abs(mTarget.length() - candidate.length());
        for (int i=0; i < minLen; i++) {
            if (candidate.charAt(i) == mTarget.charAt(i)) {
                ++mMatchingChars;
            }
        }
        mNonMatchingChars += minLen - mMatchingChars;
        return (double)mMatchingChars / (double)(mMatchingChars + mNonMatchingChars);
    }

    @Override
    public boolean isMatch(String candidate) {
        return candidate.equals(mTarget);
    }

}
