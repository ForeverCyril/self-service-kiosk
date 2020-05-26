package org.ebu6304gp42.data;

import org.ebu6304gp42.exception.AccountException;

/**
 * Class that represent an account.
 * You should create Account by Account Manager due to there is no data valid inside this class.
 */
public class Account {
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private int count = 0;
    private int id;
    private boolean accept_rec;
    public final static int
            FREE_NUM = 10;//可以免费的邮票数

    public Account(String first_name, String last_name, String phone, String email, int id, boolean accept_rec) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.id = id;
        this.accept_rec = accept_rec;
    }

    public Account(String first_name, String last_name, String phone, String email, int id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.id = id;
        accept_rec = false;
    }

    /**
     *Deep copy from another Account
     * @param account resource
     */
    public void copyFrom(Account account){
        first_name = account.first_name;
        last_name = account.last_name;
        phone = account.phone;
        email = account.email;
        count = account.count;
        id = account.id;
    }

    public String getName(){
        return first_name + " " + last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Make Account stamp count increase one.
     */
    public void addCount(){
        count++;
    }

    /**
     * Use Stamp Count, count will decrease FREE_NUM
     * @throws AccountException throw when count no enough.
     */
    public void useCount() throws AccountException{
        if(count < FREE_NUM){
            throw new AccountException("Count Not Enough.");
        }
        count -= FREE_NUM;
    }

    public boolean isAccept_rec() {
        return accept_rec;
    }

    public void setAccept_rec(boolean accept_rec) {
        this.accept_rec = accept_rec;
    }

    public void setCount(int count){
        this.count = count;
    }

    @Override
    public String toString() {
        return "Account{" +
               "first_name='" + first_name + '\'' +
               ", last_name='" + last_name + '\'' +
               ", phone='" + phone + '\'' +
               ", email='" + email + '\'' +
               ", count=" + count +
               ", ID='" + id + '\'' +
               '}';
    }
}
