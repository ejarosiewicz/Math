package ej.com.math;

import com.ejarosiewicz.utils.FunctionCalculator;
import com.ejarosiewicz.utils.ReversePolishNotationParser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class FunctionCalculatorTest {

    public static final int[] FUNC_DATA = {-10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public static final String SQUARE_FUNCTION = "(x*x)";
    public static final String TYPE_SQUARE = "TYPE_SQUARE";

    public static final String FUNCTION_ONE = "((2*x)+3)";
    public static final String TYPE_FUNCTION_ONE = "TYPE_FUNCTION_ONE";


    @Test
    public void functionCalculatorCaseSquare() throws Exception {
        functionCalculatorComparator(SQUARE_FUNCTION, TYPE_SQUARE);
    }

    @Test
    public void functionCalculatorCaseOne() throws Exception {
        System.out.print("" + TYPE_FUNCTION_ONE + "\n");
        functionCalculatorComparator(FUNCTION_ONE, TYPE_FUNCTION_ONE);
    }


    public void functionCalculatorComparator(String expression, String type)
            throws Exception {

        FunctionCalculator functionCalculator = new FunctionCalculator(
                ReversePolishNotationParser.parseToExpressionStack(expression)
        );
        for (int x : FUNC_DATA) {
            Float calculatedNotation = functionCalculator.calculate(x);
            Float calculatedTest = calculateFunction(x, type);
            System.out.print("" + x + "  " + calculatedNotation + " " + calculatedTest + "\n");
            assertEquals(calculatedNotation, calculatedTest);
        }
    }

    private Float calculateFunction(int x, String type) {
        Float equation = 0.0f;
        switch (type){
            case TYPE_SQUARE:
                equation = (float)x*x;
                break;
            case TYPE_FUNCTION_ONE:
                equation = (float)(2*x)+3;
                break;
        }
                return equation;

    }


}