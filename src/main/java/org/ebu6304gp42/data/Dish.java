package org.ebu6304gp42.data;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Store Dish data
 */
public class Dish {
    private String name;
    private double price;
    private String pic;
    private String description;
    private int remain;
    private boolean status;
    private String recommend;

    ArrayList<DishOption> options = new ArrayList<DishOption>();

    public Dish(){}

    public Dish(String name) {
        this.name = name;
    }

    public Dish(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public void copyFrom(Dish dish){
        name = dish.name;
        price = dish.price;
        pic = dish.pic;
        description = dish.description;
        remain = dish.remain;
        status = dish.status;
        options = dish.options;
        recommend = dish.recommend;
    }

    public boolean isAvailable(){
        return status && (remain > 0);
    }

    public void addOption(DishOption dishOption){
        options.add(dishOption);
    }
    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return this.description;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }

    public void setRemain(int remain){
        this.remain=remain;
    }
    public int getRemain(){
        return this.remain;
    }

    public void setStatus(boolean status){
        this.status = status;
    }
    public boolean getStatus(){
        return this.status;
    }

    public void setPrice(double price){
        this.price=price;
    }
    public double getPrice(){
        return this.price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String[] getRecommend() {
        if (recommend == null){
            return null;
        }
        return recommend.split(";");
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public ArrayList<DishOption> getOptions() {
        return options;
    }
    public void resetOptions(ArrayList<DishOption> list){
        options = list;
    }
    public String toString(){
        return "Name"+name+"Description"+description;
    }

    /**
     * Set Image to a Image View
     * @param container image container
     * @param checkStatus when it's true, it will set sold out picture when the dish is available.
     */
    public void setImageTo(ImageView container, boolean checkStatus){
        if(checkStatus && !isAvailable()){
            setSoldOutImage(container);
            return;
        }
        if(pic!=null && !pic.isBlank()){
            File picFile = new File(pic);
            if (!picFile.exists()){
                pic = null;
            }
        }
        setImageTo(container, pic);
    }

    /**
     * Set image to container
     * @param container image container
     * @param pic filename
     */
    static public void setImageTo(ImageView container, String pic){
        if(pic!=null && !pic.isBlank()){
            File picFile = new File(pic);
            if(picFile.exists()){
                try(FileInputStream picStream = new FileInputStream(picFile)) {
                    Image picImg = new Image(picStream, container.getFitWidth(), container.getFitHeight(), true, true);
                    container.setImage(picImg);
                } catch (IOException e){
                    System.out.println(String.format("Error When Deal with Dish Pic:%s", e.getLocalizedMessage()));
                }
            }
        }
    }

    /**
     * Set sold out picture in imageview
     * @param container imageview that contain the image.
     */
    static public void setSoldOutImage(ImageView container){
        Image image = new Image(Dish.class.getResourceAsStream("/res/pic/soldout.png"),
                container.getFitWidth(), container.getFitHeight(), true, true);
        container.setImage(image);
    }
}
