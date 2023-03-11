package com.example.socialnetworkgui.graph;
import java.util.*;

public class LongestPath {

    private List< List<Long>> edges;
    private List<Long> vertices, ans, currComp;
    private Map<Long, Boolean> visited;

    public LongestPath(List<Long> vertices, List< List<Long> > edges) {
        this.edges = edges;
        this.vertices = vertices;
        visited = new HashMap<>();
        ans = new ArrayList<>();
        currComp = new ArrayList<>();
    }

    public List<Long> getNeighbours(Long vertex) {
        return edges.get(vertices.indexOf(vertex));
    }

    public void initGraph() {
        for (Long vertex: vertices)
            visited.put(vertex, false);
    }


    /// DFS traversal
    public  List<Long> DFS(Long node) {
        /// We are going to operate on the tree that is being formed via DFS traversal
        /// The root of the tree will be the vertex "node"
        /// We add new leaves in the tree every time we discover unvisited vertices
        Map<Long, Long> father = new HashMap<>();  /// the father of every node in the DFS tre
        Map<Long, Integer> dist = new HashMap<>(); /// the distance from the root(node) to the node specified in BFS tree

        /// We save the vertices that are to be processed in a queue
        /// The first vertex to be added in the queue is node, the root of the BFS tree.
        Stack<Long> nodesStack = new Stack<>();
        nodesStack.add(node);
        father.put(node, null);
        visited.put(node, true);
        dist.put(node, 0);
        while (!nodesStack.isEmpty()) {
            Long u = nodesStack.pop();

            for (Long v: getNeighbours(u))
                if (!visited.get(v))  {
                    visited.replace(v, true);
                    father.put(v, u);
                    dist.put(v, dist.get(u) + 1);
                    nodesStack.add(v);
                    break;
                }
        }

        /// We find the furthest node from the root( or deepest)
        Long lastNode = node;

        for (Long currNode: vertices)
            if (visited.get(currNode))
                if (dist.get(lastNode) < dist.get(currNode))
                    lastNode = currNode;

        /// We compose (in bottom-up style) the longest path from the vertex node to any other node in the graph
        List<Long> maxPath = new ArrayList<>();
        while (lastNode != null) {
            maxPath.add(lastNode);
            lastNode = father.get(lastNode);
        }

        return maxPath;
     }

    public void updateAnswer(List<Long> newAns) {
        if (newAns.size() <= ans.size()) return;
        ans.clear();
        ans.addAll(newAns);
    }

    public List<Long> getLongestPath() {
        for (Long node: vertices) {
            initGraph();
            updateAnswer(DFS(node));
        }

        return ans;
    }
}
