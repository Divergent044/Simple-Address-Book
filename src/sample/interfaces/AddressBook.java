package sample.interfaces;

import sample.objects.Person;

public interface AddressBook {

    // добавить запись
    void add(Person person);

    // редактировать запись
    void edit(Person person);

    // удалить запись
    void delete(Person person);
}
