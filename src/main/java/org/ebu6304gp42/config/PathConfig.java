package org.ebu6304gp42.config;

import java.io.File;


/**
 * Store the File Path Used by This Application
 */
public class PathConfig {
    public static String DISH_FILE = "Data"+ File.separator +"dish.json";
    public static String ACCOUNT_FILE = "Data"+ File.separator +"account.json";
    public static String ORDER_FILE = "Data"+ File.separator +"order.json";
    public static String PIC_DIR = "Pic" + File.separator;
    public static String EMAIL_DIR = "Email" + File.separator;
    public static String TICKET_DIR = "Ticket" + File.separator;
    public static String  DEFAULT_PIC = "/res/pic/default_pic.png";
    public static String prefix = "./Data/";

    /**
     * Check the status of the data path, it will create the folder if not exist..
     */
    public static void checkFolder(){
        checkFolder(prefix + File.separator + "Data");
        checkFolder(prefix + File.separator + "Pic");
        checkFolder(prefix + File.separator + "Email");
        checkFolder(prefix + File.separator + "Ticket");
    }

    /**
     * Get file path which store dish information.
     * @return Path of dish data file
     */
    public static String getDishFile() {
        return prefix + File.separator + DISH_FILE;
    }

    /**
     * Get file path which store account information.
     * @return Path of account data file
     */
    public static String getAccountFile() {
        return prefix + File.separator + ACCOUNT_FILE;
    }

    /**
     * Get file path which store order information.
     * @return Path of order data file
     */
    public static String getOrderFile() {
        return prefix + File.separator + ORDER_FILE;
    }

    /**
     * Get file path of default pic for no pic dish.
     * @return Path of default picture
     */
    public static String getDefaultPic() {
        return DEFAULT_PIC;
    }

    /**
     * Get folder path which store pictures.
     * @return Path of picture folder
     */
    public static String getPicDir(){return prefix + File.separator + PIC_DIR;}

    /**
     * Get folder path which store emails.
     * @return Path of email folder
     */
    public static String getEmailDir(){return prefix + File.separator + EMAIL_DIR;}

    /**
     * Get folder path which store ticket.
     * @return Path of ticket folder
     */
    public static String getTicketDir(){return prefix + File.separator + TICKET_DIR;}

    /**
     * Check if a folder is exist under the data path, and will create if not.
     * @param name folder name.
     */
    public static void checkFolder(String name){
        File folder = new File(name);
        if(!folder.exists()){
            folder.mkdirs();
        }else if(!folder.isDirectory()){
            System.out.println("Please delete The File " + name + ". It should be a directory!");
        }
    }
}
