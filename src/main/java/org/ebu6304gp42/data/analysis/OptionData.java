package org.ebu6304gp42.data.analysis;

import org.ebu6304gp42.data.DishOption;

import java.util.HashMap;
import java.util.Map;

/**
 * Store the static for an option.
 */
public class OptionData {
    Map<String, Integer> data = new HashMap<>();
    public OptionData(){}
    public OptionData(DishOption option){
        init(option);
    }
    public void init(DishOption dishOption){
        for(var opt:dishOption.getOptions()){
            data.put(opt.getOption(), 0);
        }
    }
    public void increase(String name, int amount){
        Integer oldValue = data.get(name);
        if(oldValue == null){
            oldValue = 0;
        }
        data.put(name, oldValue + amount);
    }
    public Map<String, Integer> getData(){return data;}
}
