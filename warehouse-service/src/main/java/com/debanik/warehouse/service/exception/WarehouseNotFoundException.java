package com.debanik.warehouse.service.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * WarehouseNotFoundException is thrown when the warehouse entry is not found in the database.
 */
public class WarehouseNotFoundException extends Exception {
    private final String code;
    private final String errorMessage;

    public WarehouseNotFoundException(final String code, final String errorMessage) {
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

