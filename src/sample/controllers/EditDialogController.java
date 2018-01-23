package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DialogManager;
import sample.objects.Person;

public class EditDialogController {

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField fioField;

    @FXML
    private TextField telField;

    @FXML
    private Label fioLabel;

    @FXML
    private Label telLabel;

    private Person person;

    public void setPerson(Person person) {
        if (person == null)
            return;
        this.person = person;
        fioField.setText(person.getFio());
        telField.setText(person.getTelephone());
    }

    public Person getPerson() {
        return person;
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent actionEvent) {
        if (!checkValues())
            return;
        person.setTelephone(telField.getText());
        person.setFio(fioField.getText());
        actionClose(actionEvent);
    }

    private boolean checkValues() {
        if (fioField.getText().trim().length() == 0 || telField.getText().trim().length() == 0) {
            DialogManager.showErrorDialog("Error", "Fill Field(s)");
            return false;
        }
        return true;
    }
}
