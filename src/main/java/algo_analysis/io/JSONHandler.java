package algo_analysis.io;

import algo_analysis.entity.*;
import algo_analysis.dto.*;

import java.io.*;
import java.util.*;

public class JSONHandler {
//    read file
    public static List<GraphData> readInput(String filePath) throws IOException {
        List<GraphData> graphs = new ArrayList<>();
        StringBuilder jsonContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line.trim());
            }
        }

        String json = jsonContent.toString();

        int graphsStart = json.indexOf("\"graphs\"");
        if (graphsStart == -1) {
            throw new IOException("Invalid JSON: 'graphs' array not found");
        }

        int arrayStart = json.indexOf("[", graphsStart);
        int arrayEnd = findMatchingBracket(json, arrayStart, '[', ']');
        String graphsArray = json.substring(arrayStart + 1, arrayEnd);

        List<String> graphObjects = splitObjects(graphsArray);
        for (String graphObj : graphObjects) {
            GraphData graphData = parseGraphObject(graphObj);
            graphs.add(graphData);
        }

        return graphs;
    }

//    graphdata to Graph class
    public static Graph<String> buildGraph(GraphData data) {
        Graph<String> graph = new Graph<>();
        Map<String, Vertex<String>> vertexMap = new HashMap<>();

        // Add all vertices
        for (String nodeName : data.nodes) {
            Vertex<String> vertex = new Vertex<>(nodeName);
            vertexMap.put(nodeName, vertex);
            graph.addVertex(vertex);
        }

        // Add all edges
        for (EdgeInfo edgeInfo : data.edges) {
            Vertex<String> source = vertexMap.get(edgeInfo.from);
            Vertex<String> target = vertexMap.get(edgeInfo.to);

            if (source != null && target != null) {
                graph.addEdge(source, target, edgeInfo.weight);
            }
        }

        return graph;
    }


//  === output ===
    public static void writeOutput(String filePath, List<GraphResult> results) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("{");
            writer.println("  \"results\": [");

            for (int i = 0; i < results.size(); i++) {
                GraphResult result = results.get(i);
                writer.println("    {");
                writer.println("      \"graph_id\": " + result.graphId + ",");
                writer.println("      \"input_stats\": {");
                writer.println("        \"vertices\": " + result.vertices + ",");
                writer.println("        \"edges\": " + result.edges);
                writer.println("      },");

                // Prim's result
                writer.println("      \"prim\": {");
                writeAlgorithmResult(writer, result.primResult, "      ");
                writer.println("      },");

                // Kruskal's result
                writer.println("      \"kruskal\": {");
                writeAlgorithmResult(writer, result.kruskalResult, "      ");
                writer.println("      }");

                if (i < results.size() - 1) {
                    writer.println("    },");
                } else {
                    writer.println("    }");
                }
            }

            writer.println("  ]");
            writer.println("}");
        }
    }

