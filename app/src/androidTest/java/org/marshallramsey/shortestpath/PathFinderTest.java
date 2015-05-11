package org.marshallramsey.shortestpath;

import android.test.AndroidTestCase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by marshallramsey on 5/10/15.
 */
public class PathFinderTest extends AndroidTestCase {

    private static final String[] TEST_1_WORDS_SET = {"aaaaa", "abaaa", "abcaa", "abcda", "abcde"};
    private static final String[] TEST_2_WORDS_SET = {"aaaaa", "abaaa", "abcaa", "zbcaa", "zbcda", "zbcde", "abcda", "abcde"};

    private PathProvider<String> mPathProvider;
    private Dictionary mDictionary;
    private ChildCreator<Node<String>> mChildCreator;
    private StringEvaluator mEvaluator;

    @Override
    public void setUp() {
        mEvaluator = new StringEvaluator();
        mPathProvider = new WeightedPathProvider<String>(mEvaluator);
        mDictionary = new Dictionary();
        mChildCreator = new StringChildCreator(mDictionary);
    }

    public void testPathComparison() {
        List<String> testPath = new ArrayList<String>();
        List<String> testPath2 = Arrays.asList("aaaaa", "abaaa", "abcaa", "abcda", "abcde");
        assertFalse(testPath2.equals(testPath));

        testPath = Arrays.asList("bbbbb", "ccccc", "ddddd", "eeeee", "fffff");
        assertFalse(testPath2.equals(testPath));
    }

    public void test1PathFinder() {
        List<String> test1ExpectedPath = Arrays.asList("aaaaa", "abaaa", "abcaa", "abcda", "abcde");
        verifyPath("aaaaa", "abcde", TEST_1_WORDS_SET, test1ExpectedPath);
    }

    public void test2PathFinder() {
        List<String> test2ExpectedPath = Arrays.asList("aaaaa", "abaaa", "abcaa", "abcda", "abcde");
        verifyPath("aaaaa", "abcde", TEST_2_WORDS_SET, test2ExpectedPath);
    }

    private void verifyPath(String startValue, String endValue, String[] wordSet, List<String> expectedPath) {
        mDictionary.setWords(wordSet);
        mEvaluator.setTarget(endValue);
        PathFinder<String> pathFinder = new PathFinder<String>(mPathProvider, mChildCreator);
        assertEquals(expectedPath, pathFinder.getShortestPath(startValue, endValue));
    }
}
