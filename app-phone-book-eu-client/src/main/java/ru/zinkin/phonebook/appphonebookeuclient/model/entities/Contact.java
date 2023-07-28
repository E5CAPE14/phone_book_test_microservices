package ru.zinkin.phonebook.appphonebookeuclient.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false,length = 50)
    private String name;
    @Column(name = "surname",nullable = false,length = 50)
    private String surname;
    @Column(name = "phone",nullable = false,unique = true,length = 15)
    private String phone;
    @ElementCollection
    @OrderBy
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<String> comments;
    @Column(name = "spam",nullable = false)
    private Integer spam;

    public Contact(String name, String surname, String phone) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.comments = new ArrayList<>();
        this.spam = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return id.equals(contact.id) && name.equals(contact.name) && surname.equals(contact.surname) && phone.equals(contact.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, phone);
    }
}
