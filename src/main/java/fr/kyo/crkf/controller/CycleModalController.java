package fr.kyo.crkf.controller;

import fr.kyo.crkf.Entity.Cycle;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CycleModalController {
    @FXML
    private Label nomModal;
    @FXML
    private TextField nom;
    @FXML
    private TextField cycleTextField;
    private Stage modal;
    private boolean create;
    private GestionCycleController gestionCycleController;
    private Cycle cycle;

    @FXML
    private void initialize(){
        cycleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cycleTextField.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }

    @FXML
    private void validate(){
        if(create)
            createCycle();
        else
            updateCycle();
    }
    private void updateCycle() {
        if(!nom.getText().isEmpty()){
            cycle.setLibelle(nom.getText());
            cycle.setCycle(Integer.parseInt(cycleTextField.getText()));
        }
        if(DAOFactory.getCycleDAO().update(cycle)){
            gestionCycleController.filter();
            modal.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une erreur lors de la modification du cycle.\n Merci de vérifier que vous avez entré des informations valides");
            alert.showAndWait();
        }

    }
    private void createCycle(){
        if(!nom.getText().isEmpty() && !cycleTextField.getText().isEmpty()){
            cycle = new Cycle(0,nom.getText(), Integer.parseInt(cycleTextField.getText()));
            if(DAOFactory.getCycleDAO().insert(cycle) != 0){
                gestionCycleController.filter();
                modal.close();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une erreur lors de la création du cycle.\n Merci de vérifier que vous ayez entré des informations valides");
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
            nomModal.setText("Création d'un cycle");
        }else{
            nomModal.setText("Modification d'un cycle");
        }
    }
    public void setGestionCycleController(GestionCycleController gestionCycleController) {
        this.gestionCycleController = gestionCycleController;
    }
    public void setCycle(Cycle cycle) {
            this.cycle = cycle;
            nom.setText(cycle.getLibelle());
            cycleTextField.setText(String.valueOf(cycle.getCycle()));
            cycleTextField.setDisable(true);
    }
}
