import com.google.gson.Gson;
import javafx.application.Application;
import org.ebu6304gp42.Data.Order;
import org.ebu6304gp42.Data.OrderBank;
import org.ebu6304gp42.Data.OrderedDish;
import org.ebu6304gp42.Ui.Mainwindow;

public class Main {
    public static void main(String[] args) {
        /*Order order = new Order();
        for(int i=0; i<2; i++){
            OrderedDish orderedDish = new OrderedDish();
            orderedDish.setName("Test"+i);
            orderedDish.setPrice(8.99 + i);
            orderedDish.setAmount(i+1);
            orderedDish.addOption("OP"+i, "SEL"+i);
            orderedDish.setNote("note" + i);
            order.addDish(orderedDish);
        }

        OrderBank bank = new OrderBank();
        bank.addOrder(order);

        bank.load();

        System.out.println(bank.getOrders().get(0).getDish().get(1).getName());*/

        Application.launch(Mainwindow.class);
    }
}
