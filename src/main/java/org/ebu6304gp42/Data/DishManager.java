package org.ebu6304gp42.Data;

import com.google.gson.Gson;
import org.ebu6304gp42.Config.PathConfig;

import java.io.*;
import java.util.ArrayList;

public class DishManager {
    private static DishManager instance;
    ArrayList<Dish> dishList = new ArrayList<Dish>();

    public static DishManager getInstance(){
        if(instance == null){
            instance = new DishManager();
        }
        return instance;
    }

    private DishManager(){load();}

    public void save(){
        Gson gson= new Gson();

        StringBuilder data = new StringBuilder();
        for(var dish:dishList){
            data.append(gson.toJson(dish));
            data.append("\n");
        }

        try {
            File file = new File(PathConfig.getDishFile());
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter out = new FileWriter(file);
            out.write(data.toString());
            out.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void load(){
        File file = new File(PathConfig.getDishFile());
        if(!file.exists())return;
        Gson gson= new Gson();
        try {
            FileReader file_reader = new FileReader(file);
            BufferedReader in = new BufferedReader(file_reader);
            String line;
            while ((line = in.readLine()) != null){
                Dish dish = gson.fromJson(line, Dish.class);
                if(dish != null){
                    dishList.add(dish);
                }
            }
            in.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addDish(Dish dish){
        dishList.add(dish);
    }
    public void clear(){dishList.clear();}
    public ArrayList<Dish> getDish(){
        return dishList;
    }

}

