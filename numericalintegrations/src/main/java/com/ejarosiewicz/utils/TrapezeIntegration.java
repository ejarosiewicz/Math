package com.ejarosiewicz.utils;

/**
 * Created by 3mill on 2016-03-09.
 */
public class TrapezeIntegration extends Integration {

    @Override
    public float integrationMethod(float a, float b, float step) {
        float funA = functionCalculator.calculate(a);
        float funB = functionCalculator.calculate(b);

        return ((funA + funB) / 2) * step;
    }

    @Override
    public float calculate() {
        float score = 0;
        float dx = (max - min) / count;

        for (int i = 1; i <= count; i++) {
            score += functionCalculator.calculate(min + i * dx);
        }

        score += (functionCalculator.calculate(min) + functionCalculator.calculate(max)) / 2;

        return score * dx;
    }
}
