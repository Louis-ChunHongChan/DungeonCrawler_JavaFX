package sample;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph<T> {

    private Map<T, List<T>> g;

    private Graph(Map<T, List<T>> graph) {
        g = new HashMap<>(graph);
    }

    public int shortestPathLength(T start, T end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and/or end cannot be null");
        }
        if (!g.containsKey(start) || !g.containsKey(end)) {
            throw new IllegalArgumentException("Graph must contain start and end nodes");
        }
        if (start == end) {
            return 0;
        }
        HashSet<T> visited = new HashSet<>(g.size());
        ArrayDeque<Pair<T, Integer>> q = new ArrayDeque<>();
        q.add(new Pair<>(start, 0));
        while (!q.isEmpty()) {
            Pair<T, Integer> p = q.poll();
            if (p.k.equals(end)) {
                return p.v;
            }
            if (visited.contains(p.k)) {
                continue;
            }
            visited.add(p.k);
            for (T neighbor : this.getConnections(p.k)) {
                if (visited.contains(neighbor)) {
                    continue;
                }
                q.add(new Pair<>(neighbor, p.v + 1));
            }
        }
        return -1;
    }
    public boolean isConnected() {
        HashSet<T> visited = new HashSet<>(g.size());
        ArrayDeque<T> q = new ArrayDeque<>();
        q.add(g.keySet().iterator().next());
        while (!q.isEmpty()) {
            T n = q.poll();
            if (visited.contains(n)) {
                continue;
            }
            visited.add(n);
            for (T nn : g.get(n)) {
                if (!visited.contains(nn)) {
                    q.add(nn);
                }
            }
        }
        return visited.size() == g.size();
    }
    public boolean hasLoops() {
        for (T n : g.keySet()) {
            for (T m : g.get(n)) {
                if (n == m) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getSize() {
        return g.size();
    }
    public List<T> getConnections(T node) {
        return new LinkedList<>(g.get(node));
    }
    public Set<T> getNodes() {
        return g.keySet();
    }
    public boolean containsNode(T node) {
        return node != null && g.containsKey(node);
    }

    public static <T> Graph<T> makeRandomGraph(Map<T, Integer> nodeConnections,
                                               boolean allowLoops) {
        if (!Graph.validateCandidate(nodeConnections)) {
            return null;
        }
        Graph<T> g;
        do {
            g = Graph.makeRandomGraph(nodeConnections);
        } while (!g.isConnected() || (!allowLoops && g.hasLoops()));
        return g;
    }
    private static <T> Graph<T> makeRandomGraph(Map<T, Integer> nodeConnections) {
        ArrayList<T> pool = new ArrayList<>(nodeConnections.size());
        Map<T, List<T>> g = new HashMap<>();
        for (Map.Entry<T, Integer> entry : nodeConnections.entrySet()) {
            g.put(entry.getKey(), new LinkedList<>());
            for (int i = 0, l = entry.getValue(); i < l; i++) {
                pool.add(entry.getKey());
            }
        }
        while (!pool.isEmpty()) {
            T a = pool.remove((int) (Math.random() * pool.size()));
            T b = pool.remove((int) (Math.random() * pool.size()));
            g.get(a).add(b);
            if (a != b) {
                g.get(b).add(a);
            }
        }
        return new Graph<>(g);
    }
    public static <T> boolean validateCandidate(Map<T, Integer> nodeConnections) {
        if (nodeConnections == null) {
            return false;
        }
        int totalConnections = 0;
        int leaves = 0;
        int hubs = 0;
        for (int i : nodeConnections.values()) {
            totalConnections += i;
            if (i < 0) {
                return false;
            } else if (i == 0) {
                return false;
            } else if (i == 1) {
                leaves++;
            } else if (i > 2) {
                hubs++;
            }
        }
        if (totalConnections % 2 == 1) {
            return false;
        }
        if (leaves - 2 > hubs) {
            return false;
        }
        return true;
    }

    private static class Pair<K, V> {
        private K k;
        private V v;

        public Pair(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }

}
