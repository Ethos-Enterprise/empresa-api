package com.ethos.empresaapi.exception;

public class EmpresaNaoExisteException extends RuntimeException {
    public EmpresaNaoExisteException(String message) {
        super(message);
    }
}
