package algo_analysis.entity;

import java.util.*;

public class Graph<T> {
    private HashMap<Vertex<T>, Set<Vertex<T>>> adjacencyList;
    private Set<Vertex<T>> vertices;
    private Set<Edge<T>> edges;

    public Graph() {
        this.adjacencyList = new HashMap<>();
        this.vertices = new HashSet<>();
        this.edges = new HashSet<>();
    }

    public void addVertex(Vertex<T> vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null.");
        }

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
        if (this.edges.contains(edge)) {
            throw new IllegalArgumentException("Edge already exists.");
        }
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

    public int getVertexCount() {
        return this.vertices.size();
    }

    public int getEdgeCount() {
        return this.edges.size();
    }

//    check if the graph is connected using dfs
    public boolean isConnected() {
        if (vertices.isEmpty()) return true;

        Set<Vertex<T>> visited = new HashSet<>();
        Vertex<T> start = vertices.iterator().next();
        dfsHelper(start, visited);

        return visited.size() == vertices.size();
    }

    private void dfsHelper(Vertex<T> vertex, Set<Vertex<T>> visited) {
        visited.add(vertex);
        for (Vertex<T> neighbor : getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                dfsHelper(neighbor, visited);
            }
        }
    }

//    get a vertex by data value
    public Optional<Vertex<T>> getVertexByData(T data) {
        return vertices.stream()
                .filter(v -> v.getData().equals(data))
                .findFirst();
    }
}