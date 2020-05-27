package org.ebu6304gp42.component.output;

import org.ebu6304gp42.config.PathConfig;
import org.ebu6304gp42.data.Account;
import org.ebu6304gp42.data.Order;
import org.ebu6304gp42.view.ShowAlert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintInfo {
    private static void printToFile(File file, String data){
        if (!file.exists()) {
            try {
                var res = file.createNewFile();
                if(!res){
                    ShowAlert.error("File Create Error", "Fail to create file " + file.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter out = new FileWriter(file)) {
            out.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //消费后发送邮件
    public static void printEmail(Account account){
        String firstName=account.getFirst_name();
        String lastName=account.getLast_name();
        int stamp=account.getCount();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        File file = new File(PathConfig.getEmailDir()+ String.format("%08d(%s).txt", account.getId(), dateFormat.format(new Date())));

        String data = "Dear " + firstName + " "+ lastName + "\n" +
                      "Your ID is: " + String.format("%08d", account.getId()) + "\n" +
                      "   Thanks for coming here to enjoy the food " + "\n" +
                      "   The number of your stamps is:" + stamp + "\n" +
                      "   Welcome back again!" + "\n";
        printToFile(file, data);
    }


    //打印小票
    public static void printTicket(Order order) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        File file = new File(PathConfig.getTicketDir()+dateFormat.format(new Date())+".txt");
        //File file = new File(PathConfig.getOrderFile());
        StringBuilder data = new StringBuilder();
        String a = "-------------------------------------------\n";
        String b = "Data name-----------Dish number-------Price\n";

        data.append(order.getTime());
        data.append("\n");
        data.append(b);
        data.append(a);
        for (var dish : order.getDish()) {
            data.append(dish.getName());
            data.append("             ");
            data.append(dish.getAmount());
            data.append("                 ");
            data.append(dish.getPrice());
            data.append("\n");
            data.append(dish.getOptions()).append("\n");
            data.append(a);
        }
        data.append("                    Total              ").append(order.getPrice()).append("\n");
        data.append(a);
        data.append("                    ").append(order.getType());

        printToFile(file, data.toString());
    }
}

