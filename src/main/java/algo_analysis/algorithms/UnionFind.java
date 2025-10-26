package algo_analysis.algorithms;

import algo_analysis.entity.Vertex;
import algo_analysis.util.PerformanceMetrics;

import java.util.HashMap;
import java.util.Map;

/**
 * Union-Find (Disjoint Set Union)
 * Used in Kruskal's Algo to detect cycles
 * Implements path compression and union by rank for optimal performance
 */
public class UnionFind<T> {
    private Map<Vertex<T>, Vertex<T>> parent;
    private Map<Vertex<T>, Integer> rank;

    public UnionFind() {
        this.parent = new HashMap<>();
        this.rank = new HashMap<>();
    }

    public void makeSet(Vertex<T> vertex) {
        parent.put(vertex, vertex);
        rank.put(vertex, 0);
    }

    public Vertex<T> find(Vertex<T> vertex, PerformanceMetrics metrics) {
        if (metrics != null) {
            metrics.incrementOperations();
        }

        if (!parent.get(vertex).equals(vertex)) {
            parent.put(vertex, find(parent.get(vertex), metrics));
        }
        return parent.get(vertex);
    }

//    find without metrics
    public Vertex<T> find(Vertex<T> vertex) {
        return find(vertex, null);
    }

    public boolean union(Vertex<T> v1, Vertex<T> v2, PerformanceMetrics metrics) {
        if (metrics != null) {
            metrics.incrementOperations(); // Count union operation
        }

        Vertex<T> root1 = find(v1, metrics);
        Vertex<T> root2 = find(v2, metrics);

        if (root1.equals(root2)) {
            return false;
        }

        int rank1 = rank.get(root1);
        int rank2 = rank.get(root2);

        if (rank1 < rank2) {
            parent.put(root1, root2);
        } else if (rank1 > rank2) {
            parent.put(root2, root1);
        } else {
            parent.put(root2, root1);
            rank.put(root1, rank1 + 1);
        }

        return true;
    }

//    union without metrics
    public boolean union(Vertex<T> v1, Vertex<T> v2) {
        return union(v1, v2, null);
    }

//    check if two vertex in same set
    public boolean connected(Vertex<T> v1, Vertex<T> v2) {
        return find(v1).equals(find(v2));
    }
}