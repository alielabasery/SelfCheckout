package com.autovend.software.pojo;

import java.util.Arrays;

public class ProductDescriptionEntry {
    private String descriptiption;
    private char type;
    private Object code;

    public ProductDescriptionEntry(String descriptiption, char type, Object code) {
        this.descriptiption = descriptiption;
        this.type = type;
        this.code = code;
    }

    public String getDescriptiption() {
        return descriptiption;
    }

    public void setDescriptiption(String descriptiption) {
        this.descriptiption = descriptiption;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }
}
