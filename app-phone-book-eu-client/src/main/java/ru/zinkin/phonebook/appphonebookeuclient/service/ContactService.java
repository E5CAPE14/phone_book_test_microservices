package ru.zinkin.phonebook.appphonebookeuclient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zinkin.phonebook.appphonebookeuclient.dao.ContactDao;
import ru.zinkin.phonebook.appphonebookeuclient.model.entities.Contact;

import java.util.List;

@Service
public interface ContactService {
    Boolean exist(String phone);
    Contact findByPhone(String phone) throws RuntimeException;
    Boolean save(Contact contact);
    Boolean delete(Contact contact);

    Boolean addComment(String number, String comment);

    List<Contact> getAll();

    Boolean updateUserSpam(String contact);

}
