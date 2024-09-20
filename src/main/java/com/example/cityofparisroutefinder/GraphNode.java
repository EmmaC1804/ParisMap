package com.example.cityofparisroutefinder;

import java.util.ArrayList;
import java.util.List;

// Adjacency List
public class GraphNode<T> {
    private T data;
    public int nodeValue = Integer.MAX_VALUE;

    public List<GraphLink> adjList = new ArrayList<>();

    public GraphNode(T data){
        this.data = data;
    }

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
    }

//    public List<GraphNode<T>> getAdjList(){
//        return adjList;
//    }

    public void connectToNodeDirected(GraphNode<T> destNode,int importance,int dist){
        adjList.add(new GraphLink(destNode,importance,dist));
    }

    public void connectToNodeUndirected(GraphNode<T> destNode,int importance, int distance){
        adjList.add(new GraphLink(destNode,importance,distance));
        destNode.adjList.add(new GraphLink(this,importance,distance));
    }
}
