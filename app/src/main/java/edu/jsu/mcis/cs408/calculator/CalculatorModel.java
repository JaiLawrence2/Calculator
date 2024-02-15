package edu.jsu.mcis.cs408.calculator;

import android.widget.Button;

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

    public class CalcState{
        CalculatorState state;

        BigDecimal lhs;
        BigDecimal rhs;
    }
}
