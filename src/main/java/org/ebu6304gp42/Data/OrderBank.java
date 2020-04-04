package org.ebu6304gp42.Data;

import com.google.gson.Gson;
import org.ebu6304gp42.Config.PathConfig;

import java.io.*;
import java.util.ArrayList;

public class OrderBank {
    private ArrayList<Order> orders;

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public OrderBank(){
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
                FileWriter out = new FileWriter(PathConfig.getOrderFile(), true);
                out.write(data+"\n");
                out.flush();
                out.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
