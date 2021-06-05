import org.junit.Test;
import sample.Graph;
import sample.Maze;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GraphTest {

    @Test
    public void noLoops() {
        Graph<Integer> g = makeGraph(16);
        for (Integer i : g.getNodes()) {
            for (Integer j : g.getConnections(i)) {
                assertFalse(i.equals(j));
            }
        }
    }

    @Test
    public void fourRegular() {
        Map<Integer, Integer> connections = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            connections.put(i, 4);
        }
        Graph<Integer> g = Graph.makeRandomGraph(connections, false);
    }

    @Test
    public void connected() {
        Graph<Integer> g = makeGraph(100);
        boolean[] visited = new boolean[g.getSize()];
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(0);
        while (!q.isEmpty()) {
            int j = q.poll();
            if (visited[j]) {
                continue;
            }
            visited[j] = true;
            for (int n : g.getConnections(j)) {
                if (!visited[n]) {
                    q.add(n);
                }
            }
        }
        for (boolean b : visited) {
            assertTrue(b);
        }
    }

    @Test
    public void largeEnough() {
        Graph<Integer> g;
        int counter = 0;
        do {
            g = makeGraph(16);
            counter++;
        } while (g.shortestPathLength(0, 1) < Maze.MIN_DISTANCE);
        System.out.println(counter + " tries");
    }

    private static Graph<Integer> makeGraph(int size) {
        Map<Integer, Integer> m = new HashMap<>();
        int t;
        do {
            m.clear();
            t = 0;
            for (int i = 0; i < size; i++) {
                int c = (int) (Math.random() * 3) + 2;
                t += c;
                m.put(i, c);
            }
        } while (t % 2 == 1);
        Graph<Integer> g;
        do {
            g = Graph.makeRandomGraph(m, false);
        } while (g == null);
        assertEquals(m.size(), g.getSize());
        return g;
    }

}
