package org.ebu6304gp42.Data;

import com.google.gson.Gson;
import org.ebu6304gp42.Config.PathConfig;

import java.io.*;
import java.util.ArrayList;

public class OrderManager {
    private static OrderManager instance;
    private ArrayList<Order> orders;

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public static OrderManager getInstance() {
        if(instance == null){
            instance = new OrderManager();
        }
        return instance;
    }

    private OrderManager(){
        orders = new ArrayList<>();
    }
    public void load(){
        try {
            FileReader file = new FileReader(PathConfig.getOrderFile());
            BufferedReader in = new BufferedReader(file);
            String line;
            Gson gson = new Gson();
            while ((line = in.readLine()) != null){
                Order order = gson.fromJson(line, Order.class);
                if(order != null){
                    orders.add(order);
                }
            }
            in.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addOrder(Order order){
        Gson gson = new Gson();
        String data = gson.toJson(order);

        if(data != null){
            try {
                File file = new File(PathConfig.getOrderFile());
                if(!file.exists()){
                    file.createNewFile();
                }
                FileWriter out = new FileWriter(file, true);
                out.write(data+"\n");
                out.flush();
                out.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
