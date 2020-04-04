import javafx.application.Application;
import org.ebu6304gp42.Config.PathConfig;
import org.ebu6304gp42.Data.Dish;
import org.ebu6304gp42.Data.DishBank;
import org.ebu6304gp42.Data.DishOption;
import org.ebu6304gp42.Ui.Mainwindow;


import java.io.File;

/**
 * The Enter Point of This Application
 */
public class Main {
    public static void main(String[] args) {
        PathConfig.checkFolder();
        File file = new File(PathConfig.getDishFile());
        if(!file.exists())
            createDefaultDish();
        Application.launch(Mainwindow.class);
    }

    public static void createDefaultDish(){
        DishBank bank = new DishBank();
        Dish dish = new Dish();
        dish.setName("Ramen");
        dish.setPrice(9.99);
        dish.setPic("./media/ramen.jfif");
        DishOption soup = new DishOption("Soup");
        soup.addOption("Tonkotsu");
        soup.addOption("Shoyu");
        soup.addOption("Shio");
        dish.addOption(soup);

        DishOption noodles = new DishOption("Noodles");
        noodles.addOption("Soft");
        noodles.addOption("Medium");
        noodles.addOption("Firm");
        dish.addOption(noodles);

        DishOption onion = new DishOption("Spring Onion");
        onion.addOption("No please");
        onion.addOption("Just a little");
        onion.addOption("A lot!");
        dish.addOption(onion);

        dish.addOption(boolOption("Nori", 0));
        dish.addOption(boolOption("Chashu", 0));
        dish.addOption(boolOption("Boiled egg", 0));

        DishOption spic = new DishOption("Spiciness");
        for(int i=0;i<=5;i++){
            spic.addOption(String.format("%d",i));
        }
        dish.addOption(spic);

        dish.addOption(boolOption("Extra Nori", 1));
        dish.addOption(boolOption("Extra boiled egg", 1));
        dish.addOption(boolOption("Bamboo shoots", 1));
        dish.addOption(boolOption("Extra Chashu", 2));

        bank.addDish(dish);
        bank.save();
    }

    public static DishOption boolOption(String name, double price){
        DishOption option = new DishOption(name);
        option.addOption("Yes", price);
        option.addOption("No");
        return option;
    }
}

