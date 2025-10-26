package algo_analysis.algorithms;

import algo_analysis.dto.AlgorithmResult;
import algo_analysis.dto.EdgeInfo;
import algo_analysis.entity.Edge;
import algo_analysis.entity.Graph;
import algo_analysis.entity.Vertex;
import algo_analysis.util.PerformanceMetrics;

import java.util.*;

/**
 * Kruskal's Algorithm for finding Minimum Spanning Tree
 * Time Complexity: O(E log E) for sorting + O(E * α(V)) for Union-Find
 * Overall: O(E log E)
 */
public class KruskalAlgorithm {

    /**
     * Find MST using Kruskal's Algo with performance tracking
     */
    public static <T> AlgorithmResult findMST(Graph<T> graph) {
        AlgorithmResult result = new AlgorithmResult();
        PerformanceMetrics metrics = new PerformanceMetrics();

        if (graph == null || graph.getVertexCount() == 0) {
            result.operationsCount = 0;
            result.executionTimeMs = 0.0;
            result.totalCost = 0.0;
            return result;
        }

        metrics.start();

        // sort all edges by weight
        List<Edge<T>> sortedEdges = new ArrayList<>(graph.getAllEdges());
        sortedEdges.sort(Comparator.comparingDouble(Edge::getWeight));
        metrics.incrementOperations(sortedEdges.size());

        // unionfind for all vertices
        UnionFind<T> uf = new UnionFind<>();
        for (Vertex<T> vertex : graph.getAllVertices()) {
            uf.makeSet(vertex);
            metrics.incrementOperations();
        }

        // process edged sorted
        List<Edge<T>> mstEdges = new ArrayList<>();
        double totalCost = 0.0;

        for (Edge<T> edge : sortedEdges) {
            metrics.incrementOperations();

            Vertex<T> source = edge.getSource();
            Vertex<T> target = edge.getTarget();

            // check if cycle
            Vertex<T> root1 = uf.find(source, metrics);
            Vertex<T> root2 = uf.find(target, metrics);

            if (!root1.equals(root2)) {
                mstEdges.add(edge);
                totalCost += edge.getWeight();

                // union
                uf.union(source, target, metrics);

                if (mstEdges.size() == graph.getVertexCount() - 1) {
                    break;
                }
            }
        }

        metrics.stop();

        for (Edge<T> edge : mstEdges) {
            result.mstEdges.add(new EdgeInfo(
                    edge.getSource().getData().toString(),
                    edge.getTarget().getData().toString(),
                    edge.getWeight()
            ));
        }

        result.totalCost = totalCost;
        result.operationsCount = metrics.getOperationsCount();
        result.executionTimeMs = metrics.getExecutionTimeMs();

        return result;
    }

    // find MST and return actual edges;
    public static <T> List<Edge<T>> findMSTEdges(Graph<T> graph) {
        List<Edge<T>> mstEdges = new ArrayList<>();

        if (graph == null || graph.getVertexCount() == 0) {
            return mstEdges;
        }

        // sort
        List<Edge<T>> sortedEdges = new ArrayList<>(graph.getAllEdges());
        sortedEdges.sort(Comparator.comparingDouble(Edge::getWeight));

        // union find
        UnionFind<T> uf = new UnionFind<>();
        for (Vertex<T> vertex : graph.getAllVertices()) {
            uf.makeSet(vertex);
        }

        for (Edge<T> edge : sortedEdges) {
            Vertex<T> source = edge.getSource();
            Vertex<T> target = edge.getTarget();

            if (!uf.connected(source, target)) {
                mstEdges.add(edge);
                uf.union(source, target);

                if (mstEdges.size() == graph.getVertexCount() - 1) {
                    break;
                }
            }
        }

        return mstEdges;
    }
}