package fr.kyo.crkf.controller;

import fr.kyo.crkf.Entity.Adresse;
import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ecole;
import fr.kyo.crkf.Entity.Ville;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateEcoleModalController {
    private Stage modal;
    @FXML
    private Button ajouter;
    @FXML
    private TextField nomEcole;
    @FXML
    private TextField libeleAdresse;
    @FXML
    private ComboBox<Ville> villeComboBox;
    @FXML
    private ComboBox<Departement> departementComboBox;
    @FXML
    private ComboBox<Departement> numDepartement;


    @FXML
    private void addEcole(){
        Ecole ecole = new Ecole(0, nomEcole.getText(),
                new Adresse(0,libeleAdresse.getText(),
                new Ville(0, villeComboBox.getSelectionModel().getSelectedItem().toString(),0F, 0F,
                new Departement(0, numDepartement.getSelectionModel().getSelectedItem().toString() , departementComboBox.getSelectionModel().getSelectedItem().toString()))));
    }
    public void setModal(Stage modal) {
        this.modal = modal;
    }
    @FXML
    private void closeModal(){
        modal.close();
    }

}
