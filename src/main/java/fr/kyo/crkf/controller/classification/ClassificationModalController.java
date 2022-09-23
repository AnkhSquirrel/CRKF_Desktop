package fr.kyo.crkf.controller.classification;

import fr.kyo.crkf.entity.Classification;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ClassificationModalController {

    @FXML
    private Label nomModal;
    @FXML
    private TextField nom;
    private Stage modal;
    private boolean create;
    private GestionClassificationController gestionClassificationController;
    private Classification classification;

    @FXML
    private void validate() {
        if (create)
            createFamille();
        else
            updateFamille();
    }

    private void updateFamille() {
        if (!nom.getText().isEmpty())
            classification.setClassificationLibelle(nom.getText());
        if (DAOFactory.getClassificationDAO().update(classification)) {
            gestionClassificationController.filter();
            modal.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une erreur lors de la modification de la classification.\n Merci de vérifier que vous avez entré des informations valides");
            alert.showAndWait();
        }
    }

    private void createFamille() {
        if (!nom.getText().isEmpty()) {
            classification = new Classification(0, nom.getText());
            if (DAOFactory.getClassificationDAO().insert(classification) != 0) {
                gestionClassificationController.filter();
                modal.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une erreur lors de la création de la famille.\n Merci de vérifier que vous avez entré des informations valides");
            alert.showAndWait();
        }
    }

    @FXML
    private void closeModal() {
        modal.close();
    }

    public void setModal(Stage stage) {
        modal = stage;
    }

    public void setCreate(boolean bool) {
        create = bool;
        if (create) {
            nomModal.setText("Création d'une classfication");
        } else {
            nomModal.setText("Modification d'une classification");
        }
    }

    public void setGestionClassificationController(GestionClassificationController gestionClassificationController) {
        this.gestionClassificationController = gestionClassificationController;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
        nom.setText(classification.getClassificationLibelle());
    }
}
