package com.ejarosiewicz.exceptions.resources;

/**
 * Created by emil on 19.08.16.
 */
public abstract class ResourcesShortageException extends Exception {

    ResourcesShortageException(String errorType) {
        super(errorType);
    }
}