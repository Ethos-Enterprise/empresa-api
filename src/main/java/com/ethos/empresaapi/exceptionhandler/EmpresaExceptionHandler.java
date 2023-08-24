package com.ethos.empresaapi.exceptionhandler;

import com.ethos.empresaapi.exception.EmpresaJaExisteException;
import com.ethos.empresaapi.exception.EmpresaNaoExisteException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmpresaExceptionHandler {
    @ExceptionHandler(EmpresaNaoExisteException.class)
    public ProblemDetail empresaNaoExisteException(EmpresaNaoExisteException exception) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setTitle("Empresa não encontrada");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }
    @ExceptionHandler(EmpresaJaExisteException.class)
    public ProblemDetail empresaJaExisteException(EmpresaJaExisteException exception) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setTitle("Empresa já cadastrada");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }
}
