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
    public void initDefault(){
        CalculatorState state = CalculatorState.CLEAR;
    }
        private CalculatorState state;

        private BigDecimal lhs;
        private BigDecimal rhs;
        String operator;

        public CalculatorState getState() {
            return state;
        }

        public void setState(CalculatorState state) {
            this.state = state;
        }

        public BigDecimal getLhs() {
            return lhs;
        }

        public void setLhs(BigDecimal lhs) {
            this.lhs = lhs;
        }

        public BigDecimal getRhs() {
            return rhs;
        }

        public void setRhs(BigDecimal rhs) {
            this.rhs = rhs;
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
    }
