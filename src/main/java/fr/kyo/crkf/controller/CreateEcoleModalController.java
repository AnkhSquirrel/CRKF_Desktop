package fr.kyo.crkf.controller;

import fr.kyo.crkf.Entity.Adresse;
import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ecole;
import fr.kyo.crkf.Entity.Ville;
import fr.kyo.crkf.Searchable.Filter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

public class CreateEcoleModalController {
    private Stage modal;
    @FXML
    private Button ajouter;
    @FXML
    private TextField nomEcole;
    @FXML
    private TextField libeleAdresse;
    @FXML
    private SearchableComboBox<Ville> ville;
    @FXML
    private SearchableComboBox<Departement> nomDepartement;
    @FXML
    private SearchableComboBox<Departement> numDepartement;
    private Filter filter;


    @FXML
    private void initialize(){
        filter = new Filter();
        nomDepartement.setItems(FXCollections.observableArrayList(filter.getDepartements()));
        ville.setItems(FXCollections.observableArrayList(filter.getVilles()));
    }
    @FXML
    private void addEcole(){
        Ecole ecole = new Ecole(0, nomEcole.getText(),
                new Adresse(0,libeleAdresse.getText(),
                new Ville(0, ville.getSelectionModel().getSelectedItem().getVille(),0F, 0F,
                new Departement(0, numDepartement.getSelectionModel().getSelectedItem().toString() , nomDepartement.getSelectionModel().getSelectedItem().toString()))));
    }
    public void setModal(Stage modal) {
        this.modal = modal;
    }
    @FXML
    private void closeModal(){
        modal.close();
    }

}
