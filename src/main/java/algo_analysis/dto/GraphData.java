package algo_analysis.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents graph data from input.json
 */
public class GraphData {
    public int id;
    public List<String> nodes;
    public List<EdgeInfo> edges;

    public GraphData() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }
}
