package ru.zinkin.phonebook.appphonebookeuclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zinkin.phonebook.appphonebookeuclient.model.dto.ContactDto;
import ru.zinkin.phonebook.appphonebookeuclient.model.entities.Contact;
import ru.zinkin.phonebook.appphonebookeuclient.service.ContactService;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/find/{phone}")
    public ResponseEntity<?> findByPhone(@PathVariable("phone") String phone){
        if(contactService.exist(phone)){
            Contact contact = contactService.findByPhone(phone);

            ContactDto contactDto = ContactDto.builder()
                    .name(contact.getName())
                    .surname(contact.getSurname())
                    .phone(contact.getPhone())
                    .comments(contact.getComments().toArray(String[]::new))
                    .spam(false)
                    .build();
            if(contact.getSpam()>10){
                contactDto.setSpam(true);
            }
            return ResponseEntity.ok(contactDto);
        }
        return ResponseEntity.badRequest().body("Контакта с таким номером не существует");
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfo(){
        return ResponseEntity.ok("Микро-сервис телефонной книги.");
    }

    @PutMapping("/add/spam")
    public ResponseEntity<?> incrementSpam(@RequestBody String phone){
        if(contactService.updateUserSpam(phone)){
            return ResponseEntity.ok("Проголосовано!");
        }
        return ResponseEntity.badRequest().body("Пользователь не найден!");
    }

}
