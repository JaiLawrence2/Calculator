package edu.jsu.mcis.cs408.calculator;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorModel extends CalculatorAbstractModel {
    public static final String TAG = "CalculatorModel";
    private static CalculatorState state;
    private static BigDecimal lhs;
    private static OperatorChoice currentChoice = null;
    private static BigDecimal rhs;
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
                if (isOperator(newText)) {
                    if (state.equals(CalculatorState.LHS)){
                        newstate = CalculatorState.OP_SELECTED;
                        Operators(newText);
                        break;
                    }

                }
                else if (newText.equals("%")) {
                    PercentClick();
                    break;
                } else if (newText.equals("±")) {
                    PlusMinusClick();
                    break;
                }
                else if (newText.equals("C")) {
                    newstate = CalculatorState.CLEAR;
                    reset();
                }else if (newText.equals("√")) {
                    BigDecimal value = new BigDecimal(left.toString());
                    if (value.compareTo(BigDecimal.ZERO) >= 0) {
                        result_one = BigDecimal.valueOf(Math.sqrt(value.doubleValue()));
                        setLhs(result_one.toString());
                    }
                    break;
                } else {
                    left.append(newText);
                    setLhs(left.toString());
                }
                break;
            case OP_SELECTED:
                if (newText.equals("=")) {
                    calculate(lhs, currentChoice, rhs); // Calculate the result
                    newstate = CalculatorState.RESULT;
                    break;
                }
                newstate = CalculatorState.RHS;
                right.append(newText);
                setRhs(right.toString());
                break;
            case RHS:
                if (newText.equals("=")) {
                    newstate = CalculatorState.RESULT;
                    calculate(lhs, currentChoice, rhs);
                    break;
                }else if (newText.equals("±")) {
                    PlusMinusClick();
                    break;
                }else if (newText.equals("%")) {
                    PercentClick();
                    break;
                }else if (newText.equals("√")) {
                    BigDecimal value = new BigDecimal(right.toString());
                    if (value.compareTo(BigDecimal.ZERO) >= 0) {
                        result_one = BigDecimal.valueOf(Math.sqrt(value.doubleValue()));
                        setRhs(result_one.toString());
                        break;
                    }
                }
                right.append(newText);
                setRhs(right.toString());
                break;
            case RESULT:
                if (newText.equals("C")) {
                    newstate = CalculatorState.CLEAR;
                    reset();
                    break;
                }
                else if (isOperator(newText)){
                    Operators(newText);
                    lhs = result_one;
                    state = CalculatorState.OP_SELECTED;
                    right.setLength(0);
                    result.setLength(0);
                    Log.i(TAG, "LHS is now equal to: "+lhs);
                    break;
                }else if (newText.equals("√")) {
                    BigDecimal value = new BigDecimal(result.toString());
                    if (value.compareTo(BigDecimal.ZERO) >= 0) {
                        result_one = BigDecimal.valueOf(Math.sqrt(value.doubleValue()));
                        setRhs(result_one.toString());
                        break;
                    }
                }
            case ERROR:
                newstate = CalculatorState.CLEAR;
                break;
        }
        if (newstate != null) {
            this.state = newstate;
        }
    }

    private void PlusMinusClick() {
        if (state == CalculatorState.LHS) {
            lhs = lhs.negate();
            setLhs(lhs.toString());
        } else if (state == CalculatorState.RHS) {
            rhs = rhs.negate();
            setRhs(rhs.toString());
        }
    }

    public boolean isOperator(String newText) {
        return newText.equals("+") || newText.equals("-") || newText.equals("×") || newText.equals("÷"); //|| newText.equals("√");
    }
    private static OperatorChoice Operators(String newText) {
        if (newText.matches(OperatorChoice.ADDITION.grabSign())) {
            OperatorChoice newChoice = OperatorChoice.ADDITION;
            setOperatorChoice(newChoice);
            Log.i(TAG, "Operator = " + newChoice.grabSign());
            Log.i(TAG, "Operator = " + currentChoice);
        }
        else if (newText.matches(OperatorChoice.SUBTRACTION.grabSign())) {
            OperatorChoice newChoice = OperatorChoice.SUBTRACTION;
            setOperatorChoice(newChoice);
            Log.i(TAG, "Operator = " + newChoice.grabSign());
        }
        else if (newText.matches(OperatorChoice.MULTIPLICATION.grabSign())) {
            OperatorChoice newChoice = OperatorChoice.MULTIPLICATION;
            setOperatorChoice(newChoice);
            Log.i(TAG, "Operator = " + newChoice.grabSign());
        }
        else if (newText.matches(OperatorChoice.DIVISION.grabSign())) {
            OperatorChoice newChoice = OperatorChoice.DIVISION;
            setOperatorChoice(newChoice);
            Log.i(TAG, "Operator = " + newChoice.grabSign());
        }
        else if (newText.matches(OperatorChoice.SQRT.grabSign())) {
            OperatorChoice newChoice = OperatorChoice.SQRT;
            setOperatorChoice(newChoice);
            Log.i(TAG, "Operator = " + newChoice.grabSign());
        }
        else if (newText.matches(OperatorChoice.PERCENT.grabSign())){
            OperatorChoice newChoice = OperatorChoice.PERCENT;
            setOperatorChoice(newChoice);
        }
        return currentChoice;
    }

    public void calculate(BigDecimal lhs, OperatorChoice currentChoice, BigDecimal rhs) {
        if (currentChoice.equals(OperatorChoice.ADDITION)) {
            result.append(lhs.add(rhs));
            setResult(result.toString());
        } else if (currentChoice.equals(OperatorChoice.SUBTRACTION)) {
            result.append(lhs.subtract(rhs));
            setResult(result.toString());
            Log.i(TAG, "result is " + result_one);
        } else if (currentChoice.equals(OperatorChoice.MULTIPLICATION)) {
            result.append(lhs.multiply(rhs));
            setResult(result.toString());
            Log.i(TAG, "result is " + result_one);
        } else if (currentChoice.equals(OperatorChoice.DIVISION)) {
            if (rhs.compareTo(BigDecimal.ZERO) != 0) {
                result.append(lhs.divide(rhs));
                setResult(result.toString());
            } else {
                // Handle division by zero error
                result.append("Cannot divide by 0");
                firePropertyChange(CalculatorController.NEW_DIGIT, null, result.toString());
                state = CalculatorState.ERROR;
            }
            Log.i(TAG, "result is " + result_one);
        } else if (currentChoice.equals(OperatorChoice.SQRT)) {
            if (state.equals(CalculatorState.LHS)){
                result.append(BigDecimal.valueOf(Math.sqrt(lhs.doubleValue())));
                setResult(result.toString());
            }
            else if (state.equals(CalculatorState.RHS)){
                result.append(BigDecimal.valueOf(Math.sqrt(rhs.doubleValue())));
                setRhs(result.toString());
            }
            Log.i(TAG, "state: "+state);
            Log.i(TAG, "result is: " + result_one);
        } else if (currentChoice.equals(OperatorChoice.PERCENT)) {
            BigDecimal operand;
            if (state == CalculatorState.LHS) {
                lhs = lhs.divide(BigDecimal.valueOf(100));
                setLhs(lhs.toString());
            } else if (state == CalculatorState.RHS) {
                rhs = rhs.divide(BigDecimal.valueOf(100));
                setRhs(rhs.toString());
            }
        } else if (currentChoice.equals(OperatorChoice.PLUSMINUS)){
            if (state.equals(CalculatorState.LHS)){
                BigDecimal old_value = getLhs();
                BigDecimal new_value = old_value.negate();
                setLhs(String.valueOf(new_value));
            }
            else {
                BigDecimal old_value = getRhs();
                BigDecimal new_value = old_value.negate();
                setRhs(String.valueOf(new_value));
            }

        }

    }

    private void setResult(String result) {
        BigDecimal old_result = this.result_one;
        result_one = new BigDecimal(result.toString());
        Log.i(TAG, "Result: "+result_one);
        firePropertyChange(CalculatorController.NEW_DIGIT, old_result, result_one);

        if (state.equals(CalculatorState.ERROR)){

        }
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
    public static BigDecimal getLhs() {
        return lhs;
    }

    public static BigDecimal getRhs() {
        return rhs;
    }
   public static OperatorChoice getOperator(){
        return currentChoice;
    }

    public static void setOperatorChoice(OperatorChoice newChoice){
        currentChoice = newChoice;
    }
    private void PercentClick() {
        BigDecimal percentValue = BigDecimal.valueOf(0.01); // Represents 1%

        if (state == CalculatorState.LHS) {
            lhs = lhs.multiply(percentValue);
            setLhs(lhs.toString());

        } else if (state == CalculatorState.RHS) {
            rhs = rhs.multiply(percentValue);
            setRhs(rhs.toString());
        }
    }
    private void reset() {
        left.setLength(0);
        right.setLength(0);
        lhs = BigDecimal.ZERO;
        rhs = BigDecimal.ZERO;
        result.setLength(0);
        result_one = BigDecimal.ZERO;
        firePropertyChange(CalculatorController.NEW_DIGIT, null, result_one);
    }
}