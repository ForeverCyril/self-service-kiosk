package org.ebu6304gp42.component.inputField;

import java.math.BigDecimal;

/**
 * A text input filed to input phone number. Can auto format text(eg, 123-1234-1234)
 */
public class PhoneField extends AbstractNumberInputField {

    /**
     * No used in this case.
     * @return empty string
     */
    @Override
    protected String getFormatter() {
        return "";
    }

    /**
     * Control input data, only allowed number.
     * @param value String need to determinate.
     * @return Is valid?
     */
    @Override
    protected boolean isValid(String value) {
        if(value == null || value.isEmpty()){
            return true;
        }
        try {
            getDecimalValue();
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * Format text as phone number.
     * @param pattern Pattern for format
     * @return text after format
     */
    @Override
    protected String formatValue(String pattern) {
        if(getText() != null && !getText().isBlank()){
            BigDecimal curValue = getDecimalValue();
            if (curValue == null){return null;}
            StringBuilder data = new StringBuilder();
            data.append(curValue.toString());

            if(data.length() > 7){
                data.insert(3, '-').insert(8, '-');
            } else if(data.length() > 3) {
                data.insert(3, '-');
            }
            if(data.length() > 13){
                return data.substring(0,13);
            }
            return data.toString();
        }
        return null;
    }

    /**
     * Get Phone number
     * @return phone number as string.
     */
    @Override
    public String getValue() {
        return getDecimalValue()==null?null:String.valueOf(getDecimalValue());
    }

    /**
     * Value of the text
     * @return value of the text
     */
    private BigDecimal getDecimalValue(){
        if(getText() == null || getText().isBlank()){
            return null;
        }
        return new BigDecimal(getText().replace("-",""));
    }
}
