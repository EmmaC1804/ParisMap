package com.example.cityofparisroutefinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Search {

    public static <T> List<List<GraphNode<?>>> findAllPathsDF(GraphNode<?> from, List<GraphNode<?>> encountered, T lookingFor) {
        List<List<GraphNode<?>>> result = null, temp2;

        if (from.getData().equals(lookingFor)) {
            List<GraphNode<?>> temp = new ArrayList<>();
            temp.add(from);
            result = new ArrayList<>();
            result.add(temp);
            return result;
        }
        if (encountered == null) encountered = new ArrayList<>();
        encountered.add(from);

        for (GraphLink adjLink : from.adjList) {
            if (!encountered.contains(adjLink.destNode)) {
                temp2 = findAllPathsDF(adjLink.destNode, new ArrayList<>(encountered), lookingFor);

                if (temp2 != null) {
                    for (List<GraphNode<?>> x : temp2)
                        x.add(0, from);
                    if (result == null) result = temp2;
                    else result.addAll(temp2);
                }
            }
        }
        return result;
    }

    public static <T> List<GraphNode<?>> findBF(GraphNode<?> start, T lookingFor) {
        List<List<GraphNode<?>>> agenda = new ArrayList<>();
        List<GraphNode<?>> firstAgendaPath = new ArrayList<>(), resultPath;
        firstAgendaPath.add(start);
        agenda.add(firstAgendaPath);
        resultPath = findPathBF(agenda, null, lookingFor);
        Collections.reverse(resultPath);
        return resultPath;
    }

    public static <T> List<GraphNode<?>> findPathBF(List<List<GraphNode<?>>> agenda, List<GraphNode<?>> encountered, T lookingFor) {
        if (agenda.isEmpty()) return null;
        List<GraphNode<?>> nextPath = agenda.remove(0);
        GraphNode<?> currentNode = nextPath.get(0);
        if (currentNode.getData().equals(lookingFor)) return nextPath;
        if (encountered == null) encountered = new ArrayList<>();
        encountered.add(currentNode);
        for (GraphLink adjLink : currentNode.adjList)
            if (!encountered.contains(adjLink.destNode)) {
                List<GraphNode<?>> newPath = new ArrayList<>(nextPath);

                newPath.add(0,adjLink.destNode);
                agenda.add(newPath);
            }
        return findPathBF(agenda,encountered,lookingFor);
    }

    public static <T> CostedPath findCulturalPathDijkstra(GraphNode<?> startNode, T lookingfor){
        CostedPath cp = new CostedPath();
        List<GraphNode<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>();
        startNode.nodeValue = 0;
        unencountered.add(startNode);
        GraphNode<?> currentNode;

        do{
            currentNode = unencountered.remove(0);
            encountered.add(currentNode);

            if(currentNode.getData().equals(lookingfor)){
                cp.pathList.add(currentNode);
                cp.pathCost = currentNode.nodeValue;

                while(currentNode!=startNode){
                    boolean foundPrevPathNode = false;
                    for(GraphNode<?> n : encountered){
                        for(GraphLink e : n.adjList)
                            if(e.destNode == currentNode && currentNode.nodeValue - e.importance == n.nodeValue){
                                cp.pathList.add(0,n);
                                currentNode = n;
                                foundPrevPathNode = true;
                                break;
                            }
                        if(foundPrevPathNode) break;
                    }
                }
                for(GraphNode<?> n : encountered) n.nodeValue = Integer.MAX_VALUE;
                for(GraphNode<?> n : unencountered) n.nodeValue = Integer.MAX_VALUE;

                return cp;
            }
            for(GraphLink e : currentNode.adjList)
                if(!encountered.contains(e.destNode)){
                    e.destNode.nodeValue = Integer.min(e.destNode.nodeValue, currentNode.nodeValue + e.importance);
                    if(!unencountered.contains(e.destNode)) unencountered.add(e.destNode);
                }
            Collections.sort(unencountered,(n1,n2) -> n1.nodeValue-n2.nodeValue);
        }
        while (!unencountered.isEmpty());
        return null;
    }

    public static <T> CostedPath findShortestPathDijkstra(GraphNode<?> startNode, T lookingfor) {
        CostedPath cp = new CostedPath();
        List<GraphNode<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>();
        startNode.nodeValue = 0;
        unencountered.add(startNode);
        GraphNode<?> currentNode;

        do{
            currentNode = unencountered.remove(0);
            encountered.add(currentNode);

            if(currentNode.getData().equals(lookingfor)){
                cp.pathList.add(currentNode);
                cp.pathCost = currentNode.nodeValue;

                while(currentNode!=startNode){
                    boolean foundPrevPathNode = false;
                    for(GraphNode<?> n : encountered){
                        for(GraphLink e : n.adjList)
                            if(e.destNode == currentNode && currentNode.nodeValue - e.dist == n.nodeValue){
                                cp.pathList.add(0,n);
                                currentNode = n;
                                foundPrevPathNode = true;
                                break;
                            }
                        if(foundPrevPathNode) break;
                    }
                }
                for(GraphNode<?> n : encountered) n.nodeValue = Integer.MAX_VALUE;
                for(GraphNode<?> n : unencountered) n.nodeValue = Integer.MAX_VALUE;

                return cp;
            }
            for(GraphLink e : currentNode.adjList)
                if(!encountered.contains(e.destNode)){
                    e.destNode.nodeValue = Integer.min(e.destNode.nodeValue, currentNode.nodeValue + e.dist);
                    if(!unencountered.contains(e.destNode)) unencountered.add(e.destNode);
                }
            Collections.sort(unencountered,(n1,n2) -> n1.nodeValue-n2.nodeValue);
        }
        while (!unencountered.isEmpty());
        return null;
    }

}
