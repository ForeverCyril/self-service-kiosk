package org.ebu6304gp42.data.analysis;

import org.ebu6304gp42.data.Dish;
import org.ebu6304gp42.data.DishManager;

import java.util.*;

public class RecommenderSystem {
    private ArrayList<Dish> hotDish = null;

    public RecommenderSystem(){
        hotDish = DataAnalyser.getHotDish(2, false);
    }

    public void hotRecommend(){
        if(hotDish == null) return;;
        for(var dish: DishManager.getInstance().getDish()){
            dish.setHot(hotDish.contains(dish));
        }
    }

    public void sendRecommendEmail(){
        if(hotDish == null) return;
        System.out.println("Send");
        for(var dish:hotDish){
            var optionStatic = DataAnalyser.getDishOptionStatic(dish);
            System.out.println("Dish: " + dish.getName());
            getRecommend(optionStatic).forEach((option_name, sel_name)->{
                System.out.println(option_name + ": " + sel_name);
            });
            System.out.println();
        }
    }

    public static Map<String,String> getRecommend(HashMap<String, OptionData> optionData){
        Map<String,String> optionRecommend = new HashMap<>();
        for (String key : optionData.keySet()) {
            String option = (getKey((Map<String, Integer>) optionData.get(key), Integer.parseInt(getMaxValue((Map<String, Integer>) optionData.get(key)).toString()))).toString();
            optionRecommend.put(key,option);
        }
        System.out.println(optionRecommend);
        System.out.println("1");
        return optionRecommend;
    }

    private static Object getKey(Map<String,Integer> optionMap,Integer value){
        String option = null;
        for(Map.Entry<String,Integer> entry : optionMap.entrySet()){
            if(Objects.equals(value, entry.getValue())){
                option = entry.getKey();
            }
        }
        return option;
    }

    private static Object getMaxValue(Map<String,Integer> optionMap){
        if(optionMap.size()==0)
            return null;
        Collection<Integer> values = optionMap.values();
        Object[] options = values.toArray();
        Arrays.sort(options);
        return options[options.length-1];
    }
}
