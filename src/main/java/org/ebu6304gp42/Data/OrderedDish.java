package org.ebu6304gp42.Data;

import java.util.ArrayList;

public class OrderedDish {
    private String name;
    private ArrayList<SelectedOption> options;
    private String note;
    private double price;
    private int amount;
    private Dish dish;

    public OrderedDish(Dish dish){
        this.dish = dish;
        price = dish.getPrice();
        name = dish.getName();
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

    public void addOption(String option_name, String option_selected, double price){
        options.add(new SelectedOption(option_name, option_selected, price));
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
        double sum = price;
        for(var option:options) {
            sum += option.getPrice();
        }
        return sum;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrice(){return getPrice() * amount;}

    public static class SelectedOption{
        private String name;
        private String selected_option;
        private double price;

        public SelectedOption(String name, String selected_option, double price) {
            this.name = name;
            this.selected_option = selected_option;
            this.price = price;
        }

        public SelectedOption() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getSelected_option() {
            return selected_option;
        }

        public void setSelected_option(String selected_option) {
            this.selected_option = selected_option;
        }

        @Override
        public String toString() {
            if(price == 0){
                return String.format("%s: %s", name, selected_option);
            } else {
                return String.format("%s: %s(%.2f)", name, selected_option, price);
            }
        }
    }
}


