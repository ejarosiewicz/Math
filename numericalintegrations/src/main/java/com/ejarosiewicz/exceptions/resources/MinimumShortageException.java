package com.ejarosiewicz.exceptions.resources;

/**
 * Created by emil on 19.08.16.
 */

public class MinimumShortageException extends ResourcesShortageException {

    private static final String NO_MINIMUM_MESSAGE = "minimum is not defined";

    public MinimumShortageException() {
        super(NO_MINIMUM_MESSAGE);
    }
}
