import javafx.application.Application;
import org.ebu6304gp42.config.PathConfig;
import org.ebu6304gp42.data.DishManager;
import org.ebu6304gp42.data.analysis.DataAnalyser;
import org.ebu6304gp42.data.analysis.RecommenderSystem;
import org.ebu6304gp42.view.MainWindow;

import java.util.Calendar;
import java.util.Objects;

public class AppLauncher {
    public static void main(String[] args) {
        PathConfig.checkFolder();
        beforeStart(args);
        Application.launch(MainWindow.class, args);
    }

    public static void beforeStart(String[] args){
        RecommenderSystem recommenderSystem = new RecommenderSystem();
        recommenderSystem.hotRecommend(); // set hot dish.
        recommenderSystem.sendRecommendEmail();
    }
}
