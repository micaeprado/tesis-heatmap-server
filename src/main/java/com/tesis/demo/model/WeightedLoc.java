package com.tesis.demo.model;

public class WeightedLoc {

    protected Point location;
    protected double weight;

    public WeightedLoc() {
    }

    public Point getLocation() {
        return location;
    }

    public WeightedLoc location(Point location) {
        this.location = location;
        return this;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public double getWeight() {
        return weight;
    }

    public WeightedLoc weight(double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
