package fr.kyo.crkf.controller;

import fr.kyo.crkf.Entity.Cycle;
import fr.kyo.crkf.Entity.Instrument;
import fr.kyo.crkf.Entity.Personne;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class DiplomeModalController {

    @FXML
    private Button buttonAnnuler;

    @FXML
    private ComboBox<Cycle> cycle;

    @FXML
    private ComboBox<Instrument> instrument;

    @FXML
    private Label nomModal;
    private Stage modal;
    private Personne personne;
    private GestionDiplomeController gestionDiplomeController;
    private boolean create;

    @FXML
    private void initialize(){
        Filter filter = new Filter();

        cycle.setItems(FXCollections.observableArrayList(filter.getCycles()));
        instrument.setItems(FXCollections.observableArrayList(filter.getInstrument()));
    }

    @FXML
    void closeModal(ActionEvent event) {
        modal.close();
    }

    @FXML
    void validate(ActionEvent event) {
        if (create)
            addDiplome();
    }

    private void addDiplome() {
        if(cycle.getSelectionModel().getSelectedItem() != null && cycle.getSelectionModel().getSelectedItem().getId_cycle() != 0 && instrument.getSelectionModel().getSelectedItem() != null && instrument.getSelectionModel().getSelectedItem().getId_instrument() != 0){
            for(int i = 1; i <= cycle.getSelectionModel().getSelectedItem().getCycle(); i++)
                DAOFactory.getDiplomeDAO().personneAddDiplome(personne.getId_personne(), instrument.getSelectionModel().getSelectedItem().getId_instrument(), DAOFactory.getCycleDAO().getByCycle(i).getId_cycle());
            gestionDiplomeController.filter();
            modal.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une erreur lors de l'ajout du diplome.\n Merci de vérifier que vous avez entrée des informations valides");
            alert.showAndWait();
        }
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public void setGestionDiplomeController(GestionDiplomeController gestionDiplomeController) {
        this.gestionDiplomeController = gestionDiplomeController;
    }

    public void setCreate(boolean bool) {
        create = bool;
    }
}
