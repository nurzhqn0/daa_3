package algo_analysis.dto;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmResult {
    public List<EdgeInfo> mstEdges;
    public double totalCost;
    public int operationsCount;
    public double executionTimeMs;

    public AlgorithmResult() {
        this.mstEdges = new ArrayList<>();
    }
}
