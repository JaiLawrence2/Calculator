package edu.jsu.mcis.cs408.calculator;

import static edu.jsu.mcis.cs408.calculator.CalculatorController.NEW_DIGIT;

import android.util.Log;

import java.math.BigDecimal;

public class CalculatorModel extends CalculatorAbstractModel {
    public static final String TAG = "CalculatorModel";
    private static CalculatorState state;
    private BigDecimal lhs;
    private BigDecimal rhs;
    private char operator;

    public CalculatorModel() {
        state = CalculatorState.CLEAR;
        lhs = BigDecimal.ZERO;
        rhs = BigDecimal.ZERO;
    }

    public CalculatorState setNewDigit(String newText) {
        switch (state) {
            case CLEAR:
                setLhs(newText);
                state = CalculatorState.LHS;
                break;
            case LHS:
                lhs = lhs.multiply(BigDecimal.TEN).add(new BigDecimal(newText));
                break;
            case OP_SELECTED:
                setRhs(newText);
                state = CalculatorState.RHS;
                break;
            case RHS:
                rhs = rhs.multiply(BigDecimal.TEN).add(new BigDecimal(newText));
                break;
            case RESULT:
            case ERROR:
                state = CalculatorState.CLEAR;
                break;
        }
        return state;
    }

    public BigDecimal calculate(BigDecimal lhs, char operator, BigDecimal rhs) {
        BigDecimal result = BigDecimal.ZERO;
        switch (operator) {
            case '+':
                result = lhs.add(rhs);
                break;
            case '-':
                result = lhs.subtract(rhs);
                break;
            case '*':
                result = lhs.multiply(rhs);
                break;
            case '÷':
                if (rhs.compareTo(BigDecimal.ZERO) != 0) {
                    result = lhs.divide(rhs);
                } else {
                    throw new ArithmeticException("Cannot divide by Zero");
                }
                break;
            case '\u221A':
                result = BigDecimal.valueOf(Math.sqrt(lhs.doubleValue()));
                break;
        }
        return result;
    }

    public void setLhs(String newText) {
        lhs = new BigDecimal(newText);
        Log.i(TAG,"LHS: "+ lhs);
    }

    public void setRhs(String newText) {
        rhs = new BigDecimal(newText);
    }

    public BigDecimal getLhs() {
        return lhs;
    }

    public BigDecimal getRhs() {
        return rhs;
    }

    public char getOperator() {
        return operator;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }
}