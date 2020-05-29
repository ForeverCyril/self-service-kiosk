import org.ebu6304gp42.config.PathConfig;
import org.ebu6304gp42.data.*;

import org.ebu6304gp42.exception.AccountException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

public class DataTest {
    private void clearFile(String fileName){

        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testOrderManager(){
        PathConfig.prefix="./Test";
        PathConfig.checkFolder();
        clearFile(PathConfig.getOrderFile());

        OrderManager bank = OrderManager.getInstance();

        for(int i=0;i<5;i++){
            Order order = new Order();
            for(int j=0; j<=i;j+=2){
                OrderedDish dish = new OrderedDish();
                dish.setName(String.format("Dish %d-%d", i,j));
                dish.setPrice(1.89 * i);
                dish.setAmount(i % 3);
                dish.setNote("note from " + i + j);
                dish.addOption("Op" + i+j, "SEL"+j, j*0.6);
                order.addDish(dish);
                order.setTime(new Date());
            }
            order.setNote("Order"+i);
            bank.addOrder(order);
        }

        OrderManager bank_load = OrderManager.getInstance();
        bank_load.load();

        for(int i =0; i<5; i++){
            Order order = bank_load.getOrders().get(i);
            assertEquals(order.getDish().get(0).getName(),String.format("Dish %d-%d", i,0) );
            assertEquals(order.getDish().get(0).getPrice(), 1.89 * i, 0.001);
        }
    }

    @Test
    public void testAccount(){

        PathConfig.prefix="./Test";
        PathConfig.checkFolder();
        clearFile(PathConfig.getAccountFile());

        AccountManager bank = AccountManager.getInstance();
        for(int i = 0; i <10; i++){
            try {
                bank.register("Test"+(char)((int)'a' + i), "Test"+(char)((int)'a' + i),
                        "1231234123"+i%10,
                        "",
                        false);
            } catch (AccountException e) {
                e.printStackTrace();
            }
        }
        bank.save();
        AccountManager bank_load = AccountManager.getInstance();
        Account acc = null;
        try {
            acc = bank_load.seek(1);
        } catch (AccountException e) {
            e.printStackTrace();
        }
        assert acc != null;
        assertEquals(acc.getFirst_name(), "Testa");
    }

    @Test
    public void testDish(){

        PathConfig.prefix="./Test";
        PathConfig.checkFolder();
        DishManager dishManager = DishManager.getInstance();

        for(int i=0;i<5;i++){
            Dish dish = new Dish();
            dish.setName("Dish"+i);
            dish.setPrice(0.99*(i+1));
            dish.setRemain(i);
            dish.setStatus(true);
            for(int j=0; j<i; j+=2){
                DishOption option = new DishOption();
                option.setName("Op"+j);
                for(int k=0;k<i%3;k++){
                    option.addOption("sel"+k);
                }
                dish.addOption(option);
            }
            dishManager.addDish(dish);
        }
        dishManager.save();

        DishManager bank = DishManager.getInstance();
        bank.load();
        for(int i=0;i<5;i++){
            Dish dish = bank.getDish().get(i);
            assertEquals(dish.getName(), "Dish"+i);
            assertEquals(dish.getPrice(), 0.99*(i+1), 0.001);
            assertEquals(dish.isAvailable(), i!=0);
        }
    }
}
