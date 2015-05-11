package org.marshallramsey.shortestpath;

import junit.framework.TestCase;

/**
 * Created by marshallramsey on 5/9/15.
 */
public class StringEvaluatorTest extends TestCase {


    public void testStringEvaluator() {
        StringEvaluator evaluator;
        evaluator = new StringEvaluator();
        evaluator.setTarget("abcde");

        assertEquals(1.0, evaluator.getScore("abcde"), 0.0);

        assertEquals(5D / 6D, evaluator.getScore("abcdef"), 0.0);

        assertEquals(0.8, evaluator.getScore("xbcde"), 0.0);
        assertEquals(0.8, evaluator.getScore("axcde"), 0.0);
        assertEquals(0.8, evaluator.getScore("abxde"), 0.0);
        assertEquals(0.8, evaluator.getScore("abcxe"), 0.0);
        assertEquals(0.8, evaluator.getScore("abcdx"), 0.0);

        assertEquals(0.6, evaluator.getScore("xycde"), 0.0);
        assertEquals(0.6, evaluator.getScore("yxcde"), 0.0);
        assertEquals(0.6, evaluator.getScore("abxye"), 0.0);
        assertEquals(0.6, evaluator.getScore("abcxy"), 0.0);
        assertEquals(0.6, evaluator.getScore("abydx"), 0.0);

        assertEquals(0.4, evaluator.getScore("xywde"), 0.0);
        assertEquals(0.4, evaluator.getScore("yxcdw"), 0.0);
        assertEquals(0.4, evaluator.getScore("awxye"), 0.0);
        assertEquals(0.4, evaluator.getScore("wbcxy"), 0.0);
        assertEquals(0.4, evaluator.getScore("abywx"), 0.0);

        assertEquals(0.2, evaluator.getScore("wxyze"), 0.0);

        assertEquals(0.0, evaluator.getScore(""), 0.0);
        assertEquals(0.0, evaluator.getScore("vwxyz"), 0.0);
        assertEquals(0.0, evaluator.getScore("uvwxyz"), 0.0);
        assertEquals(0.0, evaluator.getScore("xyz"), 0.0);
        assertEquals(0.0, evaluator.getScore("z"), 0.0);
    }

}
