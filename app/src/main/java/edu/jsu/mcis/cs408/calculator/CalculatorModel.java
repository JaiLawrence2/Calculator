package edu.jsu.mcis.cs408.calculator;

import java.math.BigDecimal;

public class CalculatorModel extends CalculatorAbstractModel {
    public enum CalculatorState {
        CLEAR,
        LHS,
        OP_SELECTED,
        RHS,
        RESULT,
        ERROR
    }
    /*public void initDefault(){
        CalculatorState state = CalculatorState.CLEAR;
    }*/
    private CalculatorState state;

    private BigDecimal lhs;
    private BigDecimal rhs;
    String operator;
    StringBuilder s = new StringBuilder();

    public CalculatorModel(){
        state = CalculatorState.CLEAR;
    }

    public void calculation(){
        switch (state){
            case CLEAR:
                //
            case LHS:
                //
            case OP_SELECTED:
                //
            case RHS:
                //
            case RESULT:
                //
            case ERROR:
                //
        }
    }

    public void setState(CalculatorState newstate) {
        state = newstate;
    }
    public void setLhs(BigDecimal lhs) {

        //String oldTv = this.lhs;

        this.lhs = lhs;
        state = CalculatorState.LHS;
        //StringBuilder s = new StringBuilder();
        //s.append(newText);

        //firePropertyChange(NEW_DIGIT);
    }
    public void setRhs(BigDecimal rhs) {
        this.rhs = rhs;
        state = CalculatorState.RHS;
        //firePropertyChange();
    }
    public CalculatorState getState() {
        return state;
    }
    public BigDecimal getLhs() {
        if (state == CalculatorState.CLEAR){
            s.append(lhs);
        }
        return lhs;
    }
    public BigDecimal getRhs() {
        return rhs;
    }

    public void setNewDigit(CalculatorController NewDigit){
        StringBuilder s = new StringBuilder();
        s.append(NewDigit);
        switch (state) {
            case CLEAR:
            case LHS:
            case RHS:
                if (NewDigit.equals("[0-9.]")) {
                    s.append(NewDigit);
                }
                break;
            case OP_SELECTED:
                if (NewDigit.equals("[0-9.]")) {
                    state = CalculatorState.RHS;
                    s.setLength(0); // Clear the input buffer
                    s.append(NewDigit);
                }
                break;
            case RESULT:
                state = CalculatorState.CLEAR;
                s.setLength(0); // Clear the input buffer
                s.append(NewDigit);
                break;
            case ERROR:
                state = CalculatorState.CLEAR;
                s.setLength(0); // Clear the input buffer
                s.append(NewDigit);
                break;
        }
    }

    /*public BigDecimal calculate(BigDecimal lhs, String operator, BigDecimal rhs){
           BigDecimal result = BigDecimal.valueOf(0);
           switch(operator){
                case "+":
                    result = lhs.add(rhs);
                    break;
                case "-":
                    result = lhs.subtract(rhs);
                    break;
                case "*":
                    result = lhs.multiply(rhs);
                    break;
                case "\u00F7":
                    if (rhs.compareTo(BigDecimal.ZERO) != 0){
                        result = lhs.divide(rhs);
                    }
                    else
                        throw new ArithmeticException("Cannot divide by Zero");
                    break;
            }
            return result;
        }*/
    /*public void updateOperand(String input) {
        switch (state) {
            case CLEAR:
            case LHS:
            case RHS:
                if (input.matches("[0-9.]")) {
                    s.append(input);
                }
                break;
            case OP_SELECTED:
                if (input.matches("[0-9.]")) {
                    state = CalculatorState.RHS;
                    s.setLength(0); // Clear the input buffer
                    s.append(input);
                }
                break;
            case RESULT:
                state = CalculatorState.CLEAR;
                s.setLength(0); // Clear the input buffer
                s.append(input);
                break;
            case ERROR:
                state = CalculatorState.CLEAR;
                s.setLength(0); // Clear the input buffer
                s.append(input);
                break;
        }
    }*/
}