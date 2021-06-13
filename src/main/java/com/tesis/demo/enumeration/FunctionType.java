package com.tesis.demo.enumeration;

public enum FunctionType {
    FUNCTION_STANDARD_DEVIATION(1, "FUNCTION_STANDARD_DEVIATION"),
    FUNCTION_ARITHMETIC_AVERAGE(2, "FUNCTION_ARITHMETIC_AVERAGE"),
    FUNCTION_MINIMUM(3, "FUNCTION_MINIMUM"),
    FUNCTION_MAXIMUM(4, "FUNCTION_MAXIMUM");


    private final int id;
    private final String name;

    FunctionType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
