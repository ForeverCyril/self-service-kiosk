package org.ebu6304gp42.Ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.ebu6304gp42.Config.GeneraConfig;
import org.ebu6304gp42.Data.DishBank;
import org.ebu6304gp42.Event.MenuClickedEvent;

/**
 * Main window of this Application
 */
public class Mainwindow extends Application {
    private ManageStage manageStage;
    private Stage shopStage;
    MenuWidget menu = new MenuWidget();
    private CartWidget cart = new CartWidget();
    BorderPane root = new BorderPane();
    HBox statusBar = new HBox();

    @Override
    public void start(Stage primaryStage) throws Exception {
        shopStage = primaryStage;

        shopStage.setTitle("Self Service Kiosk");
        shopStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/pic/app.png")));
        root.setPadding(new Insets(5));
        root.setTop(initTitle());
        root.setCenter(initBuyArea());
        root.setBottom(initStatusBar());

        Scene scene = new Scene(root,900, 600);
        shopStage.setScene(scene);
        shopStage.show();
    }

    private Node initTitle(){// Top Area
        BorderPane topArea = new BorderPane();
        Label title = new Label(GeneraConfig.STORE_NAME);
        title.setFont(Font.font(null, FontWeight.BOLD, 36));
        topArea.setCenter(title);

        Label mangerEnter = new Label("Manage");
        mangerEnter.setFont(Font.font(14));
        mangerEnter.setUnderline(true);
        mangerEnter.setCursor(Cursor.HAND);
        mangerEnter.setAlignment(Pos.BOTTOM_RIGHT);
        mangerEnter.setTextFill(Color.GRAY);
        BorderPane.setAlignment(mangerEnter, Pos.BOTTOM_CENTER);
        mangerEnter.setOnMouseClicked(event -> {
            manageStage = new ManageStage();
            shopStage.hide();
            manageStage.showAndWait();
            shopStage.show();
            DishBank bank = new DishBank();
            bank.load();
            menu.load(bank.getDish());
        });
        topArea.setRight(mangerEnter);
        return topArea;
    }
    private Node initBuyArea(){
        HBox buyArea = new HBox();
        buyArea.setSpacing(6);
        buyArea.prefWidthProperty().bind(root.widthProperty());
        DishBank bank = new DishBank();
        bank.load();
        var dishes = bank.getDish();
        menu.load(dishes);
        menu.addEventHandler(MenuClickedEvent.MENU_CLICKED_EVENT, event -> {
            OptionDialog dialog = new OptionDialog(event.getDish());
            var res = dialog.showAndWait();
            res.ifPresent(orderedDish -> cart.addDish(orderedDish));
        });
        HBox.setHgrow(menu, Priority.ALWAYS);
        buyArea.getChildren().add(menu);

        cart.prefHeightProperty().bind(buyArea.heightProperty());
        cart.setPrefWidth(250);
        buyArea.getChildren().add(cart);
        return buyArea;
    }
    private Node initStatusBar(){
        statusBar.getChildren().add(new Label("Test Status Bar (Current Unavailable)"));
        return statusBar;
    }
}
