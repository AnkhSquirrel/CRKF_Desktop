package fr.kyo.crkf.Controller;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.Personne;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EcoleAroundProfesseurController {

    @FXML
    private Label ville;
    private Personne personne;

    private ApplicationCRKF applicationCRKF;

    @FXML
    private void initialize(){

    }

    public void setApplicationCRKF(ApplicationCRKF applicationCRKF){
        this.applicationCRKF = applicationCRKF;
    }

    public void setPersonne(Personne personne){
        this.personne = personne;
        ville.setText(personne.getAdresse().getVille().getVille());
    }

}
