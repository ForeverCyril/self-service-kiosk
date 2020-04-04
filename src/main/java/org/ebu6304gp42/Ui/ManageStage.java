package org.ebu6304gp42.Ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManageStage extends Stage {
    private TabPane tabPane = new TabPane();
    private MenuTable meanuTable = new MenuTable();
    public ManageStage(){
        super();
        Scene scene = new Scene(tabPane, 900, 600);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setTabDragPolicy(TabPane.TabDragPolicy.FIXED);

        initMenuManage();

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

        tabPane.getTabs().add(tab);
    }
}
