package edu.jsu.mcis.cs408.calculator;

public class CalculatorController extends CalculatorAbstractController{
    public static final String NEW_DIGIT = "NewDigit";
    public void changeTextView (String newdigit){
        setModelProperty(NEW_DIGIT, newdigit);
    }

    /*public void processButton(String buttonTag) {
        model.processButton(buttonTag);
    }*/

}
