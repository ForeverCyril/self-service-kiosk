package org.ebu6304gp42.Data;

import com.google.gson.Gson;
import org.ebu6304gp42.Config.PathConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DishBank {
    ArrayList<Dish> dishList = new ArrayList<Dish>();
    public DishBank(){}

    public void save(){
        Gson gson= new Gson();

        StringBuilder data = new StringBuilder();
        for(var dish:dishList){
            data.append(gson.toJson(dish));
            data.append("\n");
        }

        try {
            FileWriter out = new FileWriter(PathConfig.DISH_FILE);
            out.write(data.toString());
            out.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void load(){
        Gson gson= new Gson();
        try {
            FileReader file = new FileReader(PathConfig.DISH_FILE);
            BufferedReader in = new BufferedReader(file);
            String line;
            while ((line = in.readLine()) != null){
                Dish dish = gson.fromJson(line, Dish.class);
                if(dish != null){
                    dishList.add(dish);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addDish(Dish dish){
        dishList.add(dish);
    }

    public ArrayList<Dish> getDish(){
        return dishList;
    }

}

