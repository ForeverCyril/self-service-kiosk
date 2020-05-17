import javafx.application.Application;
import org.ebu6304gp42.config.PathConfig;
import org.ebu6304gp42.view.MainWindow;

public class AppLauncher {
    public static void main(String[] args) {
        PathConfig.checkFolder();
        Application.launch(MainWindow.class, args);
    }
}
