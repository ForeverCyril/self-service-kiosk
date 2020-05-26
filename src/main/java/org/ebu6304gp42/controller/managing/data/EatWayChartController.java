package org.ebu6304gp42.controller.managing.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import org.ebu6304gp42.data.Order;
import org.ebu6304gp42.data.OrderManager;

import java.net.URL;
import java.util.ResourceBundle;

import static org.ebu6304gp42.data.Order.TYPE.EAT_IN;
import static org.ebu6304gp42.data.Order.TYPE.TAKE_AWAY;

public class EatWayChartController implements Initializable {
    @FXML
    private PieChart EatWayChart;

    private int eatIn_Num = 0;
    private int takeAway_Num = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statistics();
        ObservableList<PieChart.Data> eatWayData = FXCollections.observableArrayList();
        eatWayData.addAll(new PieChart.Data("EAT_IN",eatIn_Num),new PieChart.Data("TAKE_AWAY",takeAway_Num));
        EatWayChart.setData(eatWayData);
        EatWayChart.setClockwise(true);
        EatWayChart.setLabelLineLength(30);
    }

    /**
     * Static information of orders
     */
    public void statistics(){
        for(Order order:OrderManager.getInstance().getOrders()){
            if(order.getType()== EAT_IN){
                eatIn_Num++;
            }
            if(order.getType()==TAKE_AWAY){
                takeAway_Num++;
            }
        }
    }
}
