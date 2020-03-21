package org.ebu6304gp42.Data;

import java.util.ArrayList;

public class DishOption {
    public String name;
    ArrayList<String> options;

    public DishOption(){
        options = new ArrayList<>();
    }

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
    public void addOption(String name){
        options.add(name);
    }
}
