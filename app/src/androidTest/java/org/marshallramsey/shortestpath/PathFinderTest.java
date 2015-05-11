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

    private static String WORD_FILE_NAME = "words.txt";
    private static final String[] TEST_1_WORDS_SET = {"aaaaa", "abaaa", "vwxyz", "abcaa", "abcda", "abcde"};
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

    public void testValidPathSet1() {
        List<String> testExpectedPath = Arrays.asList("aaaaa", "abaaa", "abcaa", "abcda", "abcde");
        verifyPath("aaaaa", "abcde", TEST_1_WORDS_SET, testExpectedPath);
    }

    public void testValidPathSet2() {
        List<String> testExpectedPath = Arrays.asList("aaaaa", "abaaa", "abcaa", "abcda", "abcde");
        verifyPath("aaaaa", "abcde", TEST_2_WORDS_SET, testExpectedPath);
    }

    public void testInvalidPath() {
        List<String> testExpectedPath = null;
        verifyPath("aaaaa", "vwxyz", TEST_1_WORDS_SET, testExpectedPath);
    }

    public void testInvalidPathTargetNotWord() {
        List<String> testExpectedPath = null;
        verifyPath("aaaaa", "heady", TEST_1_WORDS_SET, testExpectedPath);
    }

    public void testInvalidPathStartNotWord() {
        List<String> testExpectedPath = null;
        verifyPath("vvvvv", "abcde", TEST_1_WORDS_SET, testExpectedPath);
    }


    public void testInvalidPathBothNotWord() {
        List<String> testExpectedPath = null;
        verifyPath("vvvvv", "heady", TEST_1_WORDS_SET, testExpectedPath);
    }

    public void testInvalidPathEmptyStart() {
        List<String> testExpectedPath = null;
        verifyPath("", "heady", TEST_1_WORDS_SET, testExpectedPath);
    }

    public void testInvalidPathEmptyEnd() {
        List<String> testExpectedPath = null;
        verifyPath("aaaaa", "", TEST_1_WORDS_SET, testExpectedPath);
    }

    public void testBothEmpty() {
        List<String> testExpectedPath = Arrays.asList("");
        verifyPath("", "", TEST_1_WORDS_SET, testExpectedPath);
    }

    public void testLargeDataSet() {
        try {
            InputStream wordStream = getContext().getAssets().open(WORD_FILE_NAME);
            mDictionary.load(wordStream);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        verifyPath("write", "visas", null, Arrays.asList("write", "writs", "waits", "wasts", "vasts", "vases", "vises", "visas"));
    }

    public void testDepthFirstOrder() {
        List<String> testExpectedPath = Arrays.asList("aaaaa", "abaaa", "abcaa", "abcda", "abcde");
        mPathProvider = new DepthFirstPathProvider<String>();
        verifyPath("aaaaa", "abcde", TEST_1_WORDS_SET, testExpectedPath);
    }

    public void testBreadthFirstOrder() {
        List<String> testExpectedPath = Arrays.asList("aaaaa", "abaaa", "abcaa", "abcda", "abcde");
        mPathProvider = new BreadthFirstPathProvider<String>();
        verifyPath("aaaaa", "abcde", TEST_1_WORDS_SET, testExpectedPath);
    }

    private void verifyPath(String startValue, String endValue, String[] wordSet, List<String> expectedPath) {
        if (wordSet != null) {
            mDictionary.setWords(wordSet);
        }
        mEvaluator.setTarget(endValue);
        PathFinder<String> pathFinder = new PathFinder<String>(mPathProvider, mChildCreator);
        assertEquals(expectedPath, pathFinder.getShortestPath(startValue, endValue));
    }
}
