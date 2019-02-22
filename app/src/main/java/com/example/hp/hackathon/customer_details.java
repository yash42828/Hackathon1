package com.example.hp.hackathon;
import java.util.ArrayList;

public class customer_details {
    private ArrayList<String> item;
    private ArrayList<String> price;
    private ArrayList<String> weight;



    public customer_details(ArrayList<String> i, ArrayList<String> p, ArrayList<String> w) {
        item = i;
        price = p;
        weight = w;
    }

    public ArrayList<String> getItem() {
        return item;
    }

    public ArrayList<String> getPrice() {
        return price;
    }

    public ArrayList<String> getWeight() {
        return weight;
    }
}
