package org.ebu6304gp42.Component.InputField;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CurrencyField extends AbstractNumberInputField {
    private static final String CurrencySymbol = "$";
    private final static int decimalScale = 2;

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

    protected BigDecimal getCurrencyValue() {
        return getDecimalValue('.');
    }


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



    @Override
    protected String getFormatter() {
        return String.format("%s%s%s", CurrencySymbol, BASE_PATTERN, ".##");
    }

    @Override
    public BigDecimal getValue() {
        return getCurrencyValue();
    }

    public double getDoubleValue(){
        return getValue()!=null?getValue().doubleValue():0.0;
    }
}
