package org.marshallramsey.shortestpath;

import android.test.AndroidTestCase;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by marshallramsey on 5/10/15.
 */
public class StringChildCreatorTest extends AndroidTestCase {

    private static String WORD_FILE_NAME = "words.txt";

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

    public void testStringChildCreator() {
        Node<String> parent;
        List<Node<String>> children;

        // No children
        parent = new Node<String>("zooid", new StringChildCreator(mDictionary));
        children = parent.getChildren();
        assertEquals(0, children.size());

        // Only one child
        parent = new Node<String>("liard", new StringChildCreator(mDictionary));
        children = parent.getChildren();
        assertEquals(1, children.size());
        assertTrue(children.get(0).hasValue("liars"));

        // Several children
        parent = new Node<String>("level", new StringChildCreator(mDictionary));
        children = parent.getChildren();
        assertEquals(7, children.size());
        assertTrue(children.get(3).hasValue("levee"));

        parent = new Node<String>("lilly", new StringChildCreator(mDictionary));
        children = parent.getChildren();
        assertEquals(8, children.size());
    }

}
