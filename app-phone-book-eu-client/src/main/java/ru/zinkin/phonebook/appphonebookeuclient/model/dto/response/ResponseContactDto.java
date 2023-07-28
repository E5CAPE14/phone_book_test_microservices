package ru.zinkin.phonebook.appphonebookeuclient.model.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonSerialize
public class ResponseContactDto {

    @JsonView
    private String name;
    @JsonView
    private String surname;
    @JsonView
    private String phone;
    @JsonView
    private String[] comments;
    @JsonView
    private Boolean spam;
}
