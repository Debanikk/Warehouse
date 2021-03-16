package com.debanik.warehouse.service.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * InvalidProductException is thrown when the product is not found in the database.
 */
public class InvalidProductException extends Exception {
    private final String code;
    private final String errorMessage;

    public InvalidProductException(final String code, final String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    public String getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}

