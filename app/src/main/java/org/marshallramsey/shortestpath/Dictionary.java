package org.marshallramsey.shortestpath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * Created by marshallramsey on 5/9/15.
 */
public class Dictionary {

    protected HashSet<String> mWordSet;

    public Dictionary() {
        mWordSet = new HashSet<String>();
    }

    public void load(InputStream dicInputStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(dicInputStream);
        BufferedReader bufReader = new BufferedReader(reader);
        String line = bufReader.readLine();
        while (line != null) {
            mWordSet.add(line);
            line = bufReader.readLine();
        }
    }

    public void setWords(String[] words) {
        mWordSet.clear();
        for (String word : words) {
            mWordSet.add(word);
        }
    }

    public boolean isWord(String potentialWord) {
        return mWordSet.contains(potentialWord);
    }


    public int getCount() {
        return mWordSet.size();
    }
}
