package com.ejarosiewicz.utils;

import java.util.Stack;

/**
 * Created by Emil Jarosiewicz on 2016-03-07.
 */
public class FunctionCalculator {

    private Stack<String> originExpressionStack;
    private Stack<String> expressionStack;
    private Stack<Float> numberStack;

    private enum Signs {
        DIGITS,
        EXPRESSION,
        VALUE,
        MINUS_VALUE
    }

    public FunctionCalculator(Stack<String> originExpressionStack) {
        this.originExpressionStack = originExpressionStack;
    }

    public Float calculate(float x){
        expressionStack = (Stack) originExpressionStack.clone();
        numberStack = new Stack<>();
        while (!expressionStack.empty()) {

            String value = expressionStack.pop();

            for (Signs sign : Signs.values()) {
                if (valueCondition(sign, value)) {
                    valueActions(sign, value, x);
                    break;
                }
            }
        }
        return numberStack.pop();
    }

    private boolean valueCondition(Signs sign, String value) {
        switch (sign) {
            case DIGITS:
                return isDigitsOnly(value);

            case EXPRESSION:
                return isExpression(value);

            case VALUE:
                return value.equals(Constants.FUNC_X);

            case MINUS_VALUE:
                return value.equals(Constants.FUNC_MINUS_X);

            default:
                return false;
        }
    }

    private void valueActions(Signs sign, String value, float x) {
        Float number = null;

        switch (sign) {
            case DIGITS:
                number = Float.parseFloat(value);
                break;

            case EXPRESSION:
                number = computeFromStack(value);
                break;

            case VALUE:
                number = x;
                break;

            case MINUS_VALUE:
                number = -x;
                break;
        }

        numberStack.push(number);
    }

    private boolean isDigitsOnly(String value) {
        boolean isDigit = true;
        int begin = 0;

        if (value.charAt(0) == Constants.SUB) {
            begin = 1;
        }

        for (int i = begin; i < value.length(); i++){
            if (!Character.isDigit(value.charAt(i))){
                isDigit = false;
            }
        }

        return isDigit;
    }

    private float computeFromStack(String expression) {
        float numberOne = numberStack.pop();
        float numberTwo = numberStack.pop();
        float value = 0;
        switch (expression.charAt(0)) {
            case Constants.ADD:
                value = numberOne + numberTwo;
                break;

            case Constants.SUB:
                value = numberOne - numberTwo;
                break;

            case Constants.MUL:
                value = numberOne * numberTwo;
                break;

            case Constants.DIV:
                value = numberOne / numberTwo;
                break;
        }

        return value;
    }

    private boolean isExpression(String value){
        return value.equals(Constants.ADD)
                ||value.equals(Constants.SUB)
                ||value.equals(Constants.MUL)
                ||value.equals(Constants.DIV);
    }
}
