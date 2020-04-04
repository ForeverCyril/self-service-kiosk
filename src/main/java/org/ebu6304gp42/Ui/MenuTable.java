package org.ebu6304gp42.Ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.Data.Dish;
import org.ebu6304gp42.Data.DishBank;


public class MenuTable extends TableView<Dish> {
    private ObservableList<Dish> data;

    public MenuTable(){
        DishBank bank = new DishBank();
        bank.load();
        data = FXCollections.observableArrayList(bank.getDish());
        addColumn("Dish Name", "name");
        addColumn("Price", "price");
        addColumn("Description", "description");
        addColumn("Reamin", "remain");
        addColumn("Status", "status");

        setItems(data);
        setEditable(false);
        setRowFactory(tv->{
            TableRow<Dish> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    TableRow cur_row = (TableRow) event.getSource();
                    int index = cur_row.getIndex();
                    System.out.println(index);
                    System.out.println(data.get(index).getName());
                }
            });
            return row ;
        });
    }

    public void save(){
        DishBank bank = new DishBank();
        data.forEach(bank::addDish);
        bank.save();
    }


    private void addColumn(String name, String property){
        TableColumn<Dish, String> col = new TableColumn<>(name);
        col.setMinWidth(100);
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        this.getColumns().add(col);
    }
}

class DishDialog extends Dialog<Dish>{
    TextField name = new TextField();
    TextField price = new TextField();
    public DishDialog() {
        setHeaderText(null);
        setGraphic(null);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(name,price);
        
    }

}
