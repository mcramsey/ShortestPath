package org.marshallramsey.shortestpath;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    private static final String WORD_FILE_NAME = "words.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        findShortestPath("write", "visas");

        return true;
    }

    private void findShortestPath(String startValue, String endValue) {
        StringEvaluator evaluator = new StringEvaluator();
        PathProvider<String> pathProvider = new WeightedPathProvider<String>(evaluator);
        Dictionary dictionary = new Dictionary();
        try {
            InputStream wordStream = getAssets().open(WORD_FILE_NAME);
            dictionary.load(wordStream);
            ChildCreator<Node<String>> childCreator = new StringChildCreator(dictionary);
            PathFinder<String> pathFinder = new PathFinder<String>(pathProvider, childCreator);
            Log.d(TAG, "start: " + startValue);
            Log.d(TAG, "end: " + endValue);
            evaluator.setTarget(endValue);
            List<String> path = pathFinder.getShortestPath(startValue, endValue);
            StringBuilder builder = new StringBuilder("[");
            boolean isFirstElement = true;
            for (String element : path) {
                if (!isFirstElement) {
                    builder.append(", ");
                } else {
                    isFirstElement = false;
                }
                builder.append(element);
            }
            builder.append("]");
            Log.d(TAG, "path: " + builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
