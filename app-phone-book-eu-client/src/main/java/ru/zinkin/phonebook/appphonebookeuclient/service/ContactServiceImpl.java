package ru.zinkin.phonebook.appphonebookeuclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.zinkin.phonebook.appphonebookeuclient.dao.ContactDao;
import ru.zinkin.phonebook.appphonebookeuclient.model.entities.Contact;

@Service
public class ContactServiceImpl implements ContactService{


    private final ContactDao contactDao;

    @Autowired
    public ContactServiceImpl(ContactDao contactDao) {
        this.contactDao = contactDao;
//        contactDao.save(new Contact("Дмитрий","Зинкин","89585485393"));
    }

    @Override
    public Boolean exist(String phone) {
        return contactDao.existsContactByPhone(phone);
    }

    @Override
    public Contact findByPhone(String phone) throws RuntimeException {
        return contactDao.findByPhone(phone).orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
    }

    @Override
    public Boolean save(Contact contact) {
        Contact contact1 = contactDao.save(contact);
        return contact1.getId() != null;
    }

    @Override
    public Boolean delete(Contact contact) {
        contactDao.delete(contact);
        return !exist(contact.getPhone());
    }

    @Override
    public Boolean updateUserSpam(String contact) {
        if(exist(contact)){
            Contact c = findByPhone(contact);
            c.setSpam(c.getSpam() + 1);
            contactDao.save(c);
            return true;
        }
        return false;
    }
}
