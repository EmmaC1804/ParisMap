package com.example.cityofparisroutefinder;

public class Link {
    Landmark landmark1, landmark2;
    int distance;
    int importance;

    public Landmark getLandmark1() {
        return landmark1;
    }

    public void setLandmark1(Landmark landmark1) {
        this.landmark1 = landmark1;
    }

    public Landmark getLandmark2() {
        return landmark2;
    }

    public void setLandmark2(Landmark landmark2) {
        this.landmark2 = landmark2;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }
}
