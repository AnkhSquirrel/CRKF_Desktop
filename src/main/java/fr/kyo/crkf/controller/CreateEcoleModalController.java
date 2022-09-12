package fr.kyo.crkf.controller;

import fr.kyo.crkf.Entity.Adresse;
import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ecole;
import fr.kyo.crkf.Entity.Ville;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.Searchable.SearchableEcole;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private Label numDepartement;
    private SearchableEcole searchableEcole;
    private Filter filter;


    @FXML
    private void initialize(){
        filter = new Filter();


        searchableEcole = new SearchableEcole();


        nomDepartement.setItems(FXCollections.observableArrayList(filter.getDepartements()));
        nomDepartement.getSelectionModel().selectedItemProperty().addListener(observable -> filterDepartement());


        ville.getSelectionModel().selectedItemProperty().addListener(observable -> filter());
    }
    @FXML
    private void addEcole(){
        Ecole ecole = new Ecole(0, nomEcole.getText(),
                new Adresse(0,libeleAdresse.getText(),
                new Ville(ville.getSelectionModel().getSelectedItem().getId_ville(), ville.getSelectionModel().getSelectedItem().getVille(),0F, 0F,
                new Departement(nomDepartement.getSelectionModel().getSelectedItem().getId_departement(), nomDepartement.getSelectionModel().getSelectedItem().getNumero_departement() , nomDepartement.getSelectionModel().getSelectedItem().toString()))));

        if(!ecole.getNom().equals("") && !ecole.getAdresse().getAdresse().equals("")){
            int id =  DAOFactory.getAdresseDAO().insert(ecole.getAdresse());
            ecole.getAdresse().setId_adresse(id);
            DAOFactory.getEcoleDAO().insert(ecole);
        }
    }
    public void setModal(Stage modal) {
        this.modal = modal;
    }
    @FXML
    private void closeModal(){
        modal.close();
    }

    private void filterDepartement() {
        ville.setItems(FXCollections.observableArrayList(filter.getVilleLike("", searchableEcole.getDepartement().getId_departement())));
        ville.getSelectionModel().select(0);
        filter();
    }

    @FXML
    private void filter(){
        if(nomDepartement.getSelectionModel().getSelectedItem() != null){
            searchableEcole.setDepartement(nomDepartement.getSelectionModel().getSelectedItem());
        }
    }


}
