package org.ebu6304gp42.Config;

import java.io.File;

public class PathConfig {
    public static String DISH_FILE = "Data"+ File.separator +"dish.json";
    public static String ACCOUNT_FILE = "Data"+ File.separator +"account.json";
    public static String ORDER_FILE = "Data"+ File.separator +"order.json";
    public static String  DEFAULT_PIC = "/res/pic/default_pic.png";
    public static String prefix = System.getProperties().getProperty("user.home") + File.separator +".config/kiosk";

    public static void checkFolder(){
        checkFolder(prefix + "/Data");
    }


    public static String getDishFile() {
        return prefix + File.separator + DISH_FILE;
    }

    public static String getAccountFile() {
        return prefix + File.separator + ACCOUNT_FILE;
    }

    public static String getOrderFile() {
        return prefix + File.separator + ORDER_FILE;
    }

    public static String getDefaultPic() {
        return DEFAULT_PIC;
    }


    public static void checkFolder(String name){
        File folder = new File(name);
        if(!folder.exists()){
            folder.mkdirs();
        }else if(!folder.isDirectory()){
            System.out.println("Please delete The File " + name + ". It should be a directory!");
        }
    }
}
