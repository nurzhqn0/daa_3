package algo_analysis.algorithms;

import algo_analysis.dto.AlgorithmResult;
import algo_analysis.dto.EdgeInfo;
import algo_analysis.entity.Edge;
import algo_analysis.entity.Graph;
import algo_analysis.entity.Vertex;
import algo_analysis.util.PerformanceMetrics;

import java.util.*;

/**
 * Prim's Algorithm for finding MST
 * Time Complexity: O(E * V) with this implementation
 */
public class PrimAlgorithm {

    /**
     * Find MST using Prim's algorithm with performance tracking
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

        Set<Vertex<T>> visited = new HashSet<>();
        List<Edge<T>> mstEdges = new ArrayList<>();
        double totalCost = 0.0;

        Vertex<T> startVertex = graph.getAllVertices().iterator().next();
        visited.add(startVertex);
        metrics.incrementOperations();

        while (visited.size() < graph.getVertexCount()) {
            Edge<T> minEdge = null;
            double minWeight = Double.MAX_VALUE;

            for (Edge<T> edge : graph.getAllEdges()) {
                metrics.incrementOperations(); // Count edge examination

                Vertex<T> source = edge.getSource();
                Vertex<T> target = edge.getTarget();

                boolean sourceVisited = visited.contains(source);
                boolean targetVisited = visited.contains(target);

                if (sourceVisited != targetVisited) {
                    metrics.incrementOperations(); // Count comparison

                    if (edge.getWeight() < minWeight) {
                        minWeight = edge.getWeight();
                        minEdge = edge;
                    }
                }
            }

            // add minimum edge to MST
            if (minEdge != null) {
                mstEdges.add(minEdge);
                totalCost += minEdge.getWeight();

                // add new vertex to visited set
                Vertex<T> newVertex = visited.contains(minEdge.getSource())
                        ? minEdge.getTarget()
                        : minEdge.getSource();

                visited.add(newVertex);
                metrics.incrementOperations();
            } else {
                break;
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

    // find MST and return the actual edges
    public static <T> List<Edge<T>> findMSTEdges(Graph<T> graph) {
        List<Edge<T>> mstEdges = new ArrayList<>();

        if (graph == null || graph.getVertexCount() == 0) {
            return mstEdges;
        }

        Set<Vertex<T>> visited = new HashSet<>();

        Vertex<T> startVertex = graph.getAllVertices().iterator().next();
        visited.add(startVertex);

        while (visited.size() < graph.getVertexCount()) {
            Edge<T> minEdge = null;
            double minWeight = Double.MAX_VALUE;

            // find minimum edge
            for (Edge<T> edge : graph.getAllEdges()) {
                Vertex<T> source = edge.getSource();
                Vertex<T> target = edge.getTarget();

                boolean sourceVisited = visited.contains(source);
                boolean targetVisited = visited.contains(target);

                if (sourceVisited != targetVisited && edge.getWeight() < minWeight) {
                    minWeight = edge.getWeight();
                    minEdge = edge;
                }
            }

            if (minEdge != null) {
                mstEdges.add(minEdge);

                Vertex<T> newVertex = visited.contains(minEdge.getSource())
                        ? minEdge.getTarget()
                        : minEdge.getSource();

                visited.add(newVertex);
            } else {
                break;
            }
        }

        return mstEdges;
    }
}