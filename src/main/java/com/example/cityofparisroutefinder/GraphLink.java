package com.example.cityofparisroutefinder;

public class GraphLink {

    public GraphNode<?> destNode;
    public int importance;
    public int dist;

    public GraphLink(GraphNode<?> destNode, int importance, int dist){
        this.destNode = destNode;
        this.importance = importance;
        this.dist=dist;
    }
}
