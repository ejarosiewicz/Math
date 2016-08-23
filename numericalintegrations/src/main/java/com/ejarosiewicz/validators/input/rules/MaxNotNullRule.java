package com.ejarosiewicz.validators.input.rules;

import com.ejarosiewicz.MathOperator;
import com.ejarosiewicz.exceptions.resources.MaximumShortageException;
import com.ejarosiewicz.exceptions.resources.MinimumShortageException;
import com.ejarosiewicz.exceptions.resources.ResourcesShortageException;

/**
 * @author Emil Jarosiewicz on 23.08.16.
 */
public class MaxNotNullRule implements MathOperatorRule {
    @Override
    public void validateMathOperator(MathOperator mathOperator) throws ResourcesShortageException {
        if (null == mathOperator.getMax()) {
            throw new MaximumShortageException();
        }
    }
}
