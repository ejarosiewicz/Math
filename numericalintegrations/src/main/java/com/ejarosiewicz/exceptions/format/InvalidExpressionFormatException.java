package com.ejarosiewicz.exceptions.format;

/**
 * Created by emil on 19.08.16.
 */
public abstract class InvalidExpressionFormatException extends Exception {

    public InvalidExpressionFormatException(String message) {
        super(message);
    }
}
