package edu.jsu.mcis.cs408.calculator;

import android.util.Log;
import android.widget.TextView;

import java.math.BigDecimal;

public class CalculatorModel extends CalculatorAbstractModel {
    public static final String TAG = "CalculatorModel";
    public static CalculatorState state;
    //public static String lhs;
    public static BigDecimal lhs;
    private static OperatorChoice currentChoice;
    private BigDecimal rhs;
    StringBuilder left = new StringBuilder();
    StringBuilder right = new StringBuilder();
    //private OperatorChoice currentoperator = OperatorChoice;

    public CalculatorModel() {
        state = CalculatorState.CLEAR;
        lhs = BigDecimal.ZERO;
        rhs = BigDecimal.ZERO;
    }
    public CalculatorState setNewDigit(String newText) {
        switch (state) {
            case CLEAR:
                left.append(newText);
                setLhs(left.toString());
                state = CalculatorState.LHS;
                break;
            case LHS:
                isOperator(newText);
                if (isOperator(newText) == true){
                    Operators(newText);
                    state = CalculatorState.OP_SELECTED;
                    Log.i(TAG, "OPERATOR: "+newText);
                }
                else if (newText.equals(".")){
                    left.append(newText);
                    setLhs(left.toString());
                    firePropertyChange(CalculatorController.NEW_DIGIT, null, lhs);
                }
                else{
                    left.append(newText);
                    setLhs(left.toString());
                }
                break;
            case OP_SELECTED:
                state = CalculatorState.RHS;
                right.append(newText);
                setRhs(right.toString());

                break;
            case RHS:
                if (newText == "="){
                    state = CalculatorState.RESULT;
                }
                else{
                    right.append(newText);
                    setRhs(right.toString());
                }

                break;
            case RESULT:
                if (newText == "="){
                    calculate(lhs,currentChoice,rhs);
                }
                calculate(lhs,currentChoice,rhs);

            case ERROR:

                state = CalculatorState.CLEAR;
                break;
        }
        return state;
    }
public boolean isOperator(String newText){
        return newText.equals("+")|| newText.equals("-")||newText.equals("×")||newText.equals("÷")||newText.equals("√");
}

    public static void Operators(String operator) {
        if (operator.matches(OperatorChoice.ADDITION.grabSign())) {
            OperatorChoice newChoice = OperatorChoice.ADDITION;
            setOperatorChoice(newChoice);
            Log.d("Test5", "This works. New Operator = " + newChoice.grabSign());
        } else if (operator.matches(OperatorChoice.SUBTRACTION.grabSign())) {
            OperatorChoice newChoice = OperatorChoice.SUBTRACTION;
            setOperatorChoice(newChoice);
            Log.d("Test5", "This works. New Operator = " + newChoice.grabSign());
        } else if (operator.matches(OperatorChoice.MULTIPLICATION.grabSign())) {
            OperatorChoice newChoice = OperatorChoice.MULTIPLICATION;
            setOperatorChoice(newChoice);
            Log.d("Test5", "This works. New Operator = " + newChoice.grabSign());
        } else if (operator.matches(OperatorChoice.DIVISION.grabSign())) {
            OperatorChoice newChoice = OperatorChoice.DIVISION;
            setOperatorChoice(newChoice);
            Log.d("Test5", "This works. New Operator = " + newChoice.grabSign());
        }
    }
    public BigDecimal calculate(BigDecimal lhs,OperatorChoice currentChoice, BigDecimal rhs) {
        BigDecimal result = BigDecimal.ZERO;
        switch (currentChoice) {
            case ADDITION:
                getOperator();
                result = lhs.add(rhs);
                Log.i(TAG, "result is "+result);
                break;
            case SUBTRACTION:
                result = lhs.subtract(rhs);
                break;
            case MULTIPLICATION:
                result = lhs.multiply(rhs);
                break;
            case DIVISION:
                if (rhs.compareTo(BigDecimal.ZERO) != 0) {
                    result = lhs.divide(rhs);
                } else {
                    throw new ArithmeticException("Cannot divide by Zero");
                }
                break;
            case SQRT:
                result = BigDecimal.valueOf(Math.sqrt(lhs.doubleValue()));
                break;
        }
        return result;
    }

    public void setLhs(String newText) {
        BigDecimal old_lhs = this.lhs;
        lhs = new BigDecimal(newText);
        firePropertyChange(CalculatorController.NEW_DIGIT,null,lhs);
        Log.i(TAG,"LHS: "+ lhs);
        Log.i(TAG, "Value change from "+old_lhs+ "to "+lhs);
    }

    public void setRhs(String newText) {
        BigDecimal old_rhs = this.rhs;
        rhs = new BigDecimal(newText);
        firePropertyChange(CalculatorController.NEW_DIGIT,null,rhs);
        Log.i(TAG,"RHS: "+ rhs);
        Log.i(TAG, "Value change from "+old_rhs+ "to "+rhs);
    }

    public BigDecimal getLhs() {
        return lhs;
    }

    public BigDecimal getRhs() {
        return rhs;
    }
    public static OperatorChoice getOperator(){
        return currentChoice;
    }

    public static void setOperatorChoice(OperatorChoice newChoice){
        currentChoice = newChoice;
    }

    /*public OperatorChoice getOperator() {
        return operator;
    }*/

    /*public void setOperator(OperatorChoice operator) {
        this.operator = operator;
    }*/
}