package com.ejarosiewicz;

import com.ejarosiewicz.exceptions.format.InvalidExpressionFormatException;
import com.ejarosiewicz.exceptions.resources.CountShortageException;
import com.ejarosiewicz.exceptions.resources.FunctionCalculatorShortageException;
import com.ejarosiewicz.exceptions.resources.MaximumShortageException;
import com.ejarosiewicz.exceptions.resources.MinimumShortageException;
import com.ejarosiewicz.exceptions.resources.ResourcesShortageException;
import com.ejarosiewicz.utils.ExpressionUtils;
import com.ejarosiewicz.utils.FunctionCalculator;
import com.ejarosiewicz.utils.Integration;
import com.ejarosiewicz.utils.SquareIntegration;
import com.ejarosiewicz.utils.TrapezeIntegration;

/**
 * @author Emil Jarosiewicz on 2016-03-07.
 */
public class MathOperator {

    private FunctionCalculator functionCalculator;
    private Integration integration;

    private Float min;
    private Float max;
    private Float count;

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

    public void setExpression(String expression) throws InvalidExpressionFormatException {
        functionCalculator = ExpressionUtils.generateFunctionCalculatorFromExpression(expression);
    }

    public float calculateSquareMethod() throws ResourcesShortageException {
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

    public float calculateTrapezeMethod() throws ResourcesShortageException {
        checkInput();
        integration = new TrapezeIntegration();

        return integrationCalculation();
    }

    private void checkInput() throws ResourcesShortageException {
        if (min == null) {
            throw new MinimumShortageException();
        } else if (max == null) {
            throw new MaximumShortageException();
        } else if (count == null) {
            throw new CountShortageException();
        } else if (functionCalculator == null) {
            throw new FunctionCalculatorShortageException();
        }
    }
}