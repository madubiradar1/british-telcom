package com.bt.ne;

import java.util.List;

class Route {
    List<String> path;
    int totalProcessingTime;
    int totalPrice;

    public Route(List<String> path, int totalProcessingTime, int totalPrice) {
        this.path = path;
        this.totalProcessingTime = totalProcessingTime;
        this.totalPrice = totalPrice;
    }

    public Route(){}

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public int getTotalProcessingTime() {
        return totalProcessingTime;
    }

    public void setTotalProcessingTime(int totalProcessingTime) {
        this.totalProcessingTime = totalProcessingTime;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
