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
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

public class EcoleModalController {
    private Stage modal;
    @FXML
    private TextField nomEcole;
    @FXML
    private TextField libeleAdresse;
    @FXML
    private SearchableComboBox<Ville> ville;
    @FXML
    private SearchableComboBox<Departement> nomDepartement;
    private SearchableEcole searchableEcole;
    private Filter filter;


    @FXML
    private void initialize(){
        filter = new Filter();

        searchableEcole = new SearchableEcole();

        nomDepartement.setItems(FXCollections.observableArrayList(filter.getDepartements()));
        nomDepartement.getSelectionModel().selectedItemProperty().addListener(observable -> filterDepartement());
        nomDepartement.getSelectionModel().select(0);

        ville.getSelectionModel().selectedItemProperty().addListener(observable -> filter());
        ville.setItems(FXCollections.observableArrayList(filter.getVilles()));
        ville.getSelectionModel().select(0);
    }
    @FXML
    private void addEcole(){
        Ecole ecole = new Ecole(0, nomEcole.getText(),
                    new Adresse(0,libeleAdresse.getText(),
                            new Ville(ville.getSelectionModel().getSelectedItem().getId_ville(), ville.getSelectionModel().getSelectedItem().getVille(),0F, 0F,
                                    new Departement(nomDepartement.getSelectionModel().getSelectedItem().getId_departement(), nomDepartement.getSelectionModel().getSelectedItem().getNumero_departement() , nomDepartement.getSelectionModel().getSelectedItem().toString()))));

        if(!ecole.getNom().equals("") && !ecole.getAdresse().getAdresse().equals("") && ville.getSelectionModel().getSelectedItem().getId_ville() != 0 && nomDepartement.getSelectionModel().getSelectedItem().getId_departement() != 0){
            int id =  DAOFactory.getAdresseDAO().insert(ecole.getAdresse());
            ecole.getAdresse().setId_adresse(id);
            DAOFactory.getEcoleDAO().insert(ecole);
            closeModal();
        }
        else{
            Alert alertErrorInsert = new Alert(Alert.AlertType.ERROR);
            alertErrorInsert.setTitle("Erreur");
            alertErrorInsert.setHeaderText("Erreur! Mauvaise(s) donnée(s)");
            alertErrorInsert.showAndWait().ifPresent(btnTypeError -> {
                if (btnTypeError == ButtonType.OK) {
                    alertErrorInsert.close();
                }
            });
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
