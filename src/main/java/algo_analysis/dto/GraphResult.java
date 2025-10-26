package algo_analysis.dto;

public class GraphResult {
    public int graphId;
    public int vertices;
    public int edges;
    public AlgorithmResult primResult;
    public AlgorithmResult kruskalResult;

    public GraphResult(int graphId, int vertices, int edges) {
        this.graphId = graphId;
        this.vertices = vertices;
        this.edges = edges;
    }
}
