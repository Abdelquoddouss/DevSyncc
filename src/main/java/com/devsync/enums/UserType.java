package com.devsync.enums;

public enum UserType {

    USER("User"),
    MANAGER("Manager");

    private String label; // This field holds a label for each type

    // Constructor
    UserType(String label) {
        this.label = label;
    }

    // Getter method to access the label of each enum constant
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

}
