package org.ebu6304gp42.Data;

import java.util.ArrayList;

public class Dish {
    private String name;
    private double price;
    private String pic;
    private String description;
    private int remain;
    private boolean status;
    private String recomend;

    ArrayList<DishOption> options = new ArrayList<DishOption>();

    public Dish(){}

    public Dish(String name) {
        this.name = name;
    }

    public Dish(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public void copyFrom(Dish dish){
        name = dish.name;
        price = dish.price;
        pic = dish.pic;
        description = dish.description;
        remain = dish.remain;
        status = dish.status;
        options = dish.options;
        recomend = dish.recomend;
    }

    public boolean isAvailable(){
        return status && (remain > 0);
    }

    public void addOption(DishOption dishOption){
        options.add(dishOption);
    }
    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return this.description;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }

    public void setRemain(int remain){
        this.remain=remain;
    }
    public int getRemain(){
        return this.remain;
    }

    public void setStatus(boolean status){
        this.status = status;
    }
    public boolean getStatus(){
        return this.status;
    }

    public void setPrice(double price){
        this.price=price;
    }
    public double getPrice(){
        return this.price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRecomend() {
        return recomend;
    }

    public void setRecomend(String recomend) {
        this.recomend = recomend;
    }

    public ArrayList<DishOption> getOptions() {
        return options;
    }
    public void resetOptions(ArrayList<DishOption> list){
        options = list;
    }
    public String toString(){
        return "Name"+name+"Description"+description;
    }
}
