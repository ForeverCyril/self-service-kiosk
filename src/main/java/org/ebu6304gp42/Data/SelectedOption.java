package org.ebu6304gp42.Data;

public class SelectedOption{
    private String name;
    private String selected_option;

    public SelectedOption(String name, String selected_option) {
        this.name = name;
        this.selected_option = selected_option;
    }

    public SelectedOption() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelected_option() {
        return selected_option;
    }

    public void setSelected_option(String selected_option) {
        this.selected_option = selected_option;
    }
}
