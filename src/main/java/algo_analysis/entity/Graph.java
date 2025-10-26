package algo_analysis.entity;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Graph<T> {
    private HashMap<Vertex<T>, Set<Vertex<T>>> adjacencyList;
    private Set<Vertex<T>> vertices;
    private Set<Edge<T>> edges;

    public void setAdjacencyList(HashMap<Vertex<T>, Set<Vertex<T>>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public void setVertices(Set<Vertex<T>> vertices) {
        this.vertices = vertices;
    }

    public void setEdges(Set<Edge<T>> edges) {
        this.edges = edges;
    }

    public Graph() {
        this.adjacencyList = new HashMap<>();
        this.vertices = new HashSet<>();
        this.edges = new HashSet<>();
    }

    public void addVertex(Vertex<T> vertex) {
        if (this.vertices.contains(vertex)) {
            throw new IllegalArgumentException("Vertex already exists.");
        }
        this.vertices.add(vertex);
        this.adjacencyList.put(vertex, new HashSet<>());
    }

    public void removeVertex(Vertex<T> vertex) {
        if (!this.vertices.contains(vertex)) {
            throw new IllegalArgumentException("Vertex doesn't exist.");
        }

        this.vertices.remove(vertex);
        this.adjacencyList.remove(vertex);

        for (Vertex<T> v : this.vertices) {
            this.adjacencyList.get(v).remove(vertex);
        }

        this.edges.removeIf(edge ->
                edge.getSource().equals(vertex) || edge.getTarget().equals(vertex)
        );
    }

    public void addEdge(Vertex<T> source, Vertex<T> target, double weight) {
        if (!this.vertices.contains(source) || !this.vertices.contains(target)) {
            throw new IllegalArgumentException("One or both vertices don't exist.");
        }

        Edge<T> edge = new Edge<>(source, target, weight);
        this.edges.add(edge);

        this.adjacencyList.get(source).add(target);
        this.adjacencyList.get(target).add(source);
    }

    public void removeEdge(Vertex<T> source, Vertex<T> target, double weight) {
        if (!this.vertices.contains(source) || !this.vertices.contains(target)) {
            throw new IllegalArgumentException("One or both vertices don't exist.");
        }

        Edge<T> edge = new Edge<>(source, target, weight);
        this.edges.remove(edge);

        this.adjacencyList.get(source).remove(target);
        this.adjacencyList.get(target).remove(source);
    }

    public Optional<Edge<T>> getEdge(Vertex<T> source, Vertex<T> target) {
        return this.edges.stream()
                .filter(edge -> (edge.getSource().equals(source) && edge.getTarget().equals(target)) ||
                        (edge.getSource().equals(target) && edge.getTarget().equals(source)))
                .findFirst();
    }

    public boolean isAdjacent(Vertex<T> source, Vertex<T> target) {
        return this.adjacencyList.get(source).contains(target);
    }

    public Set<Vertex<T>> getNeighbors(Vertex<T> vertex) {
        return new HashSet<>(this.adjacencyList.get(vertex));
    }

    public Set<Vertex<T>> getAllVertices() {
        return new HashSet<>(this.vertices);
    }

    public Set<Edge<T>> getAllEdges() {
        return new HashSet<>(this.edges);
    }
}