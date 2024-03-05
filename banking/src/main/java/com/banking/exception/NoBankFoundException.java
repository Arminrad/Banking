package com.banking.exception;

public class NoBankFoundException extends RuntimeException {

    public NoBankFoundException() { super("No bank Found"); }
}
