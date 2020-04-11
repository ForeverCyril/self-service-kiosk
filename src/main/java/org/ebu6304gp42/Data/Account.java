package org.ebu6304gp42.Data;

/**
 * @author Dong Bo
 * @author Liu Yingying
 */
public class Account {
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private int count = 0;
    private String ID;
    private boolean receive;
    public final static int freenum = 10;//可以免费的邮票数

    public Account(String first_name, String last_name, String phone, String email, String ID,boolean receive) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.ID = ID;
        this.receive=receive;
    }

    public Account() {

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

    public String getID() {
        return ID;
    }

    public boolean getReceive() {
        return receive;
    }

    public void setReceive(boolean first_name) {
        this.receive = receive;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    void addCount(){
        count ++;
    }

    public boolean useCount(){
        if (count >= freenum){
            count -= freenum;
            return true;
        }
        else
            System.out.println("You do not have enough stamps");
        return false;
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
               ", ID='" + ID + '\'' +
               '}';
    }
}
