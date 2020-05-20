package org.ebu6304gp42.component.InputField;

import java.math.BigDecimal;

public class PhoneField extends AbstractNumberInputField {

    @Override
    protected String getFormatter() {
        return ",###";
    }

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

    @Override
    public String getValue() {
        return getDecimalValue()==null?null:String.valueOf(getDecimalValue());
    }

    private BigDecimal getDecimalValue(){
        if(getText() == null || getText().isBlank()){
            return null;
        }
        return new BigDecimal(getText().replace("-",""));
    }
}
