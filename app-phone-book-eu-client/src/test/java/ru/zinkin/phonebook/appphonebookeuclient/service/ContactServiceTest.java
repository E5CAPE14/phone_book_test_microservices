package ru.zinkin.phonebook.appphonebookeuclient.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import ru.zinkin.phonebook.appphonebookeuclient.dao.ContactDao;
import ru.zinkin.phonebook.appphonebookeuclient.model.entities.Contact;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {
    private List<Contact> contacts;
    @Mock
    private ContactDao contactDao;
    @InjectMocks
    private ContactServiceImpl contactService;
    public ContactServiceTest() {
        contacts = getContact();
    }
    /*
        Boolean save(Contact contact);
        Boolean delete(Contact contact);
        Boolean addComment(String number, String comment);
        Boolean updateUserSpam(String contact);
    */
    @Test
    public void findAllTest(){
        Mockito.when(contactDao.findAll(Sort.by("name")))
                .thenReturn(contacts.stream().sorted(new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return compare(o1.getName().getBytes(StandardCharsets.UTF_8),o2.getName().getBytes(StandardCharsets.UTF_8));
            }
            /*
                Сравнение лексикографически.
             */
            private int compare(byte[] left, byte[] right) {
                for (int i = 0, j = 0; i < left.length && j < right.length; i++, j++) {
                    int a = (left[i] & 0xff);
                    int b = (right[j] & 0xff);
                    if (a != b) {
                        return a - b;
                    }
                }
                return left.length - right.length;
            }
        })
                        .collect(Collectors.toList()));
        List<Contact> list = contactService.getAll();
        Assertions.assertEquals(16,list.size());
        Assertions.assertEquals(contacts.get(3).getName(),list.get(0).getName());
        Assertions.assertEquals(contacts.get(3).getPhone(),list.get(0).getPhone());
        Assertions.assertEquals(contacts.get(3).getId(),list.get(0).getId());
        Assertions.assertEquals(contacts.get(3).getSurname(),list.get(0).getSurname());
        Assertions.assertEquals(contacts.get(3).getComments().isEmpty(),list.get(0).getComments().isEmpty());

        Assertions.assertEquals(contacts.get(5).getName(),list.get(1).getName());
        Assertions.assertEquals(contacts.get(5).getPhone(),list.get(1).getPhone());
        Assertions.assertEquals(contacts.get(5).getId(),list.get(1).getId());
        Assertions.assertEquals(contacts.get(5).getSurname(),list.get(1).getSurname());
        Assertions.assertEquals(contacts.get(5).getComments().isEmpty(),list.get(1).getComments().isEmpty());
        Assertions.assertEquals(contacts.get(5).getComments().get(0),list.get(1).getComments().get(0));

        Assertions.assertEquals(contacts.get(15).getName(),list.get(12).getName());
        Assertions.assertEquals(contacts.get(15).getPhone(),list.get(12).getPhone());
        Assertions.assertEquals(contacts.get(15).getId(),list.get(12).getId());
        Assertions.assertEquals(contacts.get(15).getSurname(),list.get(12).getSurname());
        Assertions.assertEquals(contacts.get(15).getComments().isEmpty(),list.get(12).getComments().isEmpty());
        Assertions.assertEquals(contacts.get(15).getComments().get(0),list.get(12).getComments().get(0));
        Assertions.assertEquals(contacts.get(15).getComments().get(1),list.get(12).getComments().get(1));

        Assertions.assertEquals(contacts.get(14).getName(),list.get(15).getName());
        Assertions.assertEquals(contacts.get(14).getPhone(),list.get(15).getPhone());
        Assertions.assertEquals(contacts.get(14).getId(),list.get(15).getId());
        Assertions.assertEquals(contacts.get(14).getSurname(),list.get(15).getSurname());
        Assertions.assertEquals(contacts.get(14).getComments().isEmpty(),list.get(15).getComments().isEmpty());


    }

    @Test
    public void existTest(){
        existTest("89585485393");
        existTest("89285485393");
        existTest("89306959928");
        existTest("89306959927");
    }
    @Test
    public void findByPhoneTest(){
        findByPhone("89585485393");
        findByPhone("89285485393");
        findByPhone("89306959928");
        findByPhone("89306959927");
    }
    private List<Contact> getContact(){
        List<Contact> contacts = new ArrayList<>();
        long i = 1L;
        contacts.add(new Contact(i,"Dmitry","Zinkin","89585485393",List.of("Классный парень!"),5));
        contacts.add(new Contact(++i,"Olga","Zinkina","89625111070",new ArrayList<>(),0));
        contacts.add(new Contact(++i,"Alexey","Zinkin","89648361806",new ArrayList<>(),0));
        contacts.add(new Contact(++i,"AAA","Rodinovich","89653452144",new ArrayList<>(),12));
        contacts.add(new Contact(++i,"Alina","Makaricheva","89306959929",new ArrayList<>(),0));
        contacts.add(new Contact(++i,"AAB","Zinkin1","89585485394",List.of(new String("Классный парень!")),5));
        contacts.add(new Contact(++i,"Olga","Zinkina1","89625111071",List.of("Хорошая женщина","Отличный начальник"),0));
        contacts.add(new Contact(++i,"Evkakiy1","Rodinovich","89653452144",new ArrayList<>(),9));
        contacts.add(new Contact(++i,"Alina2","Makaricheva1","89306959928",new ArrayList<>(),0));
        contacts.add(new Contact(++i,"Evkakiy3","Rodinovich","89653452143",new ArrayList<>(),19));
        contacts.add(new Contact(++i,"Alina0","Makaricheva","89306959927",new ArrayList<>(),0));
        contacts.add(new Contact(++i,"Дмитрий","Родионович","89633452444",new ArrayList<>(),2));
        contacts.add(new Contact(++i,"Алина","Makaricheva","89316959929",new ArrayList<>(),0));
        contacts.add(new Contact(++i,"Alina4","Makaricheva","89306959927",new ArrayList<>(),0));
        contacts.add(new Contact(++i,"Дмитрий1","Родионович","89639452444",new ArrayList<>(),2));
        contacts.add(new Contact(++i,"ZZZ","Makaricheva0","89313959929",List.of("Хорошая жена","Вежливый человек","Хорошая ученица"),0));

        return contacts;
    }
    private void existTest(String phone){
        Mockito.when(contactDao.existsContactByPhone(phone))
                .thenReturn(contacts.stream().anyMatch(x -> x.getPhone().equals(phone)));
        Assertions
                .assertEquals(contacts.stream().anyMatch(x -> x.getPhone().equals(phone)),contactService.exist(phone));
    }

    private void findByPhone(String phone){
        Mockito.when(contactDao.findByPhone(phone))
                .thenReturn(contacts.stream().filter(x -> x.getPhone().equals(phone)).findFirst());
        try {
            Assertions
                    .assertEquals(contacts.stream().filter(x -> x.getPhone().equals(phone)).findFirst(), Optional.of(contactService.findByPhone(phone)));
        } catch (RuntimeException e){

        }
    }
}
