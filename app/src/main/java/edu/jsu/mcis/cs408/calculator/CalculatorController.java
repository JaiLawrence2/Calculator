package edu.jsu.mcis.cs408.calculator;

public class CalculatorController extends CalculatorAbstractController{
    public static final String TV = "0";
    public void changeTextView (String newText ){
        setModelProperty(TV, newText);
    }
}
