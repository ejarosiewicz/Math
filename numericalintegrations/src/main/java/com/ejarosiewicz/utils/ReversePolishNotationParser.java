package com.ejarosiewicz.utils;

import java.util.Collections;
import java.util.Stack;

/**
 * Created by Emil Jarosiewicz on 2016-03-08.
 */
public class ReversePolishNotationParser {

    private static Stack<String> signStack;
    private static Stack<String> expressionStack;
    private static String numberBuffer;
    private static boolean highPriorityOperator;
    private static boolean lastOpen;

    private enum ConditionValues {
        ADD_TO_NUMBER_BUFFER,
        MATH_OPERATOR_OPS,
        CHAR_LEFT_BRACE,
        CHAR_RIGHT_BRACE
    }

    private ReversePolishNotationParser() {
    }

    public static Stack<String> parseToExpressionStack(String expression) {
        initValues();

        for (int i = 0, length = expression.length(); i < length; i++) {
            char singleChar = expression.charAt(i);

            for (ConditionValues conditionValue : ConditionValues.values()) {
                if (characterCondition(conditionValue, singleChar)) {
                    makeCharacterAction(conditionValue, singleChar);
                    break;
                }
            }
        }

        removeAll(expressionStack);
        Collections.reverse(expressionStack);

        return expressionStack;
    }

    private static void initValues() {
        signStack = new Stack<>();
        expressionStack = new Stack<>();
        highPriorityOperator = false;
        lastOpen = false;
        numberBuffer = "";
    }

    private static void makeCharacterAction(ConditionValues value, char singleChar) {
        switch (value) {
            case ADD_TO_NUMBER_BUFFER:
                numberBuffer += singleChar;
                break;

            case MATH_OPERATOR_OPS:
                pushOps(expressionStack);
                signStack.push("" + singleChar);

                if (singleChar == '*' || singleChar == '/') {
                    highPriorityOperator = true;
                }

                break;

            case CHAR_LEFT_BRACE:
                signStack.push("" + singleChar);
                break;

            case CHAR_RIGHT_BRACE:
                pushOps(expressionStack);
                removeToOpen(expressionStack);
                break;
        }
    }

    private static boolean characterCondition(ConditionValues value, char singleChar) {
        switch (value) {
            case ADD_TO_NUMBER_BUFFER:
                return (lastOpen && singleChar == '-')
                        || (Character.isDigit(singleChar)
                        || singleChar == Constants.FUNC_X.charAt(0));

            case MATH_OPERATOR_OPS:
                return isMathOperator(singleChar);

            case CHAR_LEFT_BRACE:
                return lastOpen = singleChar == '(';

            case CHAR_RIGHT_BRACE:
                return lastOpen = singleChar == ')';

            default:
                return false;
        }
    }

    private static void pushNumber(Stack<String> expressionStack) {
        if (numberBuffer.length() > 0) {
            expressionStack.push(numberBuffer);
            numberBuffer = "";
        }
    }

    private static void pushOps(Stack<String> expressionStack) {
        pushNumber(expressionStack);

        if (highPriorityOperator) {
            expressionStack.push(signStack.pop());
            highPriorityOperator = false;
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
        return c == Constants.ADD
                || c == Constants.SUB
                || c == Constants.MUL
                || c == Constants.DIV;
    }
}
