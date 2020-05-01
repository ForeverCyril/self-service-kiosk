import javafx.application.Application;
import org.ebu6304gp42.Config.PathConfig;
import org.ebu6304gp42.View.MainWindow;

public class AppLauncher {
    public static void main(String[] args) {
        PathConfig.checkFolder();
        Application.launch(MainWindow.class, args);
    }
}
