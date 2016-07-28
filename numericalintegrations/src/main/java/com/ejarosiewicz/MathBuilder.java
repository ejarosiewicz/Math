package com.ejarosiewicz;

import com.ejarosiewicz.utils.FunctionCalculator;
import com.ejarosiewicz.utils.Integration;
import com.ejarosiewicz.utils.ReversePolishNotationParser;
import com.ejarosiewicz.utils.SquareIntegration;
import com.ejarosiewicz.utils.TrapezeIntegration;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Emil Jarosiewicz on 2016-03-07.
 */
public class MathBuilder {

    public static final String EQUATION_PATTERN = "(?:[0-9-+*/()x])+";

    private FunctionCalculator functionCalculator;
    private Integration integration;

    protected Float min;
    protected Float max;

    protected Float count;

    public Float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public Float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public Float getCount() {
        return count;
    }

    public FunctionCalculator getExpression() {
        return functionCalculator;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public void setExpression(String expression) throws InvalidFormatException {

        if (!matchExpressionPattern(expression)) {
            throw new InvalidFormatException(InvalidFormatException.ILLEGAL_CHARACTERS);
        }
        if (!matchBraces(expression)) {
            throw new InvalidFormatException(InvalidFormatException.BRACES_MISMATCH);
        }
        functionCalculator = new FunctionCalculator(
                ReversePolishNotationParser.parseToExpressionStack(
                        expressionCorrection(expression)
                )
        );
    }

    private boolean matchBraces(String expression) {
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

    public String expressionCorrection(String expression) {
        StringBuilder stringBuilder = new StringBuilder(expression);
        int i = 0;
        while (i < stringBuilder.length()) {

            if (
                    i > 0
                            && stringBuilder.charAt(i) != ')'
                            && noNumber(stringBuilder.charAt(i), stringBuilder.charAt(i - 1))
                            && noOperation(stringBuilder.charAt(i))
                            && noOperation(stringBuilder.charAt(i - 1)
                    )) {
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

    private boolean noNumber(char c, char c1) {
        return !(Character.isDigit(c) && Character.isDigit(c1));
    }

    private boolean noOperationsBefore(char c, char c1) {
        return c1 != '+'
                && c1 != '-'
                && c1 != '*'
                && c1 != '/';
    }

    private boolean noOperation(char c1) {
        return c1 != '+'
                && c1 != '-'
                && c1 != '*'
                && c1 != '/';
    }

    public boolean matchExpressionPattern(String expression) {
        Pattern pattern = Pattern.compile(EQUATION_PATTERN);
        Matcher matcher = pattern.matcher(expression);
        return matcher.matches();

    }

    public float calculateSquareMethod() throws NoResourcesException {
        checkInput();
        integration = new SquareIntegration();
        return integrationCalculation();

    }

    private float integrationCalculation() {
        integration.setMin(min);
        integration.setMax(max);
        integration.setCount(count);
        integration.setFunctionCalculator(this.functionCalculator);
        return integration.calculate();
    }

    public float calculateTrapezeMethod() throws NoResourcesException {
        checkInput();
        integration = new TrapezeIntegration();
        return integrationCalculation();

    }


    public void checkInput() throws NoResourcesException {
        if (min == null) {
            throw new NoResourcesException(NoResourcesException.NO_MINIMUM_MESSAGE);
        } else if (max == null) {
            throw new NoResourcesException(NoResourcesException.NO_MAXIMUM_MESSAGE);
        } else if (count == null) {
            throw new NoResourcesException(NoResourcesException.NO_COUNT_MESSAGE);
        } else if (functionCalculator == null) {
            throw new NoResourcesException(NoResourcesException.NO_FUNCTION_MESSAGE);
        }
    }

    public class NoResourcesException extends Exception {
        public static final String NO_MINIMUM_MESSAGE = "minimum is not defined";
        public static final String NO_MAXIMUM_MESSAGE = "maximum is not defined";
        public static final String NO_COUNT_MESSAGE = "count is not defined";
        public static final String NO_FUNCTION_MESSAGE = "function is not defined";

        public NoResourcesException(String errorType) {
            super(errorType);
        }
    }

    public class InvalidFormatException extends Exception {
        public static final String ILLEGAL_CHARACTERS = "Illegal characters. Should have only" +
                " digits, signs, braces, and x character";
        public static final String BRACES_MISMATCH = "Braces closure mismatch";

        public InvalidFormatException(String errorType) {
            super(errorType);
        }
    }
}
