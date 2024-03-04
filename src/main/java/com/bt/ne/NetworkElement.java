package com.bt.ne;

class NetworkElement {
    String name;
    int processingTime;

    public NetworkElement(String name, int processingTime) {
        this.name = name;
        this.processingTime = processingTime;
    }

    public NetworkElement(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }
}