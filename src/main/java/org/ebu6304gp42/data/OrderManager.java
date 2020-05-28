package org.ebu6304gp42.data;

import com.google.gson.Gson;
import org.ebu6304gp42.config.PathConfig;

import java.io.*;
import java.util.ArrayList;

/**
 * Order manger. Please use getInstance instead of manually new one.
 */
public class OrderManager {
    private static OrderManager instance;
    private boolean changed = true;
    private final ArrayList<Order> orders;

    /**
     * It will auto load data from file when data changed
     * @return order list
     */
    public ArrayList<Order> getOrders() {
        if(changed){
            load();
            changed = false;
        }
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

    /**
     * Load data from file
     */
    synchronized public void load(){
        orders.clear();
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
        } catch (FileNotFoundException e){
            System.out.println("No Order Data Exist.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add order. It will write data in a new line at the end of the file
     * @param order new order
     */
    public void addOrder(Order order){
        Gson gson = new Gson();
        String data = gson.toJson(order);
        changed = true;
        if(data != null){
            try {
                File file = new File(PathConfig.getOrderFile());
                if(!file.exists()){
                    System.out.println("Order File Not Exist! Try to Create");
                    var res = file.createNewFile();
                    if(res){
                        System.out.println("Create Successfully!");
                    } else {
                        System.out.println("Create Failed!");
                    }
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
