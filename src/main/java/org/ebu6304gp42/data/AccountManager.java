package org.ebu6304gp42.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ebu6304gp42.config.PathConfig;
import org.ebu6304gp42.exception.account.IllegalInputException;

import java.io.*;
import java.util.ArrayList;
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

    public Account register(String first_name,String last_name,String phone,String email, boolean rec) throws IllegalInputException {
        int id = list.size()+1;
        validate(first_name, last_name, phone, email);
        Account account = new Account(first_name, last_name, phone, email, id, rec);
        list.add(account);
        return account;
    }

    public void save() {
        File file = new File(PathConfig.getAccountFile());
        Gson gson = new Gson();
        StringBuilder data = new StringBuilder();
        for (var acc:list){
            data.append(gson.toJson(acc));
            data.append('\n');
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        try (FileWriter out = new FileWriter(file)) {
            out.write(data.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void updateInformation(int id, String first_name,String last_name,String phone,String email, boolean rec)
            throws IllegalInputException{
        var acc = seek(id);
        validate(first_name, last_name, phone, email);
        acc.setFirst_name(first_name);
        acc.setLast_name(last_name);
        acc.setPhone(phone);
        acc.setEmail(email);
        acc.setAccept_rec(rec);
    }


    public void load() {
        int num = 0;
        list.clear();

        File file = new File(PathConfig.getAccountFile());
        try {
            if(!file.exists()){
                System.out.print("Account File Not Exist. Try to Create");
                var res = file.createNewFile();
                if(res){
                    System.out.println("Create Account File Successfully.");
                } else {
                    System.out.println("Create Account File Failed!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(PathConfig.getAccountFile()));
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

    //根据ID返回对象
    public Account seek(int id) throws IllegalInputException {
        for (Account account : list) {
            if (account.getId() == id) {
                System.out.println(account);
                return account;
            }
        }
        throw new IllegalInputException(String.format("ID:%08d is not found", id));
    }

    public void validate(String first_name,String last_name,String phone,String email) throws IllegalInputException {
        if(!validateName(first_name)){
            throw new IllegalInputException("First Name Invalid");
        }
        if(!validateName(last_name)){
            throw new IllegalInputException("Last Name Invalid");
        }
        if((email==null||email.isBlank()) && (phone==null||phone.isBlank())){
            throw new IllegalInputException("Email and Phone Can Not Be empty Together");
        }

        if(email!= null && !validateEmail(email)){
            throw new IllegalInputException("Email Invalid");
        }
        if(phone!=null && !validateMobilePhone(phone)){
            throw new IllegalInputException("Phone Invalid");
        }
    }

    public static boolean validateMobilePhone(String phone) {
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        return pattern.matcher(phone).matches();
    }

    public static boolean validateEmail(String email) {
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

    public static Boolean validateName(String name) {
        //also include chinese character
        return name.matches("^([\\\\u4e00-\\\\u9fa5]{1,20}|[a-zA-Z\\\\.\\\\s]{1,20})$");
    }
}