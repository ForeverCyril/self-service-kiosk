package org.ebu6304gp42.data;

import java.util.*;

public class DishStatistic{
    HashMap<Dish, Integer> data = new HashMap<>();

    public static void statisticDish(){
        DishStatistic dishStatistic = new DishStatistic();
        dishStatistic.dishRecommend();
    }

    private void loadData(){
        for(Dish dish:DishManager.getInstance().getDish()){
            data.put(dish, 0);
        }
        for(var order:OrderManager.getInstance().getOrders()){
            for(var dish:order.getDish()){
                data.put(dish.getDish(),
                        data.containsKey(dish.getDish())?data.get(dish.getDish())+1:0);
            }
        }
    }

    private void dishRecommend(){
        loadData();
        if(data.isEmpty()) return;
        ArrayList<Map.Entry<Dish, Integer>> list = new ArrayList<>(data.entrySet());
        System.out.println(data);
        Collections.sort(list, (a, b)->{
            return b.getValue()-a.getValue();
        });
        ArrayList<Dish> hotDish = new ArrayList<>();
        for(int i=0;i<3 && i < list.size();i++){
            hotDish.add(list.get(i).getKey());
        }
        for (int i = 3;i<list.size() && list.get(i).getValue().equals(list.get(i - 1).getValue()); i++) {
            hotDish.add(list.get(i).getKey());
        }
        System.out.println("Hot Dish: " + hotDish.toString());
        for(var dish:DishManager.getInstance().getDish()){
            setHotRecommend(dish, hotDish.contains(dish));
        }
    }

    private void setHotRecommend(Dish dish, boolean isHot){
        var data = dish.getRecommend();
        if (data == null){
            if(isHot){
                dish.setRecommend("hot");
            }
        } else {
            ArrayList<String> recs = new ArrayList<>(Arrays.asList(dish.getRecommend()));
            recs.remove("hot");
            if (isHot) {
                recs.add("hot");
            }
            dish.setRecommend(String.join(";", recs));
        }
    }
}
