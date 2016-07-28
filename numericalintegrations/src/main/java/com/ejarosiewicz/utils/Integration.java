package com.ejarosiewicz.utils;

/**
 * Created by 3mill on 2016-03-09.
 */
public abstract class Integration {

    protected float min;
    protected float max;

    protected float count;

    protected FunctionCalculator functionCalculator;

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public FunctionCalculator getFunctionCalculator() {
        return functionCalculator;
    }

    public void setFunctionCalculator(FunctionCalculator functionCalculator) {
        this.functionCalculator = functionCalculator;
    }


    public abstract float integrationMethod(float a, float b, float step);

    public abstract float calculate();
}
