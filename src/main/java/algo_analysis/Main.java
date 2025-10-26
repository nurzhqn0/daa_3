package algo_analysis;

import algo_analysis.algorithms.*;
import algo_analysis.dto.*;
import algo_analysis.entity.*;
import algo_analysis.io.JSONHandler;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            // input
            List<GraphData> graphDataList = JSONHandler.readInput("input.json");

            List<GraphResult> results = new ArrayList<>();

            for (GraphData graphData : graphDataList) {
                System.out.println("-- Graph " + graphData.id + " --");

                Graph<String> graph = JSONHandler.buildGraph(graphData);

                System.out.println("Vertices: " + graph.getVertexCount());
                System.out.println("Edges: " + graph.getEdgeCount());
                System.out.println("Connected: " + graph.isConnected());

                GraphResult result = new GraphResult(
                        graphData.id,
                        graph.getVertexCount(),
                        graph.getEdgeCount()
                );

                // Prim's Algo
                result.primResult = PrimAlgorithm.findMST(graph);
                System.out.println("✓ MST Cost: " + result.primResult.totalCost +
                        " | Operations: " + result.primResult.operationsCount +
                        " | Time: " + String.format("%.2f", result.primResult.executionTimeMs) + "ms");

                // Kruskal's Algo
                result.kruskalResult = KruskalAlgorithm.findMST(graph);
                System.out.println("✓ MST Cost: " + result.kruskalResult.totalCost +
                        " | Operations: " + result.kruskalResult.operationsCount +
                        " | Time: " + String.format("%.2f", result.kruskalResult.executionTimeMs) + "ms");

                results.add(result);
                System.out.println();
            }

            // output
            JSONHandler.writeOutput("output.json", results);

            // summary
            System.out.println("\n== Summary ==");
            for (GraphResult result : results) {
                System.out.println("Graph " + result.graphId + ":");
                System.out.println("  MST Cost: " + result.primResult.totalCost);
                System.out.println("  Prim Operations: " + result.primResult.operationsCount);
                System.out.println("  Kruskal Operations: " + result.kruskalResult.operationsCount);
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}