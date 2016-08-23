package com.ejarosiewicz.validators.input.rules;

import com.ejarosiewicz.MathOperator;
import com.ejarosiewicz.exceptions.resources.ResourcesShortageException;

/**
 * @author Emil Jarosiewicz on 23.08.16.
 */
public interface MathOperatorRule {
    public void validateMathOperator(MathOperator mathOperator) throws ResourcesShortageException;
}
