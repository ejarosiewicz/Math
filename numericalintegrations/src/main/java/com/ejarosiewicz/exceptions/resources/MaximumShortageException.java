package com.ejarosiewicz.exceptions.resources;

/**
 * @author Emil Jarosiewicz on 19.08.16.
 */
public class MaximumShortageException extends ResourcesShortageException {

    private static final String NO_MAXIMUM_MESSAGE = "maximum is not defined";

    public MaximumShortageException() {
        super(NO_MAXIMUM_MESSAGE);
    }
}
