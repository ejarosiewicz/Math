package com.ejarosiewicz.utils;

import java.util.Stack;

/**
 * Created by 3mill on 2016-03-07.
 */
public class FunctionCalculator {

    public Stack<String> originExpressionStack;
    public Stack<String> expressionStack;
    public Stack<Float> numberStack;

    public FunctionCalculator(Stack<String> originExpressionStack) {
        this.originExpressionStack = originExpressionStack;
    }

    public Float calculate(float x){
        expressionStack = (Stack) originExpressionStack.clone();
        numberStack = new Stack<>();
        while (!expressionStack.empty()) {

            String value = expressionStack.pop();
            if (isDigitsOnly(value)) {
                numberStack.push(Float.parseFloat(value));
            } else if (isExpression(value)) {
                doExpression(value);
            } else if (value.equals(Constants.FUNC_X)) {
                numberStack.push(x);
            } else if (value.equals(Constants.FUNC_MINUS_X)) {
                numberStack.push(-x);
            }
        }
        return numberStack.pop();
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

    private void doExpression(String expression){
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
        numberStack.push(value);
    }

    private boolean isExpression(String value){
        return value.equals(Constants.ADD)
                ||value.equals(Constants.SUB)
                ||value.equals(Constants.MUL)
                ||value.equals(Constants.DIV);
    }

}
