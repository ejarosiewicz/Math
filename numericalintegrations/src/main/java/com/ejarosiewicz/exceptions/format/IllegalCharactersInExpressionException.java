package com.ejarosiewicz.exceptions.format;

/**
 * Created by emil on 19.08.16.
 */
public class IllegalCharactersInExpressionException extends InvalidExpressionFormatException {

    private static final String ILLEGAL_CHARACTERS = "Illegal characters. Should have only"
            + " digits, signs, braces, and x character";

    public IllegalCharactersInExpressionException() {
        super(ILLEGAL_CHARACTERS);
    }
}
