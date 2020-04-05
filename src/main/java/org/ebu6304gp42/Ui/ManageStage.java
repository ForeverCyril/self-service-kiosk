package org.ebu6304gp42.Ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ManageStage extends Stage {
    private TabPane tabPane = new TabPane();
    private Tab home = new Tab("Home");
    private MenuTable meanuTable = new MenuTable();
    private HBox root = new HBox();
    private VBox btnArea = new VBox();
    public ManageStage(){
        super();
        setTitle("Self Service Kiosk");
        getIcons().add(new Image(getClass().getResourceAsStream("/res/pic/app.png")));
        Scene scene = new Scene(root, 900, 600);

        root.getChildren().addAll(btnArea, tabPane);


        HBox.setHgrow(tabPane, Priority.ALWAYS);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        btnArea.setPadding(new Insets(16));
        btnArea.setSpacing(18);

        initMenuManage();
        initDataManage();
        initHomePage();

        setScene(scene);
    }

    void initMenuManage(){
        Tab tab = new Tab("Menu");
        VBox vBox = new VBox();
        VBox.setVgrow(meanuTable, Priority.ALWAYS);
        vBox.getChildren().add(meanuTable);

        Button addBtn = new Button("Add");
        addBtn.setOnMouseClicked(event -> {
            System.out.println("Add");
        });

        Button saveBtn = new Button("Save");
        saveBtn.setOnMouseClicked(event -> {
            meanuTable.save();
        });
        HBox btns = new HBox();
        btns.setSpacing(6);
        btns.getChildren().addAll(Spacer.HSpacer(), addBtn, saveBtn);
        vBox.getChildren().add(btns);
        vBox.setPadding(new Insets(6));
        vBox.setSpacing(6);
        tab.setContent(vBox);

        addTabCommon("Menu", tab);
    }
    void initDataManage(){
        Tab tab = new Tab("Data");
        addTabCommon("Data", tab);
    }
    void initHomePage(){
        Label test = new Label("Home");
        test.setAlignment(Pos.CENTER);
        test.setFont(Font.font(null, FontWeight.BOLD, 24));
        home.setContent(test);

        tabPane.getTabs().add(home);
        tabPane.getSelectionModel().select(home);
    }

    void addButtonForTab(String name, Tab tab){
        Button btn = new Button(name);
        btn.setFont(Font.font(null, FontWeight.BOLD, 16));
        btn.setPrefSize(150, 40);
        btn.setOnMouseClicked(event -> {
            if(!tabPane.getTabs().contains(tab)) {
                tabPane.getTabs().add(tab);
            }
            tabPane.getSelectionModel().select(tab);
        });
        btnArea.getChildren().add(btn);
    }

    void addTabCommon(String name, Tab tab){
        tab.setOnClosed(event -> {
            if(tabPane.getTabs().isEmpty()){
                tabPane.getTabs().add(home);
                tabPane.getSelectionModel().select(home);
            }
        });
        addButtonForTab(name, tab);
    }
}
