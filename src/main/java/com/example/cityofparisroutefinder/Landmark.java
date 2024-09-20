package com.example.cityofparisroutefinder;

public class Landmark {
    private String name;
    private int xCoord, yCoord;

    public void setName(String name){
        this.name=name;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }


    public String getName() {
        return name;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }


    public String toString(){
        return name;
    }

}
