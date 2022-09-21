package fr.kyo.crkf.controller.departement;

import fr.kyo.crkf.entity.Departement;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class DepartementModalController {
    @FXML
    private Label nomModal;
    @FXML
    private TextField nomDep;
    @FXML
    private TextField numDepartement;
    private Stage modal;
    private boolean create;
    private GestionDepartementController gestionDepartementController;
    private Departement departement;


    @FXML
    private void validate(){
        if(create)
            createDepartement();
        else
            updateDepartement();
    }
    private void updateDepartement() {
        if(!nomDep.getText().isEmpty())
            departement.setDepartementLibelle(nomDep.getText());
        if(!numDepartement.getText().isEmpty() && !DAOFactory.getDepartementDAO().getNumDepartement().contains(numDepartement.getText()))
            departement.setDepartementNumero(numDepartement.getText());
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le numéro de département est déjà attribué");
            alert.showAndWait();
        }
        if(DAOFactory.getDepartementDAO().update(departement)){
            gestionDepartementController.filter();
            modal.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une erreur lors de la modification du département.\n Merci de vérifier que vous avez entré des informations valides");
            alert.showAndWait();
        }

    }
    private void createDepartement(){
        if(!nomDep.getText().isEmpty() && !numDepartement.getText().isEmpty() && !DAOFactory.getDepartementDAO().getNumDepartement().contains(numDepartement.getText())){
            departement = new Departement(0,numDepartement.getText(),nomDep.getText());
            if(DAOFactory.getDepartementDAO().insert(departement) != 0){
                gestionDepartementController.filter();
                modal.close();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une erreur lors de la création du département.\n Merci de vérifier que vous avez entré des informations valides");
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
            nomModal.setText("Créer un département");
        }else{
            nomModal.setText("Modifier un département");
        }
    }
    public void setGestionDepartementController(GestionDepartementController gestionDepartementController) {
        this.gestionDepartementController = gestionDepartementController;
    }
    public void setDepartement(Departement departement) {
        this.departement = departement;
        nomDep.setText(departement.getDepartementLibelle());
        numDepartement.setText(departement.getDepartementNumero());
    }
}
