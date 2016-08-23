package com.ejarosiewicz.exceptions.resources;

/**
 * @author Emil Jarosiewicz on 19.08.16.
 */
public class CountShortageException extends ResourcesShortageException {

    private static final String NO_COUNT_MESSAGE = "count is not defined";

    public CountShortageException() {
        super(NO_COUNT_MESSAGE);
    }
}
