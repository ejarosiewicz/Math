package ej.com.math;

import org.junit.Test;

import ej.com.math.math.utils.FunctionCalculator;
import ej.com.math.math.utils.ReversePolishNotationParser;
import ej.com.math.math.utils.SquareIntegration;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SquareIntegrationTest {

    public static final int[] FUNC_DATA = {-10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public static final String SQUARE_FUNCTION = "(x*x)";

    @Test
    public void squareIntegrationCaseSquare() throws Exception {
        float score = squareIntegrationProcess(SQUARE_FUNCTION, -2,5,1);
        assertEquals(score, 175f, 0);
    }

    public float squareIntegrationProcess(String expression, float min, float max, float count)
            throws Exception {

        SquareIntegration squareIntegration = new SquareIntegration();
        squareIntegration.setFunctionCalculator(new FunctionCalculator(
                ReversePolishNotationParser.parseToExpressionStack(expression)
        ));
        squareIntegration.setMin(min);
        squareIntegration.setMax(max);
        squareIntegration.setCount(count);
        return squareIntegration.calculate();
    }


}