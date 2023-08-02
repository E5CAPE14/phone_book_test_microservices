package ru.zinkin.phonebook.appphonebookeuclient.model.dto.response;

public class ResponseString {

    private String message;


    public ResponseString(String message) {
        this.message = message;
    }

    public ResponseString() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
