package ru.zinkin.phonebook.appphonebookeuclient.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestContactDto {

    private String name;
    private String surname;
    private String phone;

}
