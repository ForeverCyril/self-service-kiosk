package org.ebu6304gp42.component.optioneditor;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.ebu6304gp42.component.inputField.CurrencyField;
import org.ebu6304gp42.data.DishOption;

/**
 * Widget used in OptionEditDialog, can represent {@link DishOption.Option}
 */
class OptionEditWidget extends HBox {
    private final TextField opt_name = new TextField();
    private final CurrencyField price = new CurrencyField();

    public OptionEditWidget(){
        intiUi();
    }

    public OptionEditWidget(DishOption.Option opt){
        intiUi();
        opt_name.setText(opt.option);
        price.setText(String.valueOf(opt.price));
    }

    private void intiUi(){
        //Input
        price.setText("0.00");
        getChildren().addAll(new Label("Option: "), opt_name, new Label("Price: "), price);
        //Delete
        Button del_btn = new Button("Del");
        getChildren().add(del_btn);
        del_btn.setOnMouseClicked(event -> {
            fireEvent(new DeleteRequestEvent(DeleteRequestEvent.DELETE_REQUEST_EVENT, this));
        });

    }

    /**
     * Get Option name
     * @return Option Name
     */
    public String getName(){return opt_name.getText();}

    /**
     * Get Option price
     * @return Option price
     */
    public double getPrice(){return price.getDoubleValue();}

    /**
     * Handle {@link DeleteRequestEvent}.
     * @param handler Handler
     */
    public void setOnDeletedRequest(EventHandler<DeleteRequestEvent> handler){
        this.addEventHandler(DeleteRequestEvent.DELETE_REQUEST_EVENT, handler);
    }

}
