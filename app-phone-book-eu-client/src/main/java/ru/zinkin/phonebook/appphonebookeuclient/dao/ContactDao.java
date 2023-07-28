package ru.zinkin.phonebook.appphonebookeuclient.dao;

import jakarta.persistence.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zinkin.phonebook.appphonebookeuclient.model.entities.Contact;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactDao extends JpaRepository<Contact,Long> {

    Optional<Contact> findByPhone(String phone);
    Boolean existsContactByPhone(String phone);

}
