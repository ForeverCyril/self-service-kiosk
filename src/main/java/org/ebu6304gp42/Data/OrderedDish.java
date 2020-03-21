package org.ebu6304gp42.Data;

import java.util.ArrayList;
import javafx.util.Pair;

public class OrderedDish {
    private String name;
    private ArrayList<SelectedOption> options;
    private String note;
    private double price;
    private int amount;

    public OrderedDish(String name){
        this.name = name;
        options = new ArrayList<>();
    }
    public OrderedDish(){
        options = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SelectedOption> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<SelectedOption> options) {
        this.options = options;
    }

    public void addOption(String option_name, String option_selected){
        options.add(new SelectedOption(option_name, option_selected));
    }

    public void addAllOption(ArrayList<SelectedOption> inputOptions){
        this.options.addAll(inputOptions);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrice(){return price * amount;}
}


