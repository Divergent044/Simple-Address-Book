package sample.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import sample.DialogManager;
import sample.interfaces.impls.CollectionAddressBook;
import sample.objects.Person;

import java.io.IOException;
import java.lang.reflect.Method;

public class MainController {

    private CollectionAddressBook addressBookImpl = new CollectionAddressBook();

    private Stage mainStage;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button delButton;

    @FXML
    private Button searchButton;

    @FXML
    private CustomTextField searchField;

    @FXML
    private TableView table;

    @FXML
    private TableColumn<Person, String> fioCol;

    @FXML
    private TableColumn<Person, String> telCol;

    @FXML
    private Label label;

    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditDialogController editDialogController;
    private Stage editDialogStage;
    private ObservableList<Person> backupList;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    private void initialize() {
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fioCol.setCellValueFactory(new PropertyValueFactory<Person, String>("fio"));
        telCol.setCellValueFactory(new PropertyValueFactory<Person, String>("telephone"));
        setupClearButtonField(searchField);

        initListeners();

        fillData();

        initLoader();
    }

    private void fillData() {
        addressBookImpl.fillTestData();
        backupList = FXCollections.observableArrayList();
        backupList.addAll(addressBookImpl.getPersonList());

        table.setItems(addressBookImpl.getPersonList());
    }

    private void initListeners() {

        addressBookImpl.getPersonList().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                updateCountLabel();
            }
        });

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    editDialogController.setPerson((Person)table.getSelectionModel().getSelectedItem());
                    showDialog();
                }
            }
        });
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("../fxml/editingWindow.fxml"));
            fxmlEdit = fxmlLoader.load();
            editDialogController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupClearButtonField(CustomTextField customTextField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCountLabel() {
        label.setText("Количество записей: " + addressBookImpl.getPersonList().size());
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        // если нажата не кнопка - выхдим из метода
        if (!(source instanceof Button))
            return;

        Button clickedButton = (Button) source;

        Person selectedPerson = (Person) table.getSelectionModel().getSelectedItem();

        switch (clickedButton.getId()) {
            case "addButton":
                editDialogController.setPerson(new Person());
                showDialog();
                addressBookImpl.add(editDialogController.getPerson());
                break;

            case "editButton":
                if (!personInSelected(selectedPerson))
                    return;
                editDialogController.setPerson((Person)table.getSelectionModel().getSelectedItem());
                showDialog();
                break;

            case "delButton":
                if (!personInSelected(selectedPerson))
                    return;
                addressBookImpl.delete((Person)table.getSelectionModel().getSelectedItem());
                break;
        }
    }

    private boolean personInSelected(Person selectedPerson) {
        if (selectedPerson == null) {
            DialogManager.showInfoDialog("Error", "Select Person!");
            return false;
        }
        return true;
    }

    private void showDialog(){

        if (editDialogStage == null) {
            editDialogStage = new Stage();
            editDialogStage.setTitle("Редактирование записи");
            editDialogStage.setMinWidth(300);
            editDialogStage.setMinHeight(150);
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(mainStage);
        }

        editDialogStage.showAndWait();
    }

    public void actionSearch(ActionEvent actionEvent) {
        addressBookImpl.getPersonList().clear();
        for (Person person : backupList) {
            if (person.getFio().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                    person.getTelephone().toLowerCase().contains(searchField.getText().toLowerCase()))
                addressBookImpl.getPersonList().add(person);
        }
    }
}
