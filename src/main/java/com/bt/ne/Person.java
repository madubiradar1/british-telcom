package com.bt.ne;

class Person {
    String name;
    String exchange;

    public Person(String name, String exchange) {
        this.name = name;
        this.exchange = exchange;
    }

    public Person(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    Person findPersonByName(String name) {
        return null;
    }
}