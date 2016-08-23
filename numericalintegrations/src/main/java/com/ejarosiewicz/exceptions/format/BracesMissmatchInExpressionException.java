package com.ejarosiewicz.exceptions.format;

/**
 * Created by emil on 19.08.16.
 */
public class BracesMissmatchInExpressionException extends InvalidExpressionFormatException {

    static final String BRACES_MISMATCH = "Braces closure mismatch";

    public BracesMissmatchInExpressionException() {
        super(BRACES_MISMATCH);
    }
}
