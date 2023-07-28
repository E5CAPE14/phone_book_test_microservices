package ru.zinkin.phonebook.appphonebookeuclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zinkin.phonebook.appphonebookeuclient.model.dto.request.RequestCommentDto;
import ru.zinkin.phonebook.appphonebookeuclient.model.dto.request.RequestContactDto;
import ru.zinkin.phonebook.appphonebookeuclient.model.dto.response.ResponseContactDto;
import ru.zinkin.phonebook.appphonebookeuclient.model.entities.Contact;
import ru.zinkin.phonebook.appphonebookeuclient.service.ContactService;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService contactService;
    private final Integer max_value_spam;

    @Autowired
    public ContactController(ContactService contactService,@Value(value = "${spam.value}") Integer max_value_spam) {
        this.contactService = contactService;
        this.max_value_spam = max_value_spam;
    }

    @GetMapping("/find/{phone}")
    public ResponseEntity<?> findByPhone(@PathVariable("phone") String phone){
        if(contactService.exist(phone)){
            Contact contact = contactService.findByPhone(phone);

            ResponseContactDto responseContactDto = ResponseContactDto.builder()
                    .name(contact.getName())
                    .surname(contact.getSurname())
                    .phone(contact.getPhone())
                    .comments(contact.getComments().toArray(String[]::new))
                    .spam(false)
                    .build();
            if(contact.getSpam()>max_value_spam){
                responseContactDto.setSpam(true);
            }
            return ResponseEntity.ok(responseContactDto);
        }
        return ResponseEntity.badRequest().body("Контакта с таким номером не существует");
    }
    @GetMapping("/find/all")
    public ResponseEntity<List<ResponseContactDto>> findAll(){
        List<ResponseContactDto> response = contactService.getAll().stream().map(x -> {
            ResponseContactDto responseContactDto = ResponseContactDto.builder()
                    .name(x.getName())
                    .surname(x.getSurname())
                    .phone(x.getPhone())
                    .comments(x.getComments().toArray(String[]::new))
                    .spam(false)
                    .build();
            if (x.getSpam() > max_value_spam) {
                responseContactDto.setSpam(true);
            }
            return responseContactDto;
        }).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<String> getInfo(){
        return ResponseEntity.ok("Микро-сервис телефонной книги.");
    }

    @PutMapping("/add/spam")
    public ResponseEntity<String> incrementSpam(@RequestBody String phone){
        if(contactService.updateUserSpam(phone)){
            return ResponseEntity.ok("Проголосовано!");
        }
        return ResponseEntity.badRequest().body("Пользователь не найден!");
    }

    @PutMapping("/add/comment")
    public ResponseEntity<String> addComment(@RequestBody RequestCommentDto commentDto){
        StringBuilder stringBuilder = new StringBuilder();
        if(!contactService.exist(commentDto.getPhone())){
            stringBuilder.append("Пользователь не найден!");
        } else {
            if (contactService.addComment(commentDto.getPhone(), commentDto.getComment())) {
                stringBuilder.append(String.format("Комментарий к номеру %s с содержанием %s добавлен", commentDto.getPhone(), commentDto.getComment()));
            } else {
                stringBuilder.append("Комментарий уже существует");
            }
        }
        return ResponseEntity.badRequest().body(stringBuilder.toString());
    }

    @PostMapping("/add/contact")
    public ResponseEntity<?> saveContact(@RequestBody RequestContactDto requestContactDto){
        if(contactService.exist(requestContactDto.getPhone())){
            return ResponseEntity.badRequest().body("Такой номер в БД уже существует!");
        }
        contactService.save(new Contact(
                requestContactDto.getName(),
                requestContactDto.getSurname(),
                requestContactDto.getPhone()
        ));
        return ResponseEntity.ok(String.format("Пользователь c номером телефона %s сохранен!",requestContactDto.getPhone()));
    }
}
