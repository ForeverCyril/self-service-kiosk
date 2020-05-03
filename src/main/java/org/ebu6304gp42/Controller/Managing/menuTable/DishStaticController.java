package org.ebu6304gp42.Controller.Managing.menuTable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.Component.Selectable.OptionStaticItem;
import org.ebu6304gp42.Component.SimpleToggle.SimpleToggle;
import org.ebu6304gp42.Component.SimpleToggle.SimpleToggleGroup;
import org.ebu6304gp42.Data.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DishStaticController {
    @FXML
    private PieChart chart;
    @FXML
    private VBox optionList;

    public void setDish(Dish dish) {
        staticData(dish);
        initOptionList();
        group.selectedToggleObjectProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == oldValue){
                return;
            }
            if(newValue != null){
                var chartData = data.get(newValue.getUserData().toString());
                chart.setTitle(newValue.getUserData().toString());
                setChartData(chartData);
            }
        });
        ((SimpleToggle)optionList.getChildren().get(0)).setSelect(true);
    }

    private final Map<String, OptionData> data = new HashMap<>();
    private void staticData(Dish dish){
        String dishName = dish.getName();
        for(var option:dish.getOptions()){
            var optionData = new OptionData(option);
            data.put(option.name, optionData);
        }
        List<Order> orders = OrderManager.getInstance().getOrders();
        // getOrderedDish that we want
        List<OrderedDish> result = new ArrayList<>();
        for(var order:orders){
            result.addAll(order.getDish().stream().
                    filter((OrderedDish o) -> o.getName().equals(dishName))
                    .collect(Collectors.toList())
            );
        }
        // static option
        for(var orderedDish:result){
            for(var opt:orderedDish.getOptions()){
                OptionData optionData = data.get(opt.getName());
                if(optionData == null){
                    optionData = new OptionData();
                    data.put(opt.getName(), optionData);
                }
                optionData.increase(opt.getSelected_option(), orderedDish.getAmount());
            }
        }
    }

    private final SimpleToggleGroup group = new SimpleToggleGroup();
    private void initOptionList(){
        for(var item:data.entrySet()){
            OptionStaticItem widget = new OptionStaticItem(item.getKey());
            widget.setUserData(item.getKey());
            widget.setToggleGroup(group);
            for(var optionData:item.getValue().getData().entrySet()){
                widget.addContent(String.format("%s(%d)", optionData.getKey(), optionData.getValue()));
            }
            optionList.getChildren().add(widget);
        }
    }

    private void setChartData(OptionData optionData){
        chart.getData().clear();
        for(var opt:optionData.getData().entrySet()){
            chart.getData().add(new PieChart.Data(opt.getKey(), opt.getValue()));
        }
    }

    static class OptionData{
        Map<String, Integer> data = new HashMap<>();
        public OptionData(){}
        public OptionData(DishOption option){
            init(option);
        }
        public void init(DishOption dishOption){
            for(var opt:dishOption.getOptions()){
                data.put(opt.option, 0);
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
}
