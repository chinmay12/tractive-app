package com.code.pettracking.pets.exceptions;

public class RecordAlreadyExists extends RuntimeException {

    public RecordAlreadyExists(String message){
        super(message);
    }
}
