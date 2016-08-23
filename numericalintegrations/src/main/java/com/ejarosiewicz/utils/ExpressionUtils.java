package com.ejarosiewicz.utils;

import com.ejarosiewicz.MathOperator;
import com.ejarosiewicz.exceptions.format.BracesMissmatchInExpressionException;
import com.ejarosiewicz.exceptions.format.IllegalCharactersInExpressionException;
import com.ejarosiewicz.exceptions.format.InvalidExpressionFormatException;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Emil Jarosiewicz on 19.08.16.
 */
public class ExpressionUtils {

    private static final String EQUATION_PATTERN = "(?:[0-9-+*/()x])+";

    public static FunctionCalculator generateFunctionCalculatorFromExpression(String expression)
            throws InvalidExpressionFormatException {
        if (!matchExpressionPattern(expression)) {
            throw new IllegalCharactersInExpressionException();
        }

        if (!matchBraces(expression)) {
            throw new BracesMissmatchInExpressionException();
        }

        return new FunctionCalculator(
                ReversePolishNotationParser.parseToExpressionStack(
                        expressionCorrection(expression)
                )
        );
    }

    private static boolean matchBraces(String expression) {
        Stack<Character> bracesStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char character = expression.charAt(i);

            if (character == '(') {
                bracesStack.push(character);
            } else if (character == ')') {
                if (bracesStack.isEmpty()) {
                    return false;
                } else {
                    bracesStack.pop();
                }
            }
        }

        return bracesStack.isEmpty();
    }

    private static boolean matchExpressionPattern(String expression) {
        Pattern pattern = Pattern.compile(EQUATION_PATTERN);
        Matcher matcher = pattern.matcher(expression);

        return matcher.matches();
    }

    public static String expressionCorrection(String expression) {
        StringBuilder stringBuilder = new StringBuilder(expression);
        int i = 0;
        while (i < stringBuilder.length()) {

            if (i > 0 && stringBuilder.charAt(i) != ')'
                    && noNumber(stringBuilder.charAt(i), stringBuilder.charAt(i - 1))
                    && noOperation(stringBuilder.charAt(i))
                    && noOperation(stringBuilder.charAt(i - 1))) {
                stringBuilder.insert(i, '*');
            }

            i++;
        }

        if (expression.charAt(0) != '(') {
            stringBuilder.insert(0, '(');
            stringBuilder.append(')');
        }

        return stringBuilder.toString();
    }

    private static boolean noNumber(char c, char c1) {
        return !(Character.isDigit(c) && Character.isDigit(c1));
    }

    private static boolean noOperation(char c1) {
        return c1 != '+'
                && c1 != '-'
                && c1 != '*'
                && c1 != '/';
    }
}
