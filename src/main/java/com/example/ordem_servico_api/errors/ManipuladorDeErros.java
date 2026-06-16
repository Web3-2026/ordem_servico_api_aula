package com.example.ordem_servico_api.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.example.ordem_servico_api.exceptions.ResourceNotFoundException;


@RestControllerAdvice
public class ManipuladorDeErros {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespostaErro> manipuladorExcecoes(Exception ex, WebRequest request){

        Integer status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String erro = "Erro no servidor";
        String mensagem = "Ocorreu um erro inesperado.";

        if (ex instanceof ResourceNotFoundException) {
        
            status = HttpStatus.NOT_FOUND.value();
            erro = "Recurso não encontrado";
            mensagem = ex.getMessage();
        
        } else if (ex instanceof MethodArgumentNotValidException) {

            status = HttpStatus.BAD_REQUEST.value();
            erro = "Recurso de validação";
            mensagem = ex.getMessage();
        
        }

        RespostaErro resposta = new RespostaErro(
            status, 
            erro, 
            mensagem,
            request.getDescription(false)
        );


        if (ex instanceof IllegalArgumentException) {

            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) ex;

            for (FieldError fieldError : validException.getBindingResult().getFieldErrors()) {

                String campo = fieldError.getField();
                String mens  = fieldError.getDefaultMessage();

                resposta.addErro(campo, mensagem);
            }
            
        }

        return ResponseEntity.status(HttpStatus.OK).body(resposta);

    }


}
