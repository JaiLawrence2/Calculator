package edu.jsu.mcis.cs408.calculator;

public enum OperatorChoice {
    ADDITION("\\+"),
    SUBTRACTION("-"),
    MULTIPLICATION("×"),
    DIVISION("÷"),
    SQRT("√"),
    PERCENT("%"),
    PLUSMINUS("±");

    private final String sign;

    OperatorChoice(String sign){
        this.sign = sign;
    }
    public String grabSign(){
        return sign;
    }
}

