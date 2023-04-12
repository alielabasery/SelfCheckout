package com.autovend.software.pojo;

import java.util.Arrays;

public class ProductDescriptionEntry {
    private String descriptiption;
    private char type;
    private Object code;

    /**
     * Constructor for ProductDescriptionEntry
     * @param descriptiption
     * 		The description
     * @param type
     * 		the type of the product
     * @param code
     * 		the product code
     */
    public ProductDescriptionEntry(String descriptiption, char type, Object code) {
        this.descriptiption = descriptiption;
        this.type = type;
        this.code = code;
    }

    /**
     * Getter for the description
     * @return
     * 		The description
     */
    public String getDescriptiption() {
        return descriptiption;
    }

    /**
     * Sets the description
     * @param descriptiption
     * 		The description to set
     */
    public void setDescriptiption(String descriptiption) {
        this.descriptiption = descriptiption;
    }

    /**
     * Getter for the type
     * @return
     * 		The type for the item
     */
    public char getType() {
        return type;
    }

    /**
     * Sets the type
     * @param type
     * 		Type to be set
     */
    public void setType(char type) {
        this.type = type;
    }

    /**
     * Getter for the code
     * @return
     * 		The code
     */
    public Object getCode() {
        return code;
    }

    /**
     * Setter for the code
     * @param code
     * 		Code to be set.
     */
    public void setCode(Object code) {
        this.code = code;
    }
}
