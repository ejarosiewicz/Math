package com.ejarosiewicz.validators.input;

import com.ejarosiewicz.MathOperator;
import com.ejarosiewicz.exceptions.resources.ResourcesShortageException;
import com.ejarosiewicz.validators.input.rules.CountNotNullRule;
import com.ejarosiewicz.validators.input.rules.FunctionCalculatorNotNullRule;
import com.ejarosiewicz.validators.input.rules.MathOperatorRule;
import com.ejarosiewicz.validators.input.rules.MaxNotNullRule;
import com.ejarosiewicz.validators.input.rules.MinNotNullRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil Jarosiewicz on 23.08.16.
 */
public class InputValidator {

    public static void validateInput(MathOperator mathOperator) throws ResourcesShortageException {
        List<MathOperatorRule> mathOperatorRuleList = new ArrayList<>();
        mathOperatorRuleList.add(new MinNotNullRule());
        mathOperatorRuleList.add(new MaxNotNullRule());
        mathOperatorRuleList.add(new CountNotNullRule());
        mathOperatorRuleList.add(new FunctionCalculatorNotNullRule());

        for (MathOperatorRule mathOperatorRule : mathOperatorRuleList) {
            mathOperatorRule.validateMathOperator(mathOperator);
        }
    }

}
