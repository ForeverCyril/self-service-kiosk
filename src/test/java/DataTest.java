import org.ebu6304gp42.Config.PathConfig;
import org.ebu6304gp42.Data.*;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    public void testOrderBank(){
        PathConfig.ORDER_FILE = "Test/" + PathConfig.ORDER_FILE;
        clearFile(PathConfig.ORDER_FILE);

        OrderBank bank = new OrderBank();

        for(int i=0;i<5;i++){
            Order order = new Order();
            for(int j=0; j<=i;j+=2){
                OrderedDish dish = new OrderedDish();
                dish.setName(String.format("Dish %d-%d", i,j));
                dish.setPrice(1.89 * i);
                dish.setAmount(i % 3);
                dish.setNote("note from " + i + j);
                dish.addOption("Op" + i+j, "SEL"+j);
                order.addDish(dish);
                order.setTime(new Date());
            }
            order.setNote("Order"+i);
            bank.addOrder(order);
        }

        OrderBank bank_load = new OrderBank();
        bank_load.load();
        for(int i =0; i<5; i++){
            Order order = bank_load.getOrders().get(i);
            assertEquals(order.getDish().get(0).getName(),String.format("Dish %d-%d", i,0) );
            assertEquals(order.getDish().get(0).getPrice(), 1.89 * i, 0.001);
        }
    }

    @Test
    public void testAccount(){
        PathConfig.ACCOUNT_FILE = "Test/" + PathConfig.ACCOUNT_FILE;
        clearFile(PathConfig.ACCOUNT_FILE);

        AccountBank bank = new AccountBank();
        for(int i = 0; i <10; i++){
            bank.register("Test"+i, "Test"+i, "phone+"+i,"email"+i);
        }

        AccountBank bank_load = new AccountBank();
        var acc = bank_load.seek("00000001");
        assertEquals(acc.getFirst_name(), "Test0");
    }

    @Test
    public void testDish(){
        PathConfig.DISH_FILE = "Test/" + PathConfig.DISH_FILE;
        DishBank dishBank = new DishBank();

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
            dishBank.addDish(dish);
        }
        dishBank.save();

        DishBank bank = new DishBank();
        bank.load();
        for(int i=0;i<5;i++){
            Dish dish = bank.getDish().get(i);
            assertEquals(dish.getName(), "Dish"+i);
            assertEquals(dish.getPrice(), 0.99*(i+1), 0.001);
            assertEquals(dish.isAvailable(), i!=0);
        }
    }
}
