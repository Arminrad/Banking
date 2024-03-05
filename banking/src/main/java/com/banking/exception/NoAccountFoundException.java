package com.banking.exception;

public class NoAccountFoundException extends RuntimeException {

    public NoAccountFoundException() { super("No Account Found"); }
}
