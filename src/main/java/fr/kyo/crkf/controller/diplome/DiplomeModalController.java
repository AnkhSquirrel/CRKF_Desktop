package fr.kyo.crkf.controller.diplome;

import fr.kyo.crkf.entity.Cycle;
import fr.kyo.crkf.entity.Instrument;
import fr.kyo.crkf.entity.Personne;
import fr.kyo.crkf.searchable.Filter;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class DiplomeModalController {

    @FXML
    private ComboBox<Cycle> cycle;
    @FXML
    private ComboBox<Instrument> instrument;
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
    void closeModal() {
        modal.close();
    }

    @FXML
    void validate() {
        if (create)
            addDiplome();
    }

    private void addDiplome() {
        if(cycle.getSelectionModel().getSelectedItem() != null && cycle.getSelectionModel().getSelectedItem().getCycleId() != 0 && instrument.getSelectionModel().getSelectedItem() != null && instrument.getSelectionModel().getSelectedItem().getInstrumentId() != 0){
            for(int i = 1; i <= cycle.getSelectionModel().getSelectedItem().getCycleNumero(); i++)
                DAOFactory.getDiplomeDAO().personneAddDiplome(personne.getPersonneId(), instrument.getSelectionModel().getSelectedItem().getInstrumentId(), DAOFactory.getCycleDAO().getByCycle(i).getCycleId());
            gestionDiplomeController.filter();
            modal.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une erreur lors de l'ajout du diplome.\n Merci de vérifier que vous avez entré des informations valides");
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
