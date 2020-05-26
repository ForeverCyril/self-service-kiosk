package org.ebu6304gp42.data;

import java.util.Date;
import java.util.ArrayList;

/**
 * Order data.
 */
public class Order {
    /**
     * Order Type.
     */
    public enum TYPE{TAKE_AWAY, EAT_IN }
    Date time;
    double price;
    ArrayList<OrderedDish> dish;
    String note;
    TYPE type;

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public Order(){
        price = 0;
        dish = new ArrayList<>();
    }

    /**
     * Get ordered dish
     * @return array list of ordered dish
     */
    public ArrayList<OrderedDish> getDish() {
        return dish;
    }

    /**
     * Add ordered dish
     * @param ordered_dish new ordered dish
     */
    public void addDish(OrderedDish ordered_dish) {
        price += ordered_dish.getTotalPrice();
        this.dish.add(ordered_dish);
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
