package com.upc.trabajofinal2.exception;

public class PacienteExistenteException extends RuntimeException {

    public PacienteExistenteException(String message) {
        super(message);
    }
}