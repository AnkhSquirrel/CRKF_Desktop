package fr.kyo.crkf.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateEcoleModalController {
    private Stage modal;
    @FXML
    private Button ajouter;
    @FXML
    private TextField nom;



    public void setModal(Stage modal) {
        this.modal = modal;
    }
    @FXML
    private void closeModal(){
        modal.close();
    }

}
