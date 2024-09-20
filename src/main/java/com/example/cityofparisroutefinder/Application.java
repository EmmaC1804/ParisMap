package com.example.cityofparisroutefinder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application extends javafx.application.Application {
    public static ArrayList<Landmark> landmarks = new ArrayList<>();
    public static ArrayList<Link> links = new ArrayList<>();
    public static ArrayList<GraphNode> graphnodes = new ArrayList<>();
    private static MainController mainController;
    static GraphNode<Landmark> a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("City Of Paris Route Finder");
        stage.setScene(scene);
        stage.show();
        mainController = fxmlLoader.getController();
        mainController.populateCombos();
    }

    public static void main(String[] args) {
        try {
            List<List<String>> data = new ArrayList<>();
            String file = "landmarks.csv";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //Reading until we run out of lines
            String line = br.readLine();
            while(line != null) {
                List<String> lineData = Arrays.asList(line.split(","));//splitting lines
                data.add(lineData);
                line = br.readLine();
            }

            //create Landmark objects and add to landmarks list
            for(List<String> list : data) {
                Landmark lm = new Landmark();
                Link link = new Link();
                int counter=0;

                for(String s : list){
                    // Links
                    if (s.equals("link")) {
                        break;
//                        if (counter == 1) link.setLandmark1(s);
//                        else if (counter == 2) link.setLandmark2(s);
//                        if (counter == 3) link.setDistance(Float.parseFloat(s));
//                        else if (counter == 4) link.setImportance(Integer.parseInt(s));
                    }

                    //Landmarks
                    else {
                        if (counter == 1) lm.setName(s);
                        else if (counter == 2) lm.setxCoord(Integer.parseInt(s));
                        else if (counter == 3) lm.setyCoord(Integer.parseInt(s));
                    }
                    counter++;
                }

                if(lm.getName()!=null) {
                    landmarks.add(lm);
                }
//                else if(link.getImportance()>0){
//                    links.add(link);
//                }
            }

            a = new GraphNode<>(landmarks.get(0));
            b = new GraphNode<>(landmarks.get(1));
            c = new GraphNode<>(landmarks.get(2));
            d = new GraphNode<>(landmarks.get(3));
            e = new GraphNode<>(landmarks.get(4));
            f = new GraphNode<>(landmarks.get(5));
            g = new GraphNode<>(landmarks.get(6));
            h = new GraphNode<>(landmarks.get(7));
            i = new GraphNode<>(landmarks.get(8));
            j = new GraphNode<>(landmarks.get(9));
            k = new GraphNode<>(landmarks.get(10));
            l = new GraphNode<>(landmarks.get(11));
            m = new GraphNode<>(landmarks.get(12));
            n = new GraphNode<>(landmarks.get(13));
            o = new GraphNode<>(landmarks.get(14));
            p = new GraphNode<>(landmarks.get(15));
            q = new GraphNode<>(landmarks.get(16));
            r = new GraphNode<>(landmarks.get(17));
            s = new GraphNode<>(landmarks.get(18));
            t = new GraphNode<>(landmarks.get(19));
            u = new GraphNode<>(landmarks.get(20));
            v = new GraphNode<>(landmarks.get(21));

            graphnodes.add(a);
            graphnodes.add(b);
            graphnodes.add(c);
            graphnodes.add(d);
            graphnodes.add(e);
            graphnodes.add(f);
            graphnodes.add(g);
            graphnodes.add(h);
            graphnodes.add(i);
            graphnodes.add(j);
            graphnodes.add(k);
            graphnodes.add(l);
            graphnodes.add(m);
            graphnodes.add(n);
            graphnodes.add(o);
            graphnodes.add(p);
            graphnodes.add(q);
            graphnodes.add(r);
            graphnodes.add(s);
            graphnodes.add(t);
            graphnodes.add(u);
            graphnodes.add(v);

            t.connectToNodeUndirected(c,4, 30);
            c.connectToNodeUndirected(s,7,14);
            s.connectToNodeUndirected(a,1,16);
            s.connectToNodeUndirected(m,8,3);
            m.connectToNodeUndirected(i,9,23);
            i.connectToNodeUndirected(o,2,11);
            i.connectToNodeUndirected(j,9,6);
            j.connectToNodeUndirected(e,5,12);
            e.connectToNodeUndirected(v,6,41);
            j.connectToNodeUndirected(p,3,4);
            p.connectToNodeUndirected(g,4,3);
            g.connectToNodeUndirected(b,5,10);
            b.connectToNodeUndirected(f,5,8);
            f.connectToNodeUndirected(n,9,17);
            n.connectToNodeUndirected(r,1,39);
            b.connectToNodeUndirected(h,8,9);
            h.connectToNodeUndirected(d,7,6);
            d.connectToNodeUndirected(k,3,9);
            h.connectToNodeUndirected(q,2,11);
            q.connectToNodeUndirected(l,7,11);
            l.connectToNodeUndirected(u,2,13);
            t.connectToNodeUndirected(a,3,22);
            a.connectToNodeUndirected(o,6,20);
            o.connectToNodeUndirected(g,7,10);
            o.connectToNodeUndirected(q,1,18);
            q.connectToNodeUndirected(k,8,6);
            q.connectToNodeUndirected(u,4,14);
            u.connectToNodeUndirected(r,8,49);
            e.connectToNodeUndirected(b,3,13);
            d.connectToNodeUndirected(n,8,16);
            n.connectToNodeUndirected(v,2,32);

            br.close();

        }

        catch(Exception e)
        {
            System.out.print(e);
        }
        launch();
    }
}