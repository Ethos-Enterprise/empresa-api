package com.ethos.empresaapi.exception;

public class EmpresaJaExisteException extends RuntimeException{
    public EmpresaJaExisteException(String message) {
        super(message);
    }
}
