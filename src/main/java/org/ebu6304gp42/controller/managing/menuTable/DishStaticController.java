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
import org.ebu6304gp42.data.analysis.DataAnalyser;
import org.ebu6304gp42.data.analysis.OptionData;

import java.util.HashMap;
import java.util.Map;

public class DishStaticController {
    @FXML
    private PieChart chart;
    @FXML
    private VBox optionList;
    @FXML
    private Label dishNum;

    public HashMap<String,Integer> weekData = new HashMap<>();
    WeekDataController weekDataController = new WeekDataController();


    private Map<String, OptionData> data = new HashMap<>();

    public void setDish(Dish dish) {
        weekData = weekDataController.weekData();
        dishNum.setText(Integer.toString(weekData.get(dish.getName())));

        data = DataAnalyser.getDishOptionStatic(dish);
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
