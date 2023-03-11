package com.example.socialnetworkgui.service;

import com.example.socialnetworkgui.domain.Friendship;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.graph.ConnectedComponents;
import com.example.socialnetworkgui.graph.LongestPath;
import com.example.socialnetworkgui.graph.MyConnectedComponent;
import com.example.socialnetworkgui.sqlQueries.FriendshipSqlQuery;
import com.example.socialnetworkgui.sqlQueries.UserSqlQuery;

import java.util.ArrayList;
import java.util.List;

public class ServiceGraph {

    private UserSqlQuery userQuery;
    private FriendshipSqlQuery friendsQuery;
    private List<List<Long> > edges = new ArrayList<>();
    private List<Long> vertices = new ArrayList<>();

    public ServiceGraph(UserSqlQuery userQuery, FriendshipSqlQuery friendsQuery) {
        this.userQuery = userQuery;
        this.friendsQuery = friendsQuery;
        makeGraph();
    }

    private void makeGraph() {
        for (User user: userQuery.getAll())
            addVertex(user.getId());

        for (Friendship friendship: friendsQuery.getAll())
            addEdge(friendship.getId1(), friendship.getId2());
    }


    public List<Long> getNeighbours(Long vertex) {
        return edges.get(vertices.indexOf(vertex));
    }

    public void addVertex(Long vertex) {
        vertices.add(vertex);
        edges.add(new ArrayList<>());
    }

    public void addEdge(Long vertex1, Long vertex2) {
        List<Long> adjList1 = getNeighbours(vertex1);
        List<Long> adjList2 = getNeighbours(vertex2);
        adjList1.add(vertex2);
        adjList2.add(vertex1);
    }

    public int numberOfCommunities() {
        ConnectedComponents comp = new ConnectedComponents(vertices, edges);
        return comp.numberOfComponents();
    }

    public List<User> longestPath() {
        LongestPath path = new LongestPath(vertices, edges);
        MyConnectedComponent comp = new MyConnectedComponent(vertices, edges);

        List<User> ans = new ArrayList<>();
        for (Long id: comp.getMyComp(path.getLongestPath().get(0)))
            ans.add(userQuery.getUser(id));
        return ans;
    }
}
