package org.ebu6304gp42.component.inputField;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * A Currency Input Field. Can Show unit and the separator. (eg. 1,234.34 £)
 */
public class CurrencyField extends AbstractNumberInputField {
    private static final String CurrencySymbol = "£";
    private final static int decimalScale = 2;

    /**
     * Control input data, only allowed number.
     * @param value String need to determinate.
     * @return Is valid?
     */
    @Override
    protected boolean isValid(String value) {
        if(value == null || value.isBlank()){
            return true;
        }
        try {
            getCurrencyValue();
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * Get currency value of the text
     * @return text value
     */
    protected BigDecimal getCurrencyValue() {
        return getDecimalValue('.');
    }

    /**
     * Get value as decimal
     * @param separator separator used in text
     * @return value of the text
     */
    private BigDecimal getDecimalValue(final char separator){
        if(getText() == null || getText().isBlank()){
            return null;
        }
        int pos = getText().indexOf(separator);
        if(pos > -1){
            int length = getText().length() - (pos + 1);
            if(length > decimalScale){
                throw new NumberFormatException("Scale error");
            }
        }
        String value_str = getText().replace(",","").replace(CurrencySymbol, "");
        if(value_str.isBlank()){
            return new BigDecimal(0);
        }
        return new BigDecimal(value_str);
    }

    /**
     * Format text as currency.
     * @param pattern Pattern for format
     * @return text after format
     */
    @Override
    protected String formatValue(String pattern) {
        if(getText() != null && !getText().isBlank()){
            if(getText().endsWith(".")){
                return getText();
            }
            if(getText().endsWith(CurrencySymbol)){
                return "";
            }
            DecimalFormat formatter = new DecimalFormat(pattern);
            BigDecimal curValue = getDecimalValue('.');
            return formatter.format(curValue);
        }
        return null;
    }


    /**
     * Get text formatter
     * @return text formatter
     */
    @Override
    protected String getFormatter() {
        return String.format("%s%s%s", CurrencySymbol, BASE_PATTERN, ".##");
    }

    /**
     * Get text value
     * @return value of the text
     */
    @Override
    public BigDecimal getValue() {
        return getCurrencyValue();
    }

    /**
     * Get value as double
     * @return double value
     */
    public double getDoubleValue(){
        return getValue()!=null?getValue().doubleValue():0.0;
    }
}
