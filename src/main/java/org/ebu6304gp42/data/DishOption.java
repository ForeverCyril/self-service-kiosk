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
    public void addOption(String name, double price, boolean enabled){
        options.add(new Option(name, price, enabled));
    }
    public void addOption(String name, double price){
        options.add(new Option(name, price, true));
    }
    public void addOption(String name){options.add(new Option(name, 0, true));}
    public ArrayList<Option> getOptions() {
        return options;
    }

    /**
     * Select option for dish option
     */
    static public class Option{
        private String option;
        private double price;
        private boolean enabled;

        /**
         * @param option name
         * @param price price
         * @param enabled option status
         */
        public Option(String option, double price, boolean enabled){
            this.option = option;
            this.price = price;
            this.enabled = enabled;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
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
