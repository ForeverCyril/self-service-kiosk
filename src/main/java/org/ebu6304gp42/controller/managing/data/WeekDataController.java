package org.ebu6304gp42.controller.managing.data;

import org.ebu6304gp42.data.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class WeekDataController {
    int weekData=0;
    int firstDay;
    int lastDay;

    Calendar startCal = Calendar.getInstance();
    Calendar endCal = Calendar.getInstance();

    //Count the dishes we sell in a week
    public HashMap<String,Integer> weekData(){
        Calendar  now= Calendar.getInstance();
        int startMonth= now.get(Calendar.MONTH);
        int endMonth = now.get(Calendar.MONTH);
        now.setTime(new Date());
        int weekDay = now.get(Calendar.DAY_OF_WEEK);
        switch (weekDay){
            case (1):
                firstDay = now.get(Calendar.DAY_OF_MONTH)-13;
                lastDay = now.get(Calendar.DAY_OF_MONTH)-7;
                break;
            case (2):
                firstDay = now.get(Calendar.DAY_OF_MONTH)-7;
                lastDay = now.get(Calendar.DAY_OF_MONTH)-1;
                break;
            case (3):
                firstDay = now.get(Calendar.DAY_OF_MONTH)-8;
                lastDay = now.get(Calendar.DAY_OF_MONTH)-2;
                break;
            case (4):
                firstDay = now.get(Calendar.DAY_OF_MONTH)-9;
                lastDay = now.get(Calendar.DAY_OF_MONTH)-3;
                break;
            case (5):
                firstDay = now.get(Calendar.DAY_OF_MONTH)-10;
                lastDay = now.get(Calendar.DAY_OF_MONTH)-4;
                break;
            case (6):
                firstDay = now.get(Calendar.DAY_OF_MONTH)-11;
                lastDay = now.get(Calendar.DAY_OF_MONTH)-5;
                break;
            case (7):
                firstDay = now.get(Calendar.DAY_OF_MONTH)-12;
                lastDay = now.get(Calendar.DAY_OF_MONTH)-6;
                break;
        }

        if(firstDay <=0){
            if (now.get(Calendar.MONTH)==0||now.get(Calendar.MONTH)==2||now.get(Calendar.MONTH)==4||now.get(Calendar.MONTH)==6||
                    now.get(Calendar.MONTH)==7||now.get(Calendar.MONTH)==9||now.get(Calendar.MONTH)==11){
                firstDay = firstDay + 31;
            }
            else if (now.get(Calendar.MONTH) == 1 || now.get(Calendar.MONTH) == 3 || now.get(Calendar.MONTH) == 5 ||now.get(Calendar.MONTH) == 8 ||
                    now.get(Calendar.MONTH) == 10) {
                firstDay = firstDay + 30;
            }
            else
                firstDay = firstDay + 28;
            startMonth = now.get(Calendar.MONTH);
        }

        if(lastDay <=0){
            if (now.get(Calendar.MONTH)==0||now.get(Calendar.MONTH)==2||now.get(Calendar.MONTH)==4||now.get(Calendar.MONTH)==6||
                    now.get(Calendar.MONTH)==7||now.get(Calendar.MONTH)==9||now.get(Calendar.MONTH)==11){
                lastDay = lastDay + 31;
            }
            else if (now.get(Calendar.MONTH) == 1 || now.get(Calendar.MONTH)== 3 || now.get(Calendar.MONTH)== 5 ||now.get(Calendar.MONTH)== 8 ||
                    now.get(Calendar.MONTH) == 10) {
                lastDay = lastDay + 30;
            }
            else
                lastDay = lastDay + 28;
            endMonth = now.get(Calendar.MONTH);
        }

        startCal.set(now.get(Calendar.YEAR),startMonth, firstDay);
        endCal.set(now.get(Calendar.YEAR),endMonth, lastDay);
        Date startDate = startCal.getTime();
        Date endDate = endCal.getTime();

        HashMap<String,Integer> weekDataMap = new HashMap<>();
        for(Dish dish : DishManager.getInstance().getDish()){
            weekDataMap.put(dish.getName(), 0);
        }
        for(Order orders: OrderManager.getInstance().getOrders()){
            for(OrderedDish orderedDish : orders.getDish()) {
                for(String key:weekDataMap.keySet()){
                    if(Objects.equals(orderedDish.getName(),key)){
                        if (orders.getTime().getTime() >= startDate.getTime() && orders.getTime().getTime() <= endDate.getTime()) {
                            weekDataMap.put(key,(weekDataMap.get(key) + 1));
                        }
                    }
                }
            }
        }
        return weekDataMap;
    }
}
