package com.example.cityofparisroutefinder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.DomDriver;

public class MainController {
    public ImageView mapImgView;
//    private ArrayList<GraphNode> landmarks;
    private ArrayList<Landmark> avoidList = new ArrayList<>();
    private ArrayList<Landmark> visitList = new ArrayList<>();
    String s = "";
    @FXML
    private ComboBox<Landmark> fromCombo, toCombo, avoidCombo, visitCombo;
    @FXML
    private TextArea avoidTA, visitTA;
    @FXML
    private ComboBox<Integer> noRoutesBox;
    @FXML
    private Button r1button, r2button, r3button, r4button, r5button;
    Landmark fromLM, toLM, visitLM;
    List<List<GraphNode<?>>> allPaths;
    List<GraphNode<?>> path;
    CostedPath cpa;

    public void initialize(){
//        drawCircle();
    }

//    public void getCord(MouseEvent mouseEvent){
//        System.out.println("X: " + mouseEvent.getX() + ", Y: " + mouseEvent.getY());
//    }

    public void populateCombos(){
        for (Landmark lm : Application.landmarks) {
            fromCombo.getItems().add(lm);
            toCombo.getItems().add(lm);
            avoidCombo.getItems().add(lm);
            visitCombo.getItems().add(lm);
        }
        noRoutesBox.getItems().addAll(1,2,3,4,5);
    }

    @FXML
    private void addToAvoidList(){
        if(avoidList.contains(avoidCombo.getValue())) return;
        avoidList.add(avoidCombo.getValue());
        s=avoidList.get(0).getName();
        if (avoidList.size()>1) {
            boolean first=true;
            for (Landmark lm : avoidList) {
                if(!first) s += ", " + lm.getName();
                first = false;
            }
        }
        avoidTA.setText(s);
    }

    @FXML
    private void addToVisitList(){
        if(visitList.contains(visitCombo.getValue())) return;
        visitList.add(visitCombo.getValue());
        s=visitList.get(0).getName();
        if (visitList.size()>1) {
            boolean first=true;
            for (Landmark lm : visitList) {
                if(!first) s += ", " + lm.getName();
                first = false;
            }
        }
        visitTA.setText(s);
    }
    public void drawCircle(int x, int y, Color col) {
        Ellipse ellipse = new Ellipse(x,y,5,5);
        ellipse.setFill(col);
        ((Pane) mapImgView.getParent()).getChildren().add(ellipse);
    }

    private void drawLine(int x1,int y1,int x2, int y2){
        Line line = new Line(x1,y1,x2,y2);
        ((Pane) mapImgView.getParent()).getChildren().add(line);
    }

    private void searchWaypoints(Landmark first, Landmark second){
        // Get from node
        GraphNode<Landmark> fromNode=null;
        while(fromNode==null){
            for(GraphNode gn : Application.graphnodes){
                if(gn.getData() == first){
                    fromNode = gn;
                }
            }
        }

        // Find all paths
        allPaths = Search.findAllPathsDF(fromNode, null, second);


        // display wanted num routes
        enableRouteButtons(1);

        System.out.println("\nfrom " + first.getName() + " to " + second.getName() + "\n-------");
        for (GraphNode<?> n : allPaths.get(0)) {
            System.out.println(n.getData());
        }

        showRoute(1, false);
    }

