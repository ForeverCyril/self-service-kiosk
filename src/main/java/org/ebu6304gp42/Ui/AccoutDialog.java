package org.ebu6304gp42.Ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.Data.Account;
import org.ebu6304gp42.Data.AccountBank;

public class AccoutDialog {
    static class RegisterDialog extends Dialog<Account> {
        public RegisterDialog(){
        /* BorderPane root top :info_area
           BorderPane root bottom : vBox
        */
            VBox root = new VBox();

            GridPane info_area = new GridPane();
            info_area.setPadding(new Insets(10, 10, 10, 10));
            info_area.setVgap(10);
            info_area.setHgap(5);

            TextField first_name = new TextField();
            first_name.setPromptText("Enter your first name.");

            TextField last_name = new TextField();
            last_name.setPromptText("Enter your last name.");

            TextField tel = new TextField();
            tel.setPromptText("Enter your telephone number.");

            TextField email = new TextField();
            email.setPromptText("Enter your email.");

            info_area.addRow(0, new Label("First Name: "), first_name);
            info_area.addRow(1, new Label("Last Name: "), last_name);
            info_area.addRow(2, new Label("Phone: "), tel);
            info_area.addRow(3, new Label("E-Mail: "), email);

            VBox check_area = new VBox();
            check_area.setSpacing(16);
            CheckBox user_agreement = new CheckBox("Agree to user agreement");
            CheckBox receive = new CheckBox("Receive offers");
            check_area.getChildren().addAll(user_agreement,receive);

            root.setSpacing(32);
            root.getChildren().addAll(info_area, check_area);

            getDialogPane().setContent(root);
            getDialogPane().setPadding(new Insets(6));

            // Set the button types.
            getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            getDialogPane().lookupButton(ButtonType.OK).disableProperty()
                    .bind(user_agreement.selectedProperty().not());

            setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    AccountBank acc = new AccountBank();
                    acc.register(
                            first_name.getText(),
                            last_name.getText(),
                            tel.getText(),
                            email.getText(),
                            user_agreement.isSelected()
                    );
                }
                return null;
            });
        }
    }
}
