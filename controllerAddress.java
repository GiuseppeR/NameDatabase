package com.giuseppe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class controllerAddress
{
    @FXML private TextField nameText;
    @FXML private TextField surnameText;
    @FXML private TextField ageText;
    @FXML private TableView<Client> clientsTable;
    @FXML private TableColumn<Client, Integer> idColumn;
    @FXML private TableColumn<Client, String> nameColumn;
    @FXML private TableColumn<Client, String> surnameColumn;
    @FXML private TableColumn<Client, Integer> ageColumn;


    public void initialize()
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        new Thread(() ->
        {
            clientsTable.getItems().setAll(Datasource.getDatasource().getClientsList());
        }).start();

    }

    @FXML
    public void handleAddButton()
    {
        String letterRegex = "[a-zA-Z]+";
        String numberRegex = "\\d+";

        if(nameText.getText().isEmpty() || surnameText.getText().isEmpty() || ageText.getText().isEmpty())
        {
            showMessage(AlertType.ERROR, "All the fields must be fulfilled.", "Error");
        }
        else if(!nameText.getText().matches(letterRegex))
        {
            showMessage(AlertType.ERROR, "The name must contains only letters.", "Error");
        }
        else if(!surnameText.getText().matches(letterRegex))
        {
            showMessage(AlertType.ERROR, "The surname must contains only letters.", "Error");
        }
        else if(!ageText.getText().matches(numberRegex))
        {
            showMessage(AlertType.ERROR, "The age must contains only numbers.", "Error");
        }
        else
        {
            Datasource.getDatasource().addContact(
                    nameText.getText(),
                    surnameText.getText(),
                    Integer.parseInt(ageText.getText()));

            new Thread(() ->
            {
                clientsTable.getItems().setAll(Datasource.getDatasource().getClientsList());
            }).start();
            nameText.clear();
            surnameText.clear();
            ageText.clear();

            nameText.requestFocus();
        }
    }

    @FXML
    public void handleDeleteButton()
    {
        if(!clientsTable.getSelectionModel().isEmpty())
        {
            Client client = clientsTable.getSelectionModel().getSelectedItem();
            new Thread(() ->
            {
                Datasource.getDatasource().deleteContacts(client.getId());
                Platform.runLater(() ->
                {
                    clientsTable.getItems().setAll(Datasource.getDatasource().getClientsList());
                });
            }).start();
            nameText.clear();
            surnameText.clear();
            ageText.clear();
        }
        else
        {
            showMessage(AlertType.ERROR, "You must select an item.", "Error");
        }
    }

    @FXML
    public void handleItemSelection()
    {
        Client client = clientsTable.getSelectionModel().getSelectedItem();
        nameText.setText(client.getName());
        surnameText.setText(client.getSurname());
        ageText.setText(String.valueOf(client.getAge()));
    }

    @FXML
    public void handleClearButton()
    {
        nameText.clear();
        surnameText.clear();
        ageText.clear();
        clientsTable.getSelectionModel().clearSelection();
    }

    @FXML
    public void handleNameText()
    {
        surnameText.requestFocus();
    }

    @FXML
    public void handleSurnameText()
    {
        ageText.requestFocus();
    }

    private void showMessage(AlertType type, String message, String title)
    {
        Alert alert = new Alert(type, message);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
