package com.academy.model;

/**
 * Created by Daniel Palonek on 2016-12-12.
 */
public class ChartData {

    private String x;
    private long y;

    public ChartData(String x, long y) {
        this.x = x;
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

}
