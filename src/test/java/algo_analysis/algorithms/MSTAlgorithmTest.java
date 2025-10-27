package algo_analysis.algorithms;


import algo_analysis.dto.AlgorithmResult;
import algo_analysis.entity.Edge;
import algo_analysis.entity.Graph;
import algo_analysis.entity.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


import static org.junit.jupiter.api.Assertions.*;

public class MSTAlgorithmTest {
    private Graph<String> simpleGraph;
    private Graph<String> complexGraph;
    private Graph<String> disconnectedGraph;

    @BeforeEach
    void setUp() {
        simpleGraph = createSimpleGraph();
        complexGraph = createComplexGraph();
        disconnectedGraph = createDisconnectedGraph();
    }

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

        return graph;
    }

    // Prim algo

    @Test
    @DisplayName("Prim: correct MST cost")
    void testPrimSimpleGraphCost() {
        AlgorithmResult result = PrimAlgorithm.findMST(simpleGraph);
        assertEquals(6.0, result.totalCost, 0.001, "MST cost is 6.0");
    }

    @Test
    @DisplayName("Prim: V-1 edges test")
    void testPrimSimpleGraphEdgeCount() {
        AlgorithmResult result = PrimAlgorithm.findMST(simpleGraph);
        assertEquals(3, result.mstEdges.size(), "MST 3 edges");
    }

    @Test
    @DisplayName("Prim: correst MST cost for complex")
    void testPrimComplexGraphCost() {
        AlgorithmResult result = PrimAlgorithm.findMST(complexGraph);
        assertEquals(16.0, result.totalCost, 0.001, "MST cost is 16.0");
    }

    @Test
    @DisplayName("Prim: V-1 edges test for complex")
    void testPrimComplexGraphEdgeCount() {
        AlgorithmResult result = PrimAlgorithm.findMST(complexGraph);
        assertEquals(4, result.mstEdges.size(), "MST 4 edges");
    }

    @Test
    @DisplayName("Prim: operation count test")
    void testPrimOperationsCount() {
        AlgorithmResult result = PrimAlgorithm.findMST(simpleGraph);
        assertTrue(result.operationsCount > 0, "Operations count  tracked");
    }

    @Test
    @DisplayName("Prim: execution time check")
    void testPrimExecutionTime() {
        AlgorithmResult result = PrimAlgorithm.findMST(complexGraph);
        assertTrue(result.executionTimeMs > 0, "Execution time tracked");
    }

    @Test
    @DisplayName("Prim: empty graph should be cost 0")
    void testPrimEmptyGraph() {
        Graph<String> emptyGraph = new Graph<>();
        AlgorithmResult result = PrimAlgorithm.findMST(emptyGraph);

        assertEquals(0.0, result.totalCost, "Empty graph have 0 cost");
        assertEquals(0, result.mstEdges.size(), "Empty graph have 0 edges");
    }

    // kruskal

    @Test
    @DisplayName("Kruskal: correct MST cost simple")
    void testKruskalSimpleGraphCost() {
        AlgorithmResult result = KruskalAlgorithm.findMST(simpleGraph);
        assertEquals(6.0, result.totalCost, 0.001, "MST cost is 6.0");
    }

    @Test
    @DisplayName("Kruskal: V-1 correct ")
    void testKruskalSimpleGraphEdgeCount() {
        AlgorithmResult result = KruskalAlgorithm.findMST(simpleGraph);

        assertEquals(3, result.mstEdges.size(), "MST 3 edges");
    }

    @Test
    @DisplayName("Kruskal: correct MST cost for complex")
    void testKruskalComplexGraphCost() {
        AlgorithmResult result = KruskalAlgorithm.findMST(complexGraph);

        assertEquals(16.0, result.totalCost, 0.001, "MST cost is 16.0");
    }

    @Test
    @DisplayName("Kruskal: V-1 edges test for complex")
    void testKruskalComplexGraphEdgeCount() {
        AlgorithmResult result = KruskalAlgorithm.findMST(complexGraph);
        assertEquals(4, result.mstEdges.size(), "MST 4 edges");
    }

    @Test
    @DisplayName("Kruskal: operation count")
    void testKruskalOperationsCount() {
        AlgorithmResult result = KruskalAlgorithm.findMST(simpleGraph);
        assertTrue(result.operationsCount > 0, "Operations count tracked");
    }

    @Test
    @DisplayName("Kruskal: execution time")
    void testKruskalExecutionTime() {
        AlgorithmResult result = KruskalAlgorithm.findMST(complexGraph);

        assertTrue(result.executionTimeMs > 0, "Execution time tracked");
    }

    @Test
    @DisplayName("Kruskal: empty graph should be 0 cost")
    void testKruskalEmptyGraph() {
        Graph<String> emptyGraph = new Graph<>();
        AlgorithmResult result = KruskalAlgorithm.findMST(emptyGraph);

        assertEquals(0.0, result.totalCost, "Empty graph 0 cost");
        assertEquals(0, result.mstEdges.size(), "Empty graph 0 edges");
    }

    // comparison

    @Test
    @DisplayName("same cost Prim and Kruskal")
    void testBothAlgorithmsProduceSameCost() {
        AlgorithmResult primResult = PrimAlgorithm.findMST(simpleGraph);
        AlgorithmResult kruskalResult = KruskalAlgorithm.findMST(simpleGraph);

        assertEquals(primResult.totalCost, kruskalResult.totalCost, 0.001,
                "Both algorithms produce same MST cost");
    }

    @Test
    @DisplayName("same number of edges Prim and Kruskal")
    void testBothAlgorithmsProduceSameEdgeCount() {
        AlgorithmResult primResult = PrimAlgorithm.findMST(complexGraph);
        AlgorithmResult kruskalResult = KruskalAlgorithm.findMST(complexGraph);

        assertEquals(primResult.mstEdges.size(), kruskalResult.mstEdges.size(),
                "Both algorithms produce same number of edges");
    }

    // performance

    @Test
    @DisplayName("Performance: Prim reasonable time")
    void testPrimPerformance() {
        AlgorithmResult result = PrimAlgorithm.findMST(complexGraph);

        assertTrue(result.executionTimeMs < 100.0,
                "Prim should quickly on small graph");
    }

    @Test
    @DisplayName("Performance: Kruskal reasonable time")
    void testKruskalPerformance() {
        AlgorithmResult result = KruskalAlgorithm.findMST(complexGraph);

        assertTrue(result.executionTimeMs < 100.0,
                "Kruskal should quickly on small graph");
    }

    // edge case

    @Test
    @DisplayName("Single vertex 0 cost")
    void testSingleVertexGraph() {
        Graph<String> singleVertex = new Graph<>();
        singleVertex.addVertex(new Vertex<>("A"));

        AlgorithmResult primResult = PrimAlgorithm.findMST(singleVertex);
        AlgorithmResult kruskalResult = KruskalAlgorithm.findMST(singleVertex);

        assertEquals(0.0, primResult.totalCost, "Single vertex have 0 cost");
        assertEquals(0.0, kruskalResult.totalCost, "Single vertex have 0 cost");
    }

    @Test
    @DisplayName("Disconnected graph should connect available components")
    void testDisconnectedGraph() {
        AlgorithmResult primResult = PrimAlgorithm.findMST(disconnectedGraph);
        AlgorithmResult kruskalResult = KruskalAlgorithm.findMST(disconnectedGraph);

        assertTrue(primResult.mstEdges.size() >= 1, "Should connect at least one component");
        assertTrue(kruskalResult.mstEdges.size() >= 1, "Should connect at least one component");
    }
}