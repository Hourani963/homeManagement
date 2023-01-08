package com.ahmad.homeManagement.fileStorage;

public class CantDeleteFileException extends Exception{
    public CantDeleteFileException(String s) {
        System.err.println(s);
    }
}
