package com.ejarosiewicz.utils;


import java.util.Collections;
import java.util.Stack;

/**
 * Created by 3mill on 2016-03-08.
 */
public class ReversePolishNotationParser {


    static Stack<String> signStack = new Stack<>();

    static String numberBuffer = "";

    private ReversePolishNotationParser(){}

    public static Stack<String> parseToExpressionStack(String expression) {
        Stack<String> expressionStack = new Stack<>();
        boolean lastOpen = false;
        boolean highPriorityOperator = false;
        for (int i = 0, length = expression.length(); i < length; i++) {

            char singleChar = expression.charAt(i);
            if (lastOpen && singleChar == '-') {
                numberBuffer += singleChar;
            } else if (isMathOperator(singleChar)) {
                pushNumber(expressionStack);
                if (highPriorityOperator){
                    expressionStack.push(signStack.pop());
                    highPriorityOperator = false;
                }
                signStack.push("" + singleChar);
                if (singleChar == '*' || singleChar == '/'){
                    highPriorityOperator = true;
                }
            } else if (lastOpen = singleChar == '(') {
                signStack.push("" + singleChar);
            } else if (lastOpen = singleChar == ')') {
                pushNumber(expressionStack);
                if (highPriorityOperator){
                    expressionStack.push(signStack.pop());
                    highPriorityOperator = false;
                }
                removeToOpen(expressionStack);
            } else if (Character.isDigit(singleChar) || singleChar == Constants.FUNC_X.charAt(0)) {
                numberBuffer += singleChar;
            }
        }
        removeAll(expressionStack);
        Collections.reverse(expressionStack);
        return expressionStack;
    }

    private static void pushNumber(Stack<String> expressionStack) {
        if (numberBuffer.length() > 0) {
            expressionStack.push(numberBuffer);
            numberBuffer = "";
        }
    }

    private static void removeToOpen(Stack<String> expressionStack) {
        String stack;
        while (!signStack.isEmpty() && !(stack = signStack.pop()).equals("(")) {
            expressionStack.push(stack);
        }
    }

    private static void removeAll(Stack<String> expressionStack) {
        String stack;
        while (!signStack.isEmpty()) {
            stack = signStack.pop();
            if (!stack.equals("(")) {
                expressionStack.push(stack);
            }
        }
    }

    private static boolean isMathOperator(char c) {
        return c == Constants.ADD.charAt(0)
                || c == Constants.SUB.charAt(0)
                || c == Constants.MUL.charAt(0)
                || c == Constants.DIV.charAt(0);
    }


}
