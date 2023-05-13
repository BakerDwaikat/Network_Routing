package com.example.demo5;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class GraphNode {
    private double coordinateX;
    private double coordinateY;
    private int name;
    private Circle circle;
    private ArrayList<Adjacent> adjacents = new ArrayList<>();

    public GraphNode(double coordinateX, double coordinateY, int name, Circle circle) {
        super();
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.name = name;
        this.circle = circle;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public ArrayList<Adjacent> getAdjacent() {
        return adjacents;
    }

    public void setAdjacent(ArrayList<Adjacent> adjacent) {
        this.adjacents = adjacent;
    }

    @Override
    public String toString() {
        return name + " -- (X : " + coordinateX + " * Y : " + coordinateY + ")";

    }

    public String fullToString() {
        return "City [coordinateX=" + coordinateX + ", coordinateY=" + coordinateY + ", name=" + name + ", circle="
                + circle + ", adjacent=" + adjacents + "]";

    }
}
