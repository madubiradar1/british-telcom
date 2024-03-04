package com.bt.ne;

class Links {
    String source;
    String destination;
    int price; // in pence
    
    public Links(String source, String destination, int price) {
        this.source = source;
        this.destination = destination;
        this.price = price;
    }

    public Links(){}
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}