package edu.jsu.mcis.cs408.calculator;

import android.util.Log;

import java.math.BigDecimal;

public class CalculatorModel extends CalculatorAbstractModel {
    public static final String TAG = "CalculatorModel";
    private static CalculatorState state;
    public static BigDecimal lhs;
    private static OperatorChoice currentChoice = null;
    private BigDecimal rhs;
    StringBuilder left = new StringBuilder();
    StringBuilder right = new StringBuilder();
    StringBuilder result = new StringBuilder();
    private BigDecimal result_one;

    public CalculatorModel() {
        state = CalculatorState.CLEAR;
        lhs = BigDecimal.ZERO;
        rhs = BigDecimal.ZERO;
        result_one = BigDecimal.ZERO;
    }
    public void setNewDigit(String newText) {
        CalculatorState newstate = null;
        switch (state) {
            case CLEAR:
                newstate = CalculatorState.LHS;
                left.append(newText);
                setLhs(left.toString());
                break;
            case LHS:
                isOperator(newText);
                if (isOperator(newText)){
                    newstate = CalculatorState.OP_SELECTED;
                    Log.i(TAG, "newText = " + newText);
                    Operators(newText);
                    Log.i(TAG, "OPERATOR: "+currentChoice+ " State: "+state);
                    break;
                }
                else if (newText.equals(".")){
                    left.append(newText);
                    setLhs(left.toString());
                    break;
                }
                /*else if (newText.equals("√")){
                    left.append(Math.sqrt(Double.parseDouble(newText)));
                    setLhs(left.toString());
                    firePropertyChange(CalculatorController.NEW_DIGIT, null, lhs);
                }*/
                else if(newText.equals("C")){
                    newstate = CalculatorState.CLEAR;
                    reset();
                }
                else{
                    left.append(newText);
                    setLhs(left.toString());
                }
                break;
            case OP_SELECTED:
                newstate = CalculatorState.RHS;
                right.append(newText);
                setRhs(right.toString());
                break;
            case RHS:
                if (newText.equals("=")){
                    newstate = CalculatorState.RESULT;
                    Log.i(TAG, "State is" + state);
                    Log.i(TAG, "Values are " + lhs);
                    Log.i(TAG, "Values are " + currentChoice);
                    Log.i(TAG, "Values are " + rhs);
                    calculate(lhs,currentChoice,rhs);
                    break;
                }
                right.append(newText);
                setRhs(right.toString());

                break;
            case RESULT:
                if (newText.equals("C")){
                    newstate = CalculatorState.CLEAR;
                    reset();
                    break;
                }
            case ERROR:
                newstate = CalculatorState.CLEAR;
                break;
        }
        if (newstate != null){
            this.state = newstate;
        }
    }

    private void reset() {
        left.setLength(0);
        right.setLength(0);
        lhs = BigDecimal.ZERO;
        rhs = BigDecimal.ZERO;
        //result.setLength(0);
        result_one = BigDecimal.ZERO;
        firePropertyChange(CalculatorController.NEW_DIGIT, null, result_one);
    }

    public boolean isOperator(String newText){
        if (newText.equals("+")|| newText.equals("-")||newText.equals("×")||newText.equals("÷")){
            return true;
        }
        else{
            return false;
        }
    }

    private static OperatorChoice Operators(String newText) {
        if (newText.matches(OperatorChoice.ADDITION.grabSign())) {
            OperatorChoice newChoice = OperatorChoice.ADDITION;
            setOperatorChoice(newChoice);
            Log.i(TAG, "Operator = " + newChoice.grabSign());
            Log.i(TAG, "Operator = " + currentChoice);
        } else if (newText.matches(OperatorChoice.SUBTRACTION.grabSign())) {
            OperatorChoice newChoice = OperatorChoice.SUBTRACTION;
            setOperatorChoice(newChoice);
            Log.i(TAG, "Operator = " + newChoice.grabSign());
        } else if (newText.matches(OperatorChoice.MULTIPLICATION.grabSign())) {
            OperatorChoice newChoice = OperatorChoice.MULTIPLICATION;
            setOperatorChoice(newChoice);
            Log.i(TAG, "Operator = " + newChoice.grabSign());
        } else if (newText.matches(OperatorChoice.DIVISION.grabSign())) {
            OperatorChoice newChoice = OperatorChoice.DIVISION;
            setOperatorChoice(newChoice);
            Log.i(TAG, "Operator = " + newChoice.grabSign());
        }
        else if(newText.matches(OperatorChoice.SQRT.grabSign())){
            OperatorChoice newChoice = OperatorChoice.SQRT;
            setOperatorChoice(newChoice);
            Log.i(TAG, "Operator = " + newChoice.grabSign());
        }
        Log.i(TAG, "Operator = " + currentChoice.grabSign());
        return currentChoice;
    }
    public void calculate(BigDecimal lhs,OperatorChoice currentChoice, BigDecimal rhs) {
        if (currentChoice.equals(OperatorChoice.ADDITION)){
            result.append(lhs.add(rhs));
            setResult(result.toString());
            Log.i(TAG, "result is "+result_one);
        }
        else if (currentChoice.equals(OperatorChoice.SUBTRACTION)){
            result.append(lhs.subtract(rhs));
            setResult(result.toString());
            Log.i(TAG, "result is "+result_one);
        }
        else if (currentChoice.equals(OperatorChoice.MULTIPLICATION)) {
            result.append(lhs.multiply(rhs));
            setResult(result.toString());
            Log.i(TAG, "result is " + result_one);
        }
        else if(currentChoice.equals(OperatorChoice.DIVISION)) {
            result.append(lhs.divide(rhs));
            setResult(result.toString());
            Log.i(TAG, "result is "+result_one);
        }
        else if(currentChoice.equals(OperatorChoice.SQRT)){
            result.append(rhs.multiply(BigDecimal.valueOf(Long.parseLong("0.5"))));
            setResult(result.toString());
            Log.i(TAG, "result is: "+result_one);
        }

            /*case SQRT:
                result.append(BigDecimal.valueOf(Math.sqrt(lhs.doubleValue())));
                setResult(result.toString());
                break;
            case PERCENT:
                break;
            case PLUSMINUS:
                break;
        }*/
    }

    private void setResult(String result) {
        BigDecimal old_result = this.result_one;
        result_one = new BigDecimal(result.toString());
        Log.i(TAG, "Result: "+result_one);
        firePropertyChange(CalculatorController.NEW_DIGIT, old_result, result_one);
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
        firePropertyChange(CalculatorController.NEW_DIGIT, null,rhs);
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


}