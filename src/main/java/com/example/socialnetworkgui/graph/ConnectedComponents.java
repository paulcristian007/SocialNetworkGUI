package com.example.socialnetworkgui.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectedComponents {
    private List< List<Long>> edges;
    private List<Long> vertices;
    private Map<Long, Boolean> visited;

    public ConnectedComponents( List<Long> vertices, List<List<Long>> edges) {
        this.edges = edges;
        this.vertices = vertices;
        this.visited = new HashMap<>();
    }

    public void initGraph() {
        for (Long vertex: vertices)
            visited.put(vertex, false);
    }

    public List<Long> getNeighbours(Long vertex) {
        return edges.get(vertices.indexOf(vertex));
    }

    public void DFS(Long node) {
        visited.replace(node, true);
        for (Long nxt: getNeighbours(node))
            if (!visited.get(nxt))
                DFS(nxt);
    }

    public int numberOfComponents() {
        initGraph();
        int ans = 0;
        for (Long node: vertices) {
            if (!visited.get(node)) {
                ans++;
                DFS(node);
            }
        }
        return ans;
    }
}
