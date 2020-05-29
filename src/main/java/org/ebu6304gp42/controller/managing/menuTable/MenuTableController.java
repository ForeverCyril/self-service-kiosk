package org.ebu6304gp42.controller.managing.menuTable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.ebu6304gp42.data.Dish;
import org.ebu6304gp42.data.DishManager;
import org.ebu6304gp42.view.DishStaticDialog;
import org.ebu6304gp42.view.EditDishDialog;

import java.net.URL;
import java.util.ResourceBundle;

import static org.ebu6304gp42.view.DishStaticDialog.EditButtonType;

public class MenuTableController implements Initializable {
    private ObservableList<Dish> data;
    private boolean modified = false;

    @FXML
    private TableView<Dish> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setRowFactory(menuRowFactory);
        DishManager dishManager = DishManager.getInstance();
        data = FXCollections.observableArrayList(dishManager.getDish());
        table.setItems(data);
    }

    private final Callback<TableView<Dish>, TableRow<Dish>> menuRowFactory = tv->{
        TableRow<Dish> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && !row.isEmpty()){
                TableRow cur_row = (TableRow) event.getSource();
                int index = cur_row.getIndex();
                var dish = data.get(index);
                var res = (new DishStaticDialog(dish)).showAndWait();
                res.ifPresent(buttonType -> {
                    if(buttonType == EditButtonType){
                        var res_edit = (new EditDishDialog(dish)).showAndWait();
                        res_edit.ifPresent(newDish->{
                            dish.copyFrom(newDish);
                            modified = true;
                        });
                        tv.refresh();
                    }
                });
            }
        });
        return row;
    };

    @FXML
    private void onAddBtnClick(MouseEvent event){
        var dish = (new EditDishDialog(null)).showAndWait();
        dish.ifPresent(this::addDish);
    }
    @FXML
    private void onSaveBtnClick(MouseEvent event){
        save();
    }

    /**
     * Save Dish to dish manager
     */
    public void save(){
        DishManager dishManager = DishManager.getInstance();
        dishManager.clear();
        data.forEach(dishManager::addDish);
        dishManager.save();
        modified = false;
    }

    /**
     * Add dish to the table
     * @param dish dish
     */
    public void addDish(Dish dish){
        if (dish==null)return;
        data.add(dish);
        table.refresh();
        modified = true;
    }

    /**
     * @return whether menu changed.
     */
    public boolean changed(){
        return modified;
    }
}
