package org.ebu6304gp42.component.InputField;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.text.DecimalFormatSymbols;

public abstract class AbstractNumberInputField extends TextField {
    protected final static String BASE_PATTERN = ",###";
    private final static DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    public AbstractNumberInputField(){
        super();

        // Check Input
        addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if(!isValid(event.getText())){
                event.consume();
            }
        });

        // Format Value
        textProperty().addListener((observable, oldValue, newValue) -> {
            if(!isValid(newValue)){
                setText(oldValue);
            }
            // get the new pos of caret
            int pos = getCaretPosition() + (newValue == null?0:newValue.length()) - (oldValue==null?0:oldValue.length());
            //Must use Run Later, or you will get an IllegalArgument Exception, due to the UI Thread will update data
            // while we change data.
            Platform.runLater(()->{
                setText(formatValue(getFormatter()));
                positionCaret(pos);
            });
        });
    }

    protected String getFormatter(){
        return BASE_PATTERN;
    }
    protected abstract boolean isValid(String value);
    protected abstract String formatValue(String pattern);
    public abstract Object getValue();
}
