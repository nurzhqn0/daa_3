package algo_analysis;

import algo_analysis.entity.*;

public class Main {
    public static void main(String[] args) {
        Graph<String> graph = new Graph<>();

        Vertex<String> v1 = new Vertex<>("A");
        Vertex<String> v2 = new Vertex<>("B");
        Vertex<String> v3 = new Vertex<>("C");
        Vertex<String> v4 = new Vertex<>("D");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);

        System.out.println("All vertices: " + graph.getAllVertices());

        graph.addEdge(v1, v2, 5.0);
        graph.addEdge(v1, v3, 3.0);
        graph.addEdge(v2, v3, 2.0);
        graph.addEdge(v2, v4, 7.0);
        graph.addEdge(v3, v4, 1.0);

        System.out.println("All edges:");
        for (Edge<String> edge : graph.getAllEdges()) {
            System.out.println("  " + edge);
        }
        ;
        System.out.println("Is A adjacent to B? " + graph.isAdjacent(v1, v2));
        System.out.println("Is A adjacent to D? " + graph.isAdjacent(v1, v4));

        System.out.println("\n=== Neighbors ===");
        System.out.println("Neighbors of A: " + graph.getNeighbors(v1));
        System.out.println("Neighbors of B: " + graph.getNeighbors(v2));
        System.out.println("Neighbors of C: " + graph.getNeighbors(v3));

        System.out.println("\n=== Get Specific Edge ===");

        // Remove an edge
        System.out.println("\n=== Remove Edge ===");
        graph.removeEdge(v2, v4, 7.0);
        System.out.println("Removed edge B-D");
        System.out.println("All edges after removal:");
        for (Edge<String> edge2 : graph.getAllEdges()) {
            System.out.println("  " + edge2);
        }

        // Remove a vertex
        System.out.println("\n=== Remove Vertex ===");
        graph.removeVertex(v4);
        System.out.println("Removed vertex D");
        System.out.println("All vertices: " + graph.getAllVertices());
        System.out.println("All edges after vertex removal:");
        for (Edge<String> edge2 : graph.getAllEdges()) {
            System.out.println("  " + edge2);
        }
    }
}