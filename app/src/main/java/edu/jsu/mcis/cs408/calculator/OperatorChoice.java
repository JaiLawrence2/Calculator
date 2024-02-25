package edu.jsu.mcis.cs408.calculator;

public enum OperatorChoice {
    ADDITION("\\+"),
    SUBTRACTION("-"),
    MULTIPLICATION("×"),
    DIVISION("÷"),
    SQRT("√");

    private final String symbol;

    OperatorChoice(String symbol){
        this.symbol = symbol;
    }
    public String getSymbol(){
        return symbol;
    }
}

