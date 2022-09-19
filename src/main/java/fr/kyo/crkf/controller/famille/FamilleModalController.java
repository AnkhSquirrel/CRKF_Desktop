package fr.kyo.crkf.controller.famille;

import fr.kyo.crkf.Entity.Classification;
import fr.kyo.crkf.Entity.Famille;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FamilleModalController {
    @FXML
    private Label nomModal;
    @FXML
    private TextField nom;
    @FXML
    private ComboBox<Classification> classification;
    private Stage modal;
    private boolean create;
    private GestionFamilleController gestionFamilleController;
    private Famille famille;


    @FXML
    private void initialize(){
        Filter filter = new Filter();

        classification.setItems(FXCollections.observableArrayList(filter.getClassifications()));
        classification.getSelectionModel().select(0);
    }

    @FXML
    private void validate(){
        if(create)
            createFamille();
        else
            updateFamille();
    }

    private void updateFamille() {
        if(!nom.getText().isEmpty())
            famille.setfamille(nom.getText());
        if(classification.getSelectionModel().getSelectedItem() != null && classification.getSelectionModel().getSelectedItem().getId_classification() != 0)
            famille.setclassification(classification.getSelectionModel().getSelectedItem());
        if(DAOFactory.getFamilleDAO().update(famille)){
            gestionFamilleController.filter();
            modal.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une erreur lors de la modification de la famille.\n Merci de vérifier que vous avez entrée des informations valides");
            alert.showAndWait();
        }

    }

    private void createFamille(){
        if(!nom.getText().isEmpty() && classification.getSelectionModel().getSelectedItem() != null && classification.getSelectionModel().getSelectedItem().getId_classification() != 0){
            Famille famille = new Famille(0,nom.getText(),classification.getSelectionModel().getSelectedItem().getId_classification());
            if(DAOFactory.getFamilleDAO().insert(famille) != 0){
                gestionFamilleController.filter();
                modal.close();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une erreur lors de la création de la famille.\n Merci de vérifier que vous avez entrée des informations valides");
            alert.showAndWait();
        }

    }
    @FXML
    private void closeModal(){
        modal.close();
    }
    public void setModal(Stage stage){
        modal = stage;
    }

    public void setCreate(boolean bool) {
        create = bool;
        if(create){
            nomModal.setText("Création d'une nouvelle famille");
        }else{
            nomModal.setText("Modification d'une nouvelle famille");
        }
    }

    public void setGestionFamilleController(GestionFamilleController gestionFamilleController) {
        this.gestionFamilleController = gestionFamilleController;
    }

    public void setFamille(Famille famille) {
        this.famille = famille;
        nom.setText(famille.getfamille());
        classification.getSelectionModel().select(famille.getclassification());
    }
}
