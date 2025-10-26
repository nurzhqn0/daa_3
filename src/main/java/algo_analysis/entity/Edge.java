package algo_analysis.entity;

public class Edge<T> {
    private Vertex<T> source;
    private Vertex<T> target;

    private double weight;

    public Edge(Vertex<T> source, Vertex<T> target, double weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public Vertex<T> getSource() {
        return source;
    }

    public Vertex<T> getTarget() {
        return target;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Edge<?> edge = (Edge<?>) obj;
        return (source.equals(edge.source) && target.equals(edge.target) && weight == edge.weight) ||
                (source.equals(edge.target) && target.equals(edge.source) && weight == edge.weight);
    }

    @Override
    public int hashCode() {
        return (source.hashCode() ^ target.hashCode()) ^ Double.hashCode(weight);
    }

    @Override
    public String toString() {
        return "Edge{" + source + " -- " + target + ", weight = " + weight + '}';
    }
}