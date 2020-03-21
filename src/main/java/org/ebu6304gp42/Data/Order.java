package org.ebu6304gp42.Data;

import java.util.Date;
import java.util.ArrayList;
public class Order {
    Date time;
    double price;
    ArrayList<OrderedDish> dish;
    String note;

    public Order(){
        price = 0;
        dish = new ArrayList<>();
    }

    public ArrayList<OrderedDish> getDish() {
        return dish;
    }
    public void addDish(OrderedDish ordered_dish) {
        price += ordered_dish.getTotalPrice();
        this.dish.add(ordered_dish);
    }
    public void deleteDish(int index) {
        price -= dish.get(index).getTotalPrice();
        this.dish.remove(index);
    }

    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    public String toString() {
        return note;
    }
}
