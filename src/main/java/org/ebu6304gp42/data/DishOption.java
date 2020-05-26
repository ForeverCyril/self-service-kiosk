package org.ebu6304gp42.data;

import java.util.ArrayList;

/**
 * Option For Dish
 */
public class DishOption {
    public String name;
    ArrayList<Option> options;

    public DishOption(){
        options = new ArrayList<>();
    }

    /**
     * @param name option name
     */
    public DishOption(String name){
        this.name = name;
        options = new ArrayList<>();
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName() {
        return this.name;
    }
    public void addOption(String name, double price){
        options.add(new Option(name, price));
    }
    public void addOption(String name){options.add(new Option(name, 0));}
    public ArrayList<Option> getOptions() {
        return options;
    }

    /**
     * Select option for dish option
     */
    static public class Option{
        public String option;
        public double price;

        /**
         * @param option name
         * @param price price
         */
        public Option(String option, double price){
            this.option = option;
            this.price = price;
        }

        @Override
        public String toString() {
            if(price == 0){
                return option;
            }
            return String.format("%s (%.2f)", option, price);
        }
    }
}