//    write algo result
    private static void writeAlgorithmResult(PrintWriter writer, AlgorithmResult result, String indent) {
        writer.println(indent + "  \"mst_edges\": [");

        for (int i = 0; i < result.mstEdges.size(); i++) {
            EdgeInfo edge = result.mstEdges.get(i);
            writer.print(indent + "    {\"from\": \"" + edge.from + "\", ");
            writer.print("\"to\": \"" + edge.to + "\", ");
            writer.print("\"weight\": " + edge.weight + "}");

            if (i < result.mstEdges.size() - 1) {
                writer.println(",");
            } else {
                writer.println();
            }
        }

        writer.println(indent + "  ],");
        writer.println(indent + "  \"total_cost\": " + result.totalCost + ",");
        writer.println(indent + "  \"operations_count\": " + result.operationsCount + ",");
        writer.println(indent + "  \"execution_time_ms\": " + result.executionTimeMs);
    }

    // == helper methods to upper ==

    private static GraphData parseGraphObject(String graphObj) {
        GraphData data = new GraphData();
        data.id = parseIntValue(graphObj, "id");
        data.nodes = parseStringArray(graphObj, "nodes");
        data.edges = parseEdgesArray(graphObj);
        return data;
    }

    private static int parseIntValue(String json, String key) {
        String pattern = "\"" + key + "\"";
        int keyIndex = json.indexOf(pattern);
        if (keyIndex == -1) return 0;

        int colonIndex = json.indexOf(":", keyIndex);
        int commaIndex = json.indexOf(",", colonIndex);
        int braceIndex = json.indexOf("}", colonIndex);

        int endIndex = (commaIndex != -1 && (braceIndex == -1 || commaIndex < braceIndex))
                ? commaIndex : braceIndex;

        String valueStr = json.substring(colonIndex + 1, endIndex).trim();
        return Integer.parseInt(valueStr);
    }

    private static List<String> parseStringArray(String json, String key) {
        List<String> result = new ArrayList<>();
        String pattern = "\"" + key + "\"";
        int keyIndex = json.indexOf(pattern);
        if (keyIndex == -1) return result;

        int arrayStart = json.indexOf("[", keyIndex);
        int arrayEnd = findMatchingBracket(json, arrayStart, '[', ']');
        String arrayContent = json.substring(arrayStart + 1, arrayEnd);

        String[] items = arrayContent.split(",");
        for (String item : items) {
            String cleaned = item.trim().replace("\"", "");
            if (!cleaned.isEmpty()) {
                result.add(cleaned);
            }
        }

        return result;
    }

    private static List<EdgeInfo> parseEdgesArray(String json) {
        List<EdgeInfo> edges = new ArrayList<>();
        String pattern = "\"edges\"";
        int keyIndex = json.indexOf(pattern);
        if (keyIndex == -1) return edges;

        int arrayStart = json.indexOf("[", keyIndex);
        int arrayEnd = findMatchingBracket(json, arrayStart, '[', ']');
        String arrayContent = json.substring(arrayStart + 1, arrayEnd);

        List<String> edgeObjects = splitObjects(arrayContent);
        for (String edgeObj : edgeObjects) {
            EdgeInfo edge = new EdgeInfo();
            edge.from = parseStringValue(edgeObj, "from");
            edge.to = parseStringValue(edgeObj, "to");
            edge.weight = parseDoubleValue(edgeObj, "weight");
            edges.add(edge);
        }

        return edges;
    }

    private static String parseStringValue(String json, String key) {
        String pattern = "\"" + key + "\"";
        int keyIndex = json.indexOf(pattern);
        if (keyIndex == -1) return "";

        int colonIndex = json.indexOf(":", keyIndex);
        int firstQuote = json.indexOf("\"", colonIndex);
        int secondQuote = json.indexOf("\"", firstQuote + 1);

        return json.substring(firstQuote + 1, secondQuote);
    }

    private static double parseDoubleValue(String json, String key) {
        String pattern = "\"" + key + "\"";
        int keyIndex = json.indexOf(pattern);
        if (keyIndex == -1) return 0.0;

        int colonIndex = json.indexOf(":", keyIndex);
        int commaIndex = json.indexOf(",", colonIndex);
        int braceIndex = json.indexOf("}", colonIndex);

        int endIndex = (commaIndex != -1 && (braceIndex == -1 || commaIndex < braceIndex))
                ? commaIndex : braceIndex;

        String valueStr = json.substring(colonIndex + 1, endIndex).trim();
        return Double.parseDouble(valueStr);
    }

    private static List<String> splitObjects(String arrayContent) {
        List<String> objects = new ArrayList<>();
        int depth = 0;
        int start = -1;

        for (int i = 0; i < arrayContent.length(); i++) {
            char c = arrayContent.charAt(i);

            if (c == '{') {
                if (depth == 0) start = i;
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 0 && start != -1) {
                    objects.add(arrayContent.substring(start, i + 1));
                    start = -1;
                }
            }
        }

        return objects;
    }

    private static int findMatchingBracket(String json, int start, char open, char close) {
        int depth = 1;
        for (int i = start + 1; i < json.length(); i++) {
            if (json.charAt(i) == open) depth++;
            else if (json.charAt(i) == close) {
                depth--;
                if (depth == 0) return i;
            }
        }
        return json.length();
    }
}