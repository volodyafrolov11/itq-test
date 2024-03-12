package com.itqtest.order.exception;

public class NonexistentOrderException extends Exception {
    public NonexistentOrderException(String message) {
        super(message);
    }
}
