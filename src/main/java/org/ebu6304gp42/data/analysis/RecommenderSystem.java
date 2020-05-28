package org.ebu6304gp42.data.analysis;

import org.ebu6304gp42.component.output.PrintInfo;
import org.ebu6304gp42.data.Account;
import org.ebu6304gp42.data.AccountManager;
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
        StringBuilder content = new StringBuilder();
        content.append("Dish Recommend of This Week\n\n");
        for(var dish:hotDish){
            var optionStatic = DataAnalyser.getDishOptionStatic(dish);
            content.append("Hot Dish: ").append(dish.getName());
            content.append("\nMost Popular Option:\n");
            getRecommend(optionStatic).forEach((option_name, sel_name)->{
                content.append("    ").append(option_name).append(": ").append(sel_name).append("\n");
            });
            content.append("\n\n");
        }
        System.out.println("Recommend Content: \n" + content.toString());
        for(var acc: AccountManager.getInstance().getAccount()){
            if (acc.isAccept_rec()){
                PrintInfo.sendEmailSMS(acc, content.toString());
            }
        }
    }

    public Map<String,String> getRecommend(Map<String, OptionData> data){
        Map<String,String> optionRecommend = new HashMap<>();
        for (String key : data.keySet()) {
            OptionData optionData = data.get(key);
            String option = (getKey(optionData.getData(), Integer.parseInt(getMaxValue(optionData.getData()).toString()))).toString();
            optionRecommend.put(key,option);
        }
        return optionRecommend;
    }

    private static Object getKey(Map<String,Integer> optionMap,Integer value){
        String option = null;
        for(Map.Entry<String,Integer> entry : optionMap.entrySet()){
            if(value == entry.getValue()){
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
