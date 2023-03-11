package com.example.socialnetworkgui.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyConnectedComponent {
    private List<Long> ans;
    private List< List<Long>> edges;
    private List<Long> vertices;
    private Map<Long, Boolean> visited;
    public MyConnectedComponent(List<Long> vertices, List< List<Long> > edges) {
        this.edges = edges;
        this.vertices = vertices;
        visited = new HashMap<>();
        ans = new ArrayList<>();
    }

    public void initGraph() {
        for (Long vertex: vertices)
            visited.put(vertex, false);
    }

    public List<Long> getNeighbours(Long vertex) {
        return edges.get(vertices.indexOf(vertex));
    }

    void DFS(Long node) {
        visited.replace(node, true);
        ans.add(node);
        for (Long nxt: getNeighbours(node))
            if (!visited.get(nxt)) DFS(nxt);
    }

    public List<Long> getMyComp(Long node) {
        initGraph();
        ans.clear();
        DFS(node);
        return ans;
    }
}
