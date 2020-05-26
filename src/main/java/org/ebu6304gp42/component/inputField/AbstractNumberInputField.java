package org.ebu6304gp42.component.inputField;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.text.DecimalFormatSymbols;

/**
 * Abstract NumberInputField. Can control Display format and limit input.
 */
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

    /**
     * Get Formatter
     * @return The text Formatter.
     */
    protected String getFormatter(){
        return BASE_PATTERN;
    }

    /**
     * Determinate whether a string is valid. Override it to limit the input.
     * @param value String need to determinate.
     * @return Is is valid.
     */
    protected abstract boolean isValid(String value);

    /**
     * Format the text by the given pattern. Override it to control text format.
     * @param pattern Pattern for format
     * @return Formated text
     */
    protected abstract String formatValue(String pattern);

    /**
     * Get the value of the text. Override it to fit your situation.
     * @return value of the text
     */
    public abstract Object getValue();
}
