package ej.com.math;

import org.junit.Test;

import ej.com.math.math.utils.FunctionCalculator;
import ej.com.math.math.utils.ReversePolishNotationParser;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MathBuilderTest {


    public static final String FUNCTION = "3x+2";
    public static final String NORMALIZED_FUNCTION = "(3*x+2)";
    public static final String INPROPER_FUNCTION_ONE = "3xsad+2xsaDW3QFSC";



    @Test
    public void normalizationTest() throws Exception {
        MathBuilder mathBuilder = new MathBuilder();
        String normalizedFunction = mathBuilder.expressionCorrection(FUNCTION);
        assertEquals(normalizedFunction,NORMALIZED_FUNCTION);
    }


    @Test
    public void patternTest() throws Exception {
        MathBuilder mathBuilder = new MathBuilder();
        assertEquals(mathBuilder.matchExpressionPattern(FUNCTION),true);
        assertEquals(mathBuilder.matchExpressionPattern(INPROPER_FUNCTION_ONE),false);
    }

}