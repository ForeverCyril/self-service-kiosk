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

public class AccountManager {
    static AccountManager instance;

    ArrayList<Account> list = new ArrayList<>();

    public static AccountManager getInstance(){
        if(instance == null){
            instance = new AccountManager();
        }
        return instance;
    }

    private AccountManager(){load();}

    public Account register(String first_name,String last_name,String phone,String email) {
        int id = list.size()+1;

        if(validateName(first_name) && validateName(last_name) && validateName(email) && validateMobilePhone(phone)) {
            Account account = new Account(first_name, last_name, phone, email, id);
            list.add(account);
            return account;
        }
        return null;
    }

    public void save() {
        File file = new File(PathConfig.getAccountFile());
        Gson gson = new Gson();
        StringBuilder data = new StringBuilder();
        for (var acc:list){
            data.append(gson.toJson(acc));
            data.append('\n');
        }

        try (FileWriter out = new FileWriter(file)) {
            out.write(data.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void changeInformation(Account account){
        Account seekAccount = seek(account.getId());
        if(seekAccount != null){
            seekAccount.copyFrom(account);
        }
    }


    public void load() {
        int num = 0;
        list.clear();

        File file = new File(PathConfig.getAccountFile());
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
    public Account seek(int id){
        for (Account account : list) {
            if (account.getId() == id) {
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

    public static Boolean validateName(String name) {
        //also include chinese character
        return name.matches("^([\\\\u4e00-\\\\u9fa5]{1,20}|[a-zA-Z\\\\.\\\\s]{1,20})$");
    }
}


