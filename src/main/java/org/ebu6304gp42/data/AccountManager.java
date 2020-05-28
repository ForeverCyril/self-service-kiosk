package org.ebu6304gp42.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ebu6304gp42.component.output.PrintInfo;
import org.ebu6304gp42.config.PathConfig;
import org.ebu6304gp42.exception.AccountException;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Used to manager account data. Please use getInstance instead of manually new one.
 * You should only create account by this class for the {@link Account} do not have data validator.
 */

public class AccountManager {
    static AccountManager instance;

    ArrayList<Account> list = new ArrayList<>();

    /**
     * Get the instance of the class. There is only one instance during running.
     * @return Class instance.
     */
    public static AccountManager getInstance(){
        if(instance == null){
            instance = new AccountManager();
        }
        return instance;
    }

    private AccountManager(){load();}

    /**
     * Register an account, phone and email can not be both empty.
     * @param first_name Account first name
     * @param last_name Account last name
     * @param phone Account phone
     * @param email Account email
     * @param rec Want to receive news
     * @return Registered Account
     * @throws AccountException throw when given param is invalid.
     */
    public Account register(String first_name,String last_name,String phone,String email, boolean rec) throws AccountException {
        int id = list.size()+1;
        validate(first_name, last_name, phone, email);
        Account account = new Account(first_name, last_name, phone, email, id, rec);
        list.add(account);
        String content = "Account Created!"+
                         "\nID: " + String.format("%08d",id) +
                         "\nName: " + first_name + " " +last_name +
                         "\nPhone: " + phone +
                         "\nEmail: " + email;
        PrintInfo.sendEmailSMS(account, content);
        PrintInfo.printTicket("Register", content);
        return account;
    }

    /**
     * Save data into file, which is config in {@link PathConfig}
     */
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

    /**
     * Update information by account id.
     * @param id Id of account which need update
     * @param first_name New first name
     * @param last_name New Last name
     * @param phone New phone
     * @param email New email
     * @param rec New receive status
     * @throws AccountException throw when param is invalid.
     */
    public void updateInformation(int id, String first_name,String last_name,String phone,String email, boolean rec)
            throws AccountException {
        var acc = seek(id);
        validate(first_name, last_name, phone, email);
        acc.setFirst_name(first_name);
        acc.setLast_name(last_name);
        acc.setPhone(phone);
        acc.setEmail(email);
        acc.setAccept_rec(rec);
    }

    /**
     * Load data from file. It will automatic load when the class is creating.
     */
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

    /**
     * Get Account by id
     * @param id Account id
     * @return Account
     * @throws AccountException throw when Account not found.
     */
    public Account seek(int id) throws AccountException {
        for (Account account : list) {
            if (account.getId() == id) {
                System.out.println(account);
                return account;
            }
        }
        throw new AccountException(String.format("ID:%08d is not found", id));
    }

    /**
     * Validate whether data is meet the requirement.
     * @param first_name first name
     * @param last_name last name
     * @param phone phone
     * @param email email
     * @throws AccountException throw when param is invalid, the reason is stored in exception message.
     */
    public void validate(String first_name,String last_name,String phone,String email) throws AccountException {
        if(!validateName(first_name)){
            throw new AccountException("First Name Invalid");
        }
        if(!validateName(last_name)){
            throw new AccountException("Last Name Invalid");
        }
        if((email==null||email.isBlank()) && (phone==null||phone.isBlank())){
            throw new AccountException("Email and Phone Can Not Be empty Together");
        }

        if(email!= null && !email.isBlank() && !validateEmail(email)){
            throw new AccountException("Email Invalid");
        }
        if(phone!=null && !phone.isBlank() && !validateMobilePhone(phone)){
            throw new AccountException("Phone Invalid");
        }
    }

    /**
     * Validate phone format
     * @param phone phone number
     * @return Whether it's valid
     */
    public static boolean validateMobilePhone(String phone) {
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        return pattern.matcher(phone).matches();
    }

    /**
     * Valid email format
     * @param email email
     * @return Whether it's valid.
     */
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

    /**
     * Validate name
     * @param name name
     * @return Whether it's valid
     */
    public static Boolean validateName(String name) {
        //also include chinese character
        return name.matches("^([\\\\u4e00-\\\\u9fa5]{1,20}|[a-zA-Z\\\\.\\\\s]{1,20})$");
    }
}