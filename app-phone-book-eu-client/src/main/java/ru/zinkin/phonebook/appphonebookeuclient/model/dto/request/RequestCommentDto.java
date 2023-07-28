package ru.zinkin.phonebook.appphonebookeuclient.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestCommentDto {

    private String phone;
    private String comment;

}
