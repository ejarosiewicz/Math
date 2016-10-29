package com.ejarosiewicz.utils;

/**
 * Created by Emil Jarosiewicz on 2016-03-09.
 */
public class SquareIntegration extends Integration {

    @Override
    public float integrationMethod(float a, float b, float step) {
        float mid = (a + b) / 2;
        float height = functionCalculator.calculate(mid);

        return Math.abs(step * height);
    }

    @Override
    public float calculate() {
        float score = 0;
        float dx = (max - min) / count;

        for (int i = 1; i <= count; i++) {
            score += functionCalculator.calculate(min + i * dx);
        }

        return score * dx;
    }
}
