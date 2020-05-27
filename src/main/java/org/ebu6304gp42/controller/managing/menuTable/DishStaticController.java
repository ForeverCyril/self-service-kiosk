package org.ebu6304gp42.controller.managing.menuTable;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.component.selectable.OptionStaticItem;
import org.ebu6304gp42.component.simpleToggle.SimpleToggle;
import org.ebu6304gp42.component.simpleToggle.SimpleToggleGroup;
import org.ebu6304gp42.controller.managing.data.WeekDataController;
import org.ebu6304gp42.data.*;

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
    @FXML
    private Label dishNum;

    public HashMap<String,Integer> weekData = new HashMap<>();
    WeekDataController weekDataController = new WeekDataController();

    public void setDish(Dish dish) {
        weekData = weekDataController.weekData();
        dishNum.setText(Integer.toString(weekData.get(dish.getName())));

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
        if(!optionList.getChildren().isEmpty()) {
            ((SimpleToggle) optionList.getChildren().get(0)).setSelect(true);
        }
    }

    private final Map<String, OptionData> data = new HashMap<>();

    /**
     * Static option data for given dish
     * @param dish dish need to static
     */
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

}
