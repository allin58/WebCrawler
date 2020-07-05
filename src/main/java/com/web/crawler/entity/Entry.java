package com.web.crawler.entity;


import java.util.Arrays;

public class Entry implements Comparable<Entry>{

    String[] strings;
    Integer weight;

    public Entry(String[] strings) {
        this.strings = strings;
        weight =0;
        for (int i = 1; i < strings.length; i++) {

            weight = weight + Integer.parseInt(strings[i]);
        }
    }

    public String[] getStrings() {
        return strings;
    }

    public void setStrings(String[] strings) {
        this.strings = strings;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
         return  Arrays.toString(strings);
    }

    @Override
    public int compareTo(Entry o) {
        return o.getWeight().compareTo(weight);
    }
}
