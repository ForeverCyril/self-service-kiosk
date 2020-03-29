package org.ebu6304gp42.Data;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ebu6304gp42.Config.PathConfig;

/**
 * @author Dong Bo
 * @author Liu Yingying
 */

public class AccountBank {

    ArrayList<Account> list = new ArrayList<>();

    public void register(String first_name,String last_name,String phone,String email) {

        load();

        int currentID = list.size()+1;
        DecimalFormat nf = new DecimalFormat("00000000");
        String ID = nf.format(currentID);

        Account account = new Account(first_name,last_name,phone,email,ID);

        //验证
        //if(true)
        System.out.println("successful");
        save(account);

        //if(false)
        //System.out.println("fail");
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
                    System.out.println("file not exists, create new file");
                }
                fileOutputStream = new FileOutputStream(file);
            } else {
                System.out.println("file exists");
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


    private void load(){

        int num = 0;
        list.clear();
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(PathConfig.ACCOUNT_FILE));
            String line = bufferedReader.readLine();
            while (line != null){

                Account account = gson.fromJson(line, Account.class);
                list.add(num,account);
                line = bufferedReader.readLine();
                num ++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void printInformation(){
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


}


