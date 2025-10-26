package algo_analysis.dto;

public class EdgeInfo {
    public String from;
    public String to;
    public double weight;

    public EdgeInfo() {}

    public EdgeInfo(String from, String to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
