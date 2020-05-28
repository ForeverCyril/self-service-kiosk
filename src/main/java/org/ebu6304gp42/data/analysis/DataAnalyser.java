package org.ebu6304gp42.data.analysis;

import org.ebu6304gp42.data.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provide the function to Analyser Data.
 */
public class DataAnalyser {
    /**
     * Static option data for given dish
     * @param dish dish need to static
     */
    public static HashMap<String, OptionData> getDishOptionStatic(Dish dish){
        var result = new HashMap<String, OptionData>();
        //Load All Dish Option, in case some option has not been chosen.
        final String dishName = dish.getName();
        for(var option:dish.getOptions()){
            var optionData = new OptionData(option);
            result.put(option.name, optionData);
        }

        // Filter Target Data
        ArrayList<OrderedDish> targetData = new ArrayList<>();
        for(var order:OrderManager.getInstance().getOrders()){
            targetData.addAll(
                    order.getDish().stream().
                    filter((OrderedDish o) -> o.getName().equals(dishName))
                    .collect(Collectors.toList())
            );
        }

        // Static
        for(var orderDish:targetData){
            for(var option:orderDish.getOptions()){
                OptionData optionData = result.get(option.getName());
                if(optionData == null){
                    optionData = new OptionData();
                    result.put(option.getName(), optionData);
                }
                optionData.increase(option.getSelected_option(), orderDish.getAmount());
            }
        }
        return result;
    }

    /**
     * Get Hot Dish
     * @param num how many dish you want
     * @param keep_equal keep the dish has same static, but out of the number you want
     * @return hot dish list, return empty list when no data found.
     */
    public static ArrayList<Dish> getHotDish(int num, boolean keep_equal){
        HashMap<Dish, Integer> data = new HashMap<>();
        // Load Data
        for(Dish dish:DishManager.getInstance().getDish()){
            data.put(dish, 0);
        }
        for(var order:OrderManager.getInstance().getOrders()){
            for(var dish:order.getDish()){
                data.put(dish.getDish(),
                        data.containsKey(dish.getDish())?data.get(dish.getDish())+1:0);
            }
        }
        if(data.isEmpty()) return new ArrayList<>(); // return empty list when no data

        // Sort Data
        ArrayList<Map.Entry<Dish, Integer>> list = new ArrayList<>(data.entrySet());
        list.sort((a, b) -> {
            return b.getValue() - a.getValue();
        });

        ArrayList<Dish> hotDish = new ArrayList<>();
        for(int i=0;i<num && i < list.size();i++){
            hotDish.add(list.get(i).getKey());
        }
        // keep all dish with same data
        if(keep_equal){
            for (int i = 3;i<list.size() && list.get(i).getValue().equals(list.get(i - 1).getValue()); i++) {
                hotDish.add(list.get(i).getKey());
            }
        }
        return hotDish;
    }
}
