package ru.zinkin.phonebook.appphonebookeuclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.zinkin.phonebook.appphonebookeuclient.dao.ContactDao;
import ru.zinkin.phonebook.appphonebookeuclient.model.entities.Contact;

import java.util.List;

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
    public Boolean addComment(String number, String comment){
        if(exist(number)){
            Contact c = findByPhone(number);
            if(c.getComments().stream().anyMatch(x -> x.equals(comment))){
                return false;
            }
            c.getComments().add(comment);
            contactDao.save(c);
            return true;
        }
        return false;
    }
    @Override
    public List<Contact> getAll(){
        return contactDao.findAll(Sort.by("name").ascending());
    }
    @Override
    public Boolean updateUserSpam(String contact) {
        if(exist(contact)){
            Contact c = findByPhone(contact);
            int spamVote = c.getSpam() + 1;
            c.setSpam(spamVote);
            contactDao.save(c);
            return true;
        }
        return false;
    }
}