    @FXML
    private void search() {
        if(fromCombo.getValue()==null || toCombo.getValue()==null) return;

        fromLM = fromCombo.getValue();
        toLM = toCombo.getValue();
        boolean stop = false;

        if(visitList.size()>0){
            ((Pane) mapImgView.getParent()).getChildren().removeIf(e -> e instanceof Ellipse);
            ((Pane) mapImgView.getParent()).getChildren().removeIf(e -> e instanceof Line);
            System.out.println("------------------------------------------");

            if(visitList.size()==1){
                visitLM = visitList.get(0);
                searchWaypoints(fromLM,visitLM);
                searchWaypoints(visitLM,toLM);
                drawCircle(visitLM.getxCoord(), visitLM.getyCoord(), Color.BLUE);
            }

            else {
                for(int i=0; i< visitList.size(); i++){
                    visitLM = visitList.get(i);
                    searchWaypoints(fromLM,visitLM);
                    drawCircle(visitLM.getxCoord(), visitLM.getyCoord(), Color.BLUE);
                }
                searchWaypoints(visitLM,toLM);
            }

            stop = true;
        }

        if(stop) return;
        // Print
        System.out.println("All solution paths from " + fromLM);
        System.out.println("to " + toLM);
        System.out.println("------------------------------------------");

        // Get from node
        GraphNode<Landmark> fromNode=null;
        while(fromNode==null){
            for(GraphNode gn : Application.graphnodes){
                if(gn.getData() == fromLM){
                    fromNode = gn;
                }
            }
        }

        // Find all paths
        allPaths = Search.findAllPathsDF(fromNode, null, toLM);

        // display wanted num routes
        int pathCounter=1;
        int loopCounter=0;
        int numRoutesToFind;
        if(noRoutesBox.getValue()==null) numRoutesToFind=1;
        else numRoutesToFind=noRoutesBox.getValue();
        enableRouteButtons(numRoutesToFind);

        while (loopCounter < numRoutesToFind && loopCounter < allPaths.size()) {
            System.out.println("\nPath " + (pathCounter++) + "\n--------");
            for (GraphNode<?> n : allPaths.get(loopCounter)) {
                System.out.println(n.getData());
            }
            loopCounter++;
        }

        showRoute(1, true);
    }

    @FXML
    private void clearVisit(){
        visitList.clear();
        visitTA.clear();
    }

    @FXML
    private void clearAvoid(){
        avoidList.clear();
        avoidTA.clear();
    }

    @FXML
    private void shortestBF(){
        if(fromCombo.getValue()==null || toCombo.getValue()==null) return;
        fromLM = fromCombo.getValue();
        toLM = toCombo.getValue();

        System.out.println("The (shortest) breath-first search solution path from " + fromLM);
        System.out.println("to " + toLM);
        System.out.println("-----------------");

        GraphNode<Landmark> fromNode=null;
        while(fromNode==null){
            for(GraphNode gn : Application.graphnodes){
                if(gn.getData() == fromLM){
                    fromNode = gn;
                }
            }
        }

        List<GraphNode<?>> bfsPath = Search.findBF(fromNode,toLM);
        for(GraphNode<?> n : bfsPath) System.out.println(n.getData());
        drawBFRoute(bfsPath);

    }

    private void enableRouteButtons(int n){
        r1button.setDisable(true);
        r2button.setDisable(true);
        r3button.setDisable(true);
        r4button.setDisable(true);
        r5button.setDisable(true);

        if(n>1){
            r1button.setDisable(false);
            r2button.setDisable(false);
            if (n>2){
                r3button.setDisable(false);
                if (n>3){
                    r4button.setDisable(false);
                    if (n>4) r5button.setDisable(false);
                }
            }
        }
    }

    @FXML
    private void b1pressed(){
        showRoute(1, true);
    }@FXML
    private void b2pressed(){
        showRoute(2, true);
    }@FXML
    private void b3pressed(){
        showRoute(3, true);
    }@FXML
    private void b4pressed(){
        showRoute(4, true);
    }@FXML
    private void b5pressed(){
        showRoute(5, true);
    }

