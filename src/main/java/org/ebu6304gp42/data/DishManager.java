package org.ebu6304gp42.data;

import com.google.gson.Gson;
import org.ebu6304gp42.config.PathConfig;

import java.io.*;
import java.util.ArrayList;

/**
 * Manager Dish Data
 */
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

    /**
     * Save Data into file that config in {@link PathConfig}
     */
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
                System.out.print("Dish File Not Exist. Try to Create");
                var res = file.createNewFile();
                if(res){
                    System.out.println("Create Successfully!");
                } else {
                    System.out.println("Create Failed!");
                }
            }
            FileWriter out = new FileWriter(file);
            out.write(data.toString());
            out.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Load Data from file, it will auto load when the class creating.
     */
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

    /**
     * Add a new dish
     * @param dish new dish
     */
    public void addDish(Dish dish){
        dishList.add(dish);
    }

    /**
     * Clear All dish
     */
    public void clear(){dishList.clear();}

    /**
     * Get All dish
     * @return dish arraylist
     */
    public ArrayList<Dish> getDish(){
        return dishList;
    }

}

