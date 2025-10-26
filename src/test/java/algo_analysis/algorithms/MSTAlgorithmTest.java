package algo_analysis.algorithms;


import algo_analysis.dto.AlgorithmResult;
import algo_analysis.entity.Edge;
import algo_analysis.entity.Graph;
import algo_analysis.entity.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Tests for Prim's and Kruskal's MST Algorithms
 */
public class MSTAlgorithmTest {

    private Graph<String> simpleGraph;
    private Graph<String> complexGraph;
    private Graph<String> disconnectedGraph;

    @BeforeEach
    void setUp() {
        // Simple graph for basic testing
        simpleGraph = createSimpleGraph();

        // Complex graph matching input.json
        complexGraph = createComplexGraph();

        // Disconnected graph
        disconnectedGraph = createDisconnectedGraph();
    }

    /**
     * Create a simple 4-vertex graph
     *     A ---5--- B
     *     |       / |
     *     3      2  7
     *     |   /     |
     *     C ---1--- D
     */
    private Graph<String> createSimpleGraph() {
        Graph<String> graph = new Graph<>();

        Vertex<String> A = new Vertex<>("A");
        Vertex<String> B = new Vertex<>("B");
        Vertex<String> C = new Vertex<>("C");
        Vertex<String> D = new Vertex<>("D");

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);

        graph.addEdge(A, B, 5.0);
        graph.addEdge(A, C, 3.0);
        graph.addEdge(B, C, 2.0);
        graph.addEdge(B, D, 7.0);
        graph.addEdge(C, D, 1.0);

