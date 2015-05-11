package org.marshallramsey.shortestpath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marshallramsey on 5/10/15.
 */
public class StringChildCreator implements ChildCreator<Node<String>> {

    private Dictionary mDictionary;
    private char[] mPossibleChars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public StringChildCreator(Dictionary dictionary) {
        mDictionary = dictionary;
    }

    @Override
    public List<Node<String>> getChildren(Node<String> parent) {
        ArrayList<Node<String>> childNodes = new ArrayList<Node<String>>();

        char[] originalVal = parent.getValue().toCharArray();

        for (int i = 0; i < originalVal.length; i++) {
            char originalChar = originalVal[i];
            for (char replacementChar : mPossibleChars) {
                // Ensure the same word isn't created by skipping its char at the cur position.
                if (replacementChar != originalChar) {
                    originalVal[i] = replacementChar;
                    String candidate = String.valueOf(originalVal);
                    if (mDictionary.isWord(candidate)) {
                        childNodes.add(new Node<String>(candidate, this));
                    }
                }
            }
            originalVal[i] = originalChar;
        }

        return childNodes;
    }
}
