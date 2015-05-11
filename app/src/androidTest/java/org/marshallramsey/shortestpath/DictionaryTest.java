package org.marshallramsey.shortestpath;

import android.test.AndroidTestCase;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by marshallramsey on 5/9/15.
 */
public class DictionaryTest extends AndroidTestCase {

    private static String WORD_FILE_NAME = "words.txt";
    private static int EXPECTED_DIC_ENTRY_COUNT = 9755;

    private Dictionary mDictionary;

    @Override
    protected void setUp() {
        mDictionary = new Dictionary();
        try {
            InputStream wordStream = getContext().getAssets().open(WORD_FILE_NAME);
            mDictionary.load(wordStream);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    public void testDictionary() {
        assertEquals(EXPECTED_DIC_ENTRY_COUNT, mDictionary.getCount());

        assertTrue(mDictionary.isWord("aahed")); // first entry in dic
        assertTrue(mDictionary.isWord("libya")); // middle of dic
        assertTrue(mDictionary.isWord("zymic")); // last entry in dic

        assertFalse(mDictionary.isWord(null));
        assertFalse(mDictionary.isWord(""));
        assertFalse(mDictionary.isWord("abcde")); // not a word
        assertFalse(mDictionary.isWord("libyas")); // too long
        assertFalse(mDictionary.isWord("abcdefghijklmnopqrstuvwxyz")); // too long
    }

}
