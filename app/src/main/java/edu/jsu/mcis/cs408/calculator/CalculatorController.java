package edu.jsu.mcis.cs408.calculator;

import android.widget.TextView;

public class CalculatorController extends CalculatorAbstractController{

    public void changeTextView (String textview ){
        setModelProperty(textview, textview);
    }
}
