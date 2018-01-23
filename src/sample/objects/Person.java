package sample.objects;

import javafx.beans.property.SimpleStringProperty;

public class Person {

    private SimpleStringProperty fio = new SimpleStringProperty("");
    private SimpleStringProperty telephone = new SimpleStringProperty("");

    public Person() {

    }

    public Person(String fio, String telephone) {
        this.fio = new SimpleStringProperty(fio);
        this.telephone = new SimpleStringProperty(telephone);
    }

    public String getFio() {
        return fio.get();
    }

    public void setFio(String fio){
        this.fio.set(fio);
    }

    public String getTelephone() {
        return telephone.get();
    }

    public void setTelephone(String telephone){
        this.telephone.set(telephone);
    }

    public SimpleStringProperty fioProperty() {
        return fio;
    }

    public SimpleStringProperty telephoneProperty() {
        return telephone;
    }

    @Override
    public String toString() {
        return "Person(" + "fio='" + fio + '\'' + ", phone='" + telephone + '\'' + ')';
    }
}
