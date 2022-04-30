package com.lifelike.dev.springmongodbtodo.exception;

public class TodoCollectionException extends Exception {

    public TodoCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "Todo with ID " + id + " was not found.";
    }

    public static String AlreadyExists() {
        return "Todo item already exists.";
    }
}
