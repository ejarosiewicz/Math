package ej.com.math;

import com.ejarosiewicz.utils.ReversePolishNotationParser;

import org.junit.Test;

import java.util.Stack;


import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ReversePolishNotationTest {

    public static final String CASE_ONE_NORMAL_NOTATION =  "((-6-8)*4)";
    public static final String CASE_ONE_REVERSE_POLISH_NOTATION =  "-6 8 - 4 * ";

    public static final String CASE_TWO_NORMAL_NOTATION =  "((2+3)*(5+2))";
    public static final String CASE_TWO_REVERSE_POLISH_NOTATION =  "2 3 + 5 2 + * ";

    public static final String CASE_THREE_NORMAL_NOTATION =  "((2+x)*(x+2))";
    public static final String CASE_THREE_REVERSE_POLISH_NOTATION =  "2 x + x 2 + * ";


    @Test
    public void reversePolishNotationCaseOne() throws Exception {
        reversePolishNotationComparator(CASE_TWO_NORMAL_NOTATION,CASE_TWO_REVERSE_POLISH_NOTATION);
    }

    @Test
    public void reversePolishNotationCaseTwo() throws Exception {
        reversePolishNotationComparator(CASE_ONE_NORMAL_NOTATION,CASE_ONE_REVERSE_POLISH_NOTATION);
    }

    @Test
    public void reversePolishNotationCaseThree() throws Exception {
        reversePolishNotationComparator(CASE_THREE_NORMAL_NOTATION,CASE_THREE_REVERSE_POLISH_NOTATION);
    }


    public void reversePolishNotationComparator(String expression, String expectedExpression)
            throws Exception {
        Stack<String> expressionStack = ReversePolishNotationParser.parseToExpressionStack(expression);
        String computedExpression = expressionStackToString(expressionStack);
        System.out.print(computedExpression+"\n");
        assertEquals(computedExpression, expectedExpression);
    }

    private String expressionStackToString(Stack<String> expressionStack) {
        String expression = "";
        while (!expressionStack.isEmpty()){
            expression += expressionStack.pop() + " ";
        }

        return expression;
    }


}