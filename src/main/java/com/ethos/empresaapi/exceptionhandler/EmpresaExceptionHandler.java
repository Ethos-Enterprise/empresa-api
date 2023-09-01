package com.ethos.empresaapi.exceptionhandler;

import com.ethos.empresaapi.exception.EmpresaJaExisteException;
import com.ethos.empresaapi.exception.EmpresaNaoExisteException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmpresaExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        if(exception.getMessage().contains("Cnpj inválido")){
            problemDetail.setDetail("O cnpj informado é inválido");
        }
        if(exception.getMessage().contains("branco")){
            switch (exception.getFieldError().getField()) {
                case "razaoSocial" -> problemDetail.setDetail("O campo razão social não pode ser branco");
                case "cnpj" -> problemDetail.setDetail("O campo CNPJ não pode ser branco");
                case "telefone" -> problemDetail.setDetail("O campo telefone não pode ser branco");
                case "email" -> problemDetail.setDetail("O campo email não pode ser branco");
                case "senha" -> problemDetail.setDetail("O campo senha não pode ser branco");
            }
        } else if (exception.getMessage().contains("nulo") || exception.getMessage().contains("nula")) {
            switch (exception.getMessage()) {
                case "razaoSocial" -> problemDetail.setDetail("O campo razão social não pode ser nulo");
                case "cnpj" -> problemDetail.setDetail("O campo CNPJ não pode ser nulo");
                case "telefone" -> problemDetail.setDetail("O campo telefone não pode ser nulo");
                case "email" -> problemDetail.setDetail("O campo email não pode ser nulo");
                case "senha" -> problemDetail.setDetail("O campo senha não pode ser nulo");
            }
        }
        problemDetail.setTitle("Corpo da requisição inválida");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        if (exception.getMessage().contains("cnpj")) {
            problemDetail.setDetail("O campo CNPJ não pode ser nulo");
        }
        problemDetail.setDetail("Corpo da requisição inválida, algum campo está nulo");
        problemDetail.setTitle("Requisição inválida");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }
    @ExceptionHandler(EmpresaNaoExisteException.class)
    public ProblemDetail empresaNaoExisteException(EmpresaNaoExisteException exception) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
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
