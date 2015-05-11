package org.marshallramsey.shortestpath;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marshallramsey on 5/10/15.
 */
public class WeightedPathProviderTest extends AndroidTestCase {

    @Override
    public void setUp() {

    }

    public void testWeightedPathProvider() {
        StringEvaluator evaluator = new StringEvaluator();
        evaluator.setTarget("mmmmm");
        WeightedPathProvider<String> pathProvider = new WeightedPathProvider<String>(evaluator);
        List<Node<String>> path;
        path = createPath(new String[]{"abcde", "mabcd"});
        pathProvider.addPath(path);
        path = createPath(new String[]{"mmbmm"});
        pathProvider.addPath(path);
        path = createPath(new String[]{"abcde", "mmbcm"});
        pathProvider.addPath(path);
        path = createPath(new String[]{"abcde", "mabcm"});
        pathProvider.addPath(path);
        path = createPath(new String[]{"mmbmm"});
        pathProvider.addPath(path);

        assertFalse(pathProvider.isEmpty());

        path = pathProvider.getNextPath();
        assertEquals(path.get(path.size() - 1).getValue(), "mmbmm");
        assertFalse(pathProvider.isEmpty());

        path = pathProvider.getNextPath();
        assertEquals(path.get(path.size() - 1).getValue(), "mmbmm");
        assertFalse(pathProvider.isEmpty());

        path = pathProvider.getNextPath();
        assertEquals(path.get(path.size() - 1).getValue(), "mmbcm");
        assertFalse(pathProvider.isEmpty());

        path = pathProvider.getNextPath();
        assertEquals(path.get(path.size() - 1).getValue(), "mabcm");
        assertFalse(pathProvider.isEmpty());

        path = pathProvider.getNextPath();
        assertEquals(path.get(path.size() - 1).getValue(), "mabcd");
        assertTrue(pathProvider.isEmpty());
    }

    private List<Node<String>> createPath(String[] values) {
        ArrayList<Node<String>> path = new ArrayList<Node<String>>();
        for (String value : values) {
            path.add(new Node<String>(value, null));
        }
        return path;
    }

}
