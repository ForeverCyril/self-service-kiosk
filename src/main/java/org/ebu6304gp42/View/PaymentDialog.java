package org.ebu6304gp42.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.ebu6304gp42.Controller.Payment.PaymentController;

import java.io.IOException;

public class PaymentDialog extends Dialog<Boolean> {
    public PaymentDialog(double price){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Payment.fxml"));
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        PaymentController controller = loader.getController();
        controller.setPrice(price);

        getDialogPane().setContent(loader.getRoot());
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Button okBtn = (Button) getDialogPane().lookupButton(ButtonType.OK);
        Button cancelBtn = (Button) getDialogPane().lookupButton(ButtonType.CANCEL);
        okBtn.disableProperty().bind(controller.getPaidProperty().not());
        cancelBtn.disableProperty().bind(controller.getPaidProperty());

        setResultConverter(btn->{
            return btn == ButtonType.OK;
        });
    }
}
