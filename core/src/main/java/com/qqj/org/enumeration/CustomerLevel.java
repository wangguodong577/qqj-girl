package com.qqj.org.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CustomerLevel {
    LEVEL_0((short)0, "总代"),
    LEVEL_1((short)1, "一级代理"),
    LEVEL_2((short)2, "二级代理");

    private Short value;
    private String name;

    public Short getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    private CustomerLevel(Short value, String name) {
        this.value = value;
        this.name = name;
    }

    public static CustomerLevel get(Short value) {
        for (CustomerLevel i : CustomerLevel.values()) {
            if (i.value.equals(value)) {
                return i;
            }
        }
        return null;
    }
}
