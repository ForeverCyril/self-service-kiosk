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
    private boolean modified = false;
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
                    var dish = data.get(index);
                    var res = (new EditDishDialog(dish)).showAndWait();
                    res.ifPresent(newDish->{
                        dish.copyFrom(newDish);
                        modified = true;
                    });
                    refresh();
                }
            });
            return row;
        });
    }

    public void save(){
        DishBank bank = new DishBank();
        data.forEach(bank::addDish);
        bank.save();
        modified = false;
    }

    public void addDish(Dish dish){
        if (dish==null)return;
        data.add(dish);
        refresh();
        modified = true;
    }


    private void addColumn(String name, String property){
        TableColumn<Dish, String> col = new TableColumn<>(name);
        col.setMinWidth(100);
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        this.getColumns().add(col);
    }

    public boolean isModified(){
        return modified;
    }
}


