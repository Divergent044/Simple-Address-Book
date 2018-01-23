package sample.interfaces.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.interfaces.AddressBook;
import sample.objects.Person;

import java.util.ArrayList;

// класс реализовывает интерфейс с помощью коллекции
public class CollectionAddressBook implements AddressBook{

    private ObservableList<Person> personList = FXCollections.observableArrayList();

    @Override
    public void add(Person person) {
        personList.add(person);
    }

    @Override
    // для коллекции не используется, но пригодится для случая, когда данные хранятся в БД и пр.
    public void edit(Person person) {
        // т.к. коллекция и является хранилищем - то ничего обновлять не нужно
        // если бы данные хранились в БД или файле - в этом методе нужно было бы обновить соотв. запись
    }

    @Override
    public void delete(Person person) {
        personList.remove(person);
    }

    public ObservableList<Person> getPersonList() {
        return personList;
    }

    /*public void print() {
        int number = 0;
        for (Person person : personList) {
            number++;
            System.out.println(number + ". ФИО: " + person.getFio() + " Телефон: " + person.getTelephone());
        }
    }*/

    public void fillTestData() {
        personList.add(new Person("Zakharov P.V.", "+7(985)777-60-49"));
        personList.add(new Person("Zakharov P.V.", "+7(985)777-60-49"));
        personList.add(new Person("Zakharov P.V.", "+7(985)777-60-49"));
        personList.add(new Person("Zakharov P.V.", "+7(985)777-60-49"));
        personList.add(new Person("Zakharov P.V.", "+7(985)777-60-49"));
        personList.add(new Person("Zakharov P.V.", "+7(985)777-60-49"));
        personList.add(new Person("Zakharov P.V.", "+7(985)777-60-49"));
    }
}