    private void showRoute(int route, boolean clear){
        // clear map
        if(clear) {
            ((Pane) mapImgView.getParent()).getChildren().removeIf(e -> e instanceof Ellipse);
            ((Pane) mapImgView.getParent()).getChildren().removeIf(e -> e instanceof Line);
        }

        // draw circles
        drawCircle(fromLM.getxCoord(), fromLM.getyCoord(), Color.RED);
        drawCircle(toLM.getxCoord(), toLM.getyCoord(), Color.RED);

        // draw routes
        int prevX=0;
        int prevY=0;
        for (GraphNode<?> n : allPaths.get(route)) {
            Landmark l = (Landmark) n.getData();
            if(prevX!=0)
                drawLine(prevX, prevY, l.getxCoord(), l.getyCoord());
            prevX = l.getxCoord();
            prevY = l.getyCoord();
        }
    }

    private void showSingleRoute(){
        // clear map
        ((Pane) mapImgView.getParent()).getChildren().removeIf(e->e instanceof Ellipse);
        ((Pane) mapImgView.getParent()).getChildren().removeIf(e->e instanceof Line);

        // draw circles
        drawCircle(fromLM.getxCoord(), fromLM.getyCoord(), Color.RED);
        drawCircle(toLM.getxCoord(), toLM.getyCoord(), Color.RED);

        // draw routes
        int prevX=0;
        int prevY=0;
        for (GraphNode<?> n : cpa.pathList) {
            Landmark l = (Landmark) n.getData();
            if(prevX!=0)
                drawLine(prevX, prevY, l.getxCoord(), l.getyCoord());
            prevX = l.getxCoord();
            prevY = l.getyCoord();
        }
    }

    private void drawBFRoute(List<GraphNode<?>> list){
        // clear map
        ((Pane) mapImgView.getParent()).getChildren().removeIf(e->e instanceof Ellipse);
        ((Pane) mapImgView.getParent()).getChildren().removeIf(e->e instanceof Line);

        // draw circles
        drawCircle(fromLM.getxCoord(), fromLM.getyCoord(), Color.RED);
        drawCircle(toLM.getxCoord(), toLM.getyCoord(), Color.RED);

        // draw routes
        int prevX=0;
        int prevY=0;
        for (GraphNode<?> n :list ) {//wrong <-
            Landmark l = (Landmark) n.getData();
            if(prevX!=0)
                drawLine(prevX, prevY, l.getxCoord(), l.getyCoord());
            prevX = l.getxCoord();
            prevY = l.getyCoord();
        }
    }


    public void Cultural() {
        if(fromCombo.getValue()==null || toCombo.getValue()==null) return;
        fromLM = fromCombo.getValue();
        toLM = toCombo.getValue();

        // Print
        System.out.println("Most cultural path from " + fromLM);
        System.out.println("to " + toLM);
        System.out.println("------------------------------------------");

        // Get from node
        GraphNode<Landmark> fromNode=null;
        while(fromNode==null){
            for(GraphNode gn : Application.graphnodes){
                if(gn.getData() == fromLM){
                    fromNode = gn;
                }
            }
        }
        cpa = Search.findCulturalPathDijkstra(fromNode,toLM);

        for(GraphNode<?> n : cpa.pathList)
            System.out.println(n.getData());
        System.out.println("\nThe total cultural cost is: " + cpa.pathCost);
        showSingleRoute();
    }

    public void shortestDijkstra() {
        if(fromCombo.getValue()==null || toCombo.getValue()==null) return;
        fromLM = fromCombo.getValue();
        toLM = toCombo.getValue();

        // Print
        System.out.println("Dijkstra shortest path from " + fromLM);
        System.out.println("to " + toLM);
        System.out.println("------------------------------------------");

        // Get from node
        GraphNode<Landmark> fromNode=null;
        while(fromNode==null){
            for(GraphNode gn : Application.graphnodes){
                if(gn.getData() == fromLM){
                    fromNode = gn;
                }
            }
        }
        cpa = Search.findShortestPathDijkstra(fromNode,toLM);

        for(GraphNode<?> n : cpa.pathList)
            System.out.println(n.getData());
        System.out.println("\nThe total distance is: " + cpa.pathCost);
        showSingleRoute();
    }
}
