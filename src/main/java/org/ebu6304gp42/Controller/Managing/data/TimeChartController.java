package org.ebu6304gp42.Controller.Managing.data;

import javafx.fxml.FXML;

import java.io.IOException;
import java.util.Calendar;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import org.ebu6304gp42.Data.Order;
import org.ebu6304gp42.Data.OrderManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class TimeChartController implements Initializable{
    @FXML
    private LineChart<String,Integer> TimeChart;
    @FXML
    Node root;

    public int lunch_Num = 0;
    public int dinner_Num = 0;
    public int normal_Num = 0;
    public int night_Num = 0;
    public int breakfast_Num = 0;
    Calendar cal = Calendar.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statisticsWay();
        LineChart.Series<String,Integer> timeChart = new LineChart.Series<String,Integer>();
        timeChart.setName("Time Distribution");
        timeChart.getData().add(new XYChart.Data<String, Integer>("Breakfast",breakfast_Num));
        timeChart.getData().add(new XYChart.Data<String, Integer>("Lunch", lunch_Num));
        timeChart.getData().add(new XYChart.Data<String, Integer>("Dinner",dinner_Num));
        timeChart.getData().add(new XYChart.Data<String, Integer>("Night",night_Num));
        timeChart.getData().add(new XYChart.Data<String,Integer>("Normal Time",normal_Num));
        TimeChart.getData().add(timeChart);
        TimeChart.createSymbolsProperty();
    
    }

    public void statisticsWay(){
        for(Order orders: OrderManager.getInstance().getOrders()){
            cal.setTime(orders.getTime());
            if(cal.get(Calendar.HOUR_OF_DAY)>=6 && cal.get(Calendar.HOUR_OF_DAY)<9){
                breakfast_Num++;
            }
            if(cal.get(Calendar.HOUR_OF_DAY)>=11 && cal.get(Calendar.HOUR_OF_DAY)<14){
                lunch_Num++;
            }
            if(cal.get(Calendar.HOUR_OF_DAY)>=16 && cal.get(Calendar.HOUR_OF_DAY)<19){
                dinner_Num++;
            }
            if(cal.get(Calendar.HOUR_OF_DAY)>=21 || cal.get(Calendar.HOUR_OF_DAY)<2){
                night_Num++;
            }
            else{
                normal_Num++;
            }
        }
    }
}
