package com.ejarosiewicz.exceptions.resources;

/**
 * @author Emil Jarosiewicz on 19.08.16.
 */
public class FunctionCalculatorShortageException extends ResourcesShortageException {

    private static final String NO_FUNCTION_MESSAGE = "function is not defined";

    public FunctionCalculatorShortageException() {
        super(NO_FUNCTION_MESSAGE);
    }
}