        return graph;
    }

    /**
     * Create complex graph matching input.json graph 1
     */
    private Graph<String> createComplexGraph() {
        Graph<String> graph = new Graph<>();

        Vertex<String> A = new Vertex<>("A");
        Vertex<String> B = new Vertex<>("B");
        Vertex<String> C = new Vertex<>("C");
        Vertex<String> D = new Vertex<>("D");
        Vertex<String> E = new Vertex<>("E");

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);
        graph.addVertex(E);

        graph.addEdge(A, B, 4.0);
        graph.addEdge(A, C, 3.0);
        graph.addEdge(B, C, 2.0);
        graph.addEdge(B, D, 5.0);
        graph.addEdge(C, D, 7.0);
        graph.addEdge(C, E, 8.0);
        graph.addEdge(D, E, 6.0);

        return graph;
    }

    /**
     * Create disconnected graph
     */
    private Graph<String> createDisconnectedGraph() {
        Graph<String> graph = new Graph<>();

        Vertex<String> A = new Vertex<>("A");
        Vertex<String> B = new Vertex<>("B");
        Vertex<String> C = new Vertex<>("C");
        Vertex<String> D = new Vertex<>("D");

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);

        graph.addEdge(A, B, 1.0);
        graph.addEdge(C, D, 2.0);
        // A-B and C-D are disconnected

        return graph;
    }

    // ========== PRIM'S ALGORITHM TESTS ==========

    @Test
    @DisplayName("Prim: Simple graph should produce correct MST cost")
    void testPrimSimpleGraphCost() {
        AlgorithmResult result = PrimAlgorithm.findMST(simpleGraph);

        // MST should be: C-D (1), B-C (2), A-C (3)
        // Total cost: 1 + 2 + 3 = 6
        assertEquals(6.0, result.totalCost, 0.001, "MST cost should be 6.0");
    }

    @Test
    @DisplayName("Prim: Simple graph should have V-1 edges")
    void testPrimSimpleGraphEdgeCount() {
        AlgorithmResult result = PrimAlgorithm.findMST(simpleGraph);

        // MST should have 4-1 = 3 edges
        assertEquals(3, result.mstEdges.size(), "MST should have 3 edges");
    }

    @Test
    @DisplayName("Prim: Complex graph should produce correct MST cost")
    void testPrimComplexGraphCost() {
        AlgorithmResult result = PrimAlgorithm.findMST(complexGraph);

        // MST should be: B-C (2), A-C (3), B-D (5), D-E (6)
        // Total cost: 2 + 3 + 5 + 6 = 16
        assertEquals(16.0, result.totalCost, 0.001, "MST cost should be 16.0");
    }

    @Test
    @DisplayName("Prim: Complex graph should have V-1 edges")
    void testPrimComplexGraphEdgeCount() {
        AlgorithmResult result = PrimAlgorithm.findMST(complexGraph);

        // MST should have 5-1 = 4 edges
        assertEquals(4, result.mstEdges.size(), "MST should have 4 edges");
    }

    @Test
    @DisplayName("Prim: Should track operations count")
    void testPrimOperationsCount() {
        AlgorithmResult result = PrimAlgorithm.findMST(simpleGraph);

        // Operations should be greater than 0
        assertTrue(result.operationsCount > 0, "Operations count should be tracked");
    }

    @Test
    @DisplayName("Prim: Should track execution time")
    void testPrimExecutionTime() {
        AlgorithmResult result = PrimAlgorithm.findMST(complexGraph);

        // Execution time should be greater than 0
        assertTrue(result.executionTimeMs > 0, "Execution time should be tracked");
    }

    @Test
    @DisplayName("Prim: Empty graph should return zero cost")
    void testPrimEmptyGraph() {
        Graph<String> emptyGraph = new Graph<>();
        AlgorithmResult result = PrimAlgorithm.findMST(emptyGraph);

        assertEquals(0.0, result.totalCost, "Empty graph should have zero cost");
        assertEquals(0, result.mstEdges.size(), "Empty graph should have no edges");
    }

    // ========== KRUSKAL'S ALGORITHM TESTS ==========

    @Test
    @DisplayName("Kruskal: Simple graph should produce correct MST cost")
    void testKruskalSimpleGraphCost() {
        AlgorithmResult result = KruskalAlgorithm.findMST(simpleGraph);

        // MST should be: C-D (1), B-C (2), A-C (3)
        // Total cost: 1 + 2 + 3 = 6
        assertEquals(6.0, result.totalCost, 0.001, "MST cost should be 6.0");
    }

    @Test
    @DisplayName("Kruskal: Simple graph should have V-1 edges")
    void testKruskalSimpleGraphEdgeCount() {
        AlgorithmResult result = KruskalAlgorithm.findMST(simpleGraph);

        // MST should have 4-1 = 3 edges
        assertEquals(3, result.mstEdges.size(), "MST should have 3 edges");
    }

    @Test
    @DisplayName("Kruskal: Complex graph should produce correct MST cost")
    void testKruskalComplexGraphCost() {
        AlgorithmResult result = KruskalAlgorithm.findMST(complexGraph);

        // MST should be: B-C (2), A-C (3), B-D (5), D-E (6)
        // Total cost: 2 + 3 + 5 + 6 = 16
        assertEquals(16.0, result.totalCost, 0.001, "MST cost should be 16.0");
    }

    @Test
    @DisplayName("Kruskal: Complex graph should have V-1 edges")
    void testKruskalComplexGraphEdgeCount() {
        AlgorithmResult result = KruskalAlgorithm.findMST(complexGraph);

        // MST should have 5-1 = 4 edges
        assertEquals(4, result.mstEdges.size(), "MST should have 4 edges");
    }

    @Test
    @DisplayName("Kruskal: Should track operations count")
    void testKruskalOperationsCount() {
        AlgorithmResult result = KruskalAlgorithm.findMST(simpleGraph);

        // Operations should be greater than 0
        assertTrue(result.operationsCount > 0, "Operations count should be tracked");
    }

    @Test
    @DisplayName("Kruskal: Should track execution time")
    void testKruskalExecutionTime() {
        AlgorithmResult result = KruskalAlgorithm.findMST(complexGraph);

        // Execution time should be greater than 0
        assertTrue(result.executionTimeMs > 0, "Execution time should be tracked");
    }

    @Test
    @DisplayName("Kruskal: Empty graph should return zero cost")
    void testKruskalEmptyGraph() {
        Graph<String> emptyGraph = new Graph<>();
        AlgorithmResult result = KruskalAlgorithm.findMST(emptyGraph);

        assertEquals(0.0, result.totalCost, "Empty graph should have zero cost");
        assertEquals(0, result.mstEdges.size(), "Empty graph should have no edges");
    }

    // ========== COMPARISON TESTS ==========

    @Test
    @DisplayName("Prim and Kruskal should produce same MST cost")
    void testBothAlgorithmsProduceSameCost() {
        AlgorithmResult primResult = PrimAlgorithm.findMST(simpleGraph);
        AlgorithmResult kruskalResult = KruskalAlgorithm.findMST(simpleGraph);

        assertEquals(primResult.totalCost, kruskalResult.totalCost, 0.001,
                "Both algorithms should produce same MST cost");
    }

    @Test
    @DisplayName("Prim and Kruskal should produce same number of edges")
    void testBothAlgorithmsProduceSameEdgeCount() {
        AlgorithmResult primResult = PrimAlgorithm.findMST(complexGraph);
        AlgorithmResult kruskalResult = KruskalAlgorithm.findMST(complexGraph);

        assertEquals(primResult.mstEdges.size(), kruskalResult.mstEdges.size(),
                "Both algorithms should produce same number of edges");
    }

    // ========== PERFORMANCE TESTS ==========

    @Test
    @DisplayName("Performance: Prim should complete within reasonable time")
    void testPrimPerformance() {
        AlgorithmResult result = PrimAlgorithm.findMST(complexGraph);

        // Should complete in less than 100ms for small graph
        assertTrue(result.executionTimeMs < 100.0,
                "Prim should complete quickly on small graph");
    }

    @Test
    @DisplayName("Performance: Kruskal should complete within reasonable time")
    void testKruskalPerformance() {
        AlgorithmResult result = KruskalAlgorithm.findMST(complexGraph);

        // Should complete in less than 100ms for small graph
        assertTrue(result.executionTimeMs < 100.0,
                "Kruskal should complete quickly on small graph");
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    @DisplayName("Single vertex graph should have zero cost")
    void testSingleVertexGraph() {
        Graph<String> singleVertex = new Graph<>();
        singleVertex.addVertex(new Vertex<>("A"));

        AlgorithmResult primResult = PrimAlgorithm.findMST(singleVertex);
        AlgorithmResult kruskalResult = KruskalAlgorithm.findMST(singleVertex);

        assertEquals(0.0, primResult.totalCost, "Single vertex should have zero cost");
        assertEquals(0.0, kruskalResult.totalCost, "Single vertex should have zero cost");
    }

    @Test
    @DisplayName("Disconnected graph should connect available components")
    void testDisconnectedGraph() {
        AlgorithmResult primResult = PrimAlgorithm.findMST(disconnectedGraph);
        AlgorithmResult kruskalResult = KruskalAlgorithm.findMST(disconnectedGraph);

        // Should connect one component (either A-B or C-D)
        assertTrue(primResult.mstEdges.size() >= 1, "Should connect at least one component");
        assertTrue(kruskalResult.mstEdges.size() >= 1, "Should connect at least one component");
    }
}