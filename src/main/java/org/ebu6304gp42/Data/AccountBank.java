package org.ebu6304gp42.Data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ebu6304gp42.Config.PathConfig;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Dong Bo
 * @author Liu Yingying
 */

public class AccountBank {

    ArrayList<Account> list = new ArrayList<>();

    public Account register(String first_name,String last_name,String phone,String email,boolean receive) {
        PathConfig.ACCOUNT_FILE = "Test/" + PathConfig.ACCOUNT_FILE;
        load();
        int currentID = list.size()+1;
        DecimalFormat nf = new DecimalFormat("00000000");
        String ID = nf.format(currentID);

        Account account = new Account(first_name,last_name,phone,email,ID,receive);

        if(!validateName(first_name)&&!validateName(last_name)) {
            System.out.println("Your name is wrong");
        }
        else{
            if(phone.equals("")&&email.equals(""))
                System.out.println("You need to complete the information");
            else{
                if(!validateMobilePhone(phone) || !validateEmail(email))
                    System.out.println("You input the wrong information");
                else{
                    save(account);
                }
            }
        }
        return null;
    }



    private void save(Account saveAccount) {
        File file = new File(PathConfig.ACCOUNT_FILE);

        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;

        //detect whether file exists
        try {
            if (!file.exists()) {
                boolean hasFile = file.createNewFile();
                if(hasFile){
                    System.out.println("savefile not exists, create new file");
                }
                fileOutputStream = new FileOutputStream(file);
            } else {
                System.out.println("savefile exists");
                fileOutputStream = new FileOutputStream(file, true);
            }

            //write content into file
            Gson gson=new Gson();
            String accountObject=gson.toJson(saveAccount);
            String s=accountObject.toString();
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
            outputStreamWriter.write(s); //写入内容
            outputStreamWriter.write("\r\n");  //换行

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void changeInformation(Account saveAccount){
        load();
        File file = new File(PathConfig.ACCOUNT_FILE);
        file.delete();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            Account account = (Account)iterator.next();
            if (account.getID().equals(saveAccount.getID())){
                account = saveAccount;
            }
            save(account);
        }
    }


    public void load() {
        int num = 0;
        list.clear();

        File file = new File(PathConfig.ACCOUNT_FILE);
        try {
            if (!file.exists()) {
                boolean hasFile = file.createNewFile();
                if (hasFile) {
                    System.out.println("file not exists, create new file");
                }
            } else {
                System.out.println("file exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(PathConfig.ACCOUNT_FILE));
            String line = bufferedReader.readLine();
            while (line != null) {
                Account account = gson.fromJson(line, Account.class);
                list.add(num, account);
                line = bufferedReader.readLine();
                num++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Account> getAccount(){
        return list;
    }


    public void printInformation() {
        load();
        Iterator it1 = list.iterator();
        while (it1.hasNext()) {
            System.out.println(it1.next());
        }
    }


    //根据ID返回对象
    public Account seek(String ID){
        load();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            Account account = (Account)iterator.next();
            if (account.getID().equals(ID)){
                System.out.println(account);
                return account;
            }
        }
        System.out.println("Do not exist this id");
        return null;
    }

    public static boolean validateMobilePhone(String phone) {
        if(phone.equals(""))
            return true;
        else {
            Pattern pattern = Pattern.compile("^[1]\\d{10}$");
            return pattern.matcher(phone).matches();
        }
    }

    public static boolean validateEmail(String email) {
        if(email.equals(""))
            return true;
        else {
            boolean flag = false;
            try {
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(email);
                flag = matcher.matches();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        }
    }

    /**
     * 1.可以是中文
     2.可以是英文，允许输入点（英文名字中的那种点）， 允许输入空格
     3.中文和英文不能同时出现
     4.长度在20个字符以内
     */
    public static Boolean validateName(String name) {
        //return name.matches("^([\\u4e00-\\u9fa5]{1,20}|[a-zA-Z\\.\\s]{1,20})$");

        return name.matches("^([\\\\u4e00-\\\\u9fa5]{1,20}|[a-zA-Z\\\\.\\\\s]{1,20})$");
    }
}


