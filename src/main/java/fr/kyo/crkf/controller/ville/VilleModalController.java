package fr.kyo.crkf.controller.ville;

import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ville;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

public class VilleModalController {
    @FXML
    private Label nomModal;
    @FXML
    private SearchableComboBox<Departement> nomDepartement;
    @FXML
    private TextField nomVille;
    @FXML
    private TextField longitude;
    @FXML
    private TextField latitude;
    private Stage modal;
    private boolean create;
    private GestionVilleController gestionVilleController;
    private Ville ville;

    @FXML
    private void initialize(){
        nomDepartement.setItems(FXCollections.observableArrayList(DAOFactory.getDepartementDAO().getAll(1)));
    }

    @FXML
    private void validate(){
        if(create)
            createVille();
        else
            updateVille();
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
            nomModal.setText("Créer une ville");
        }else{
            nomModal.setText("Modifier une ville");
        }
    }
    public void setGestionVilleController(GestionVilleController gestionVilleController) {
        this.gestionVilleController= gestionVilleController;
    }
    public void setVille(Ville ville) {
        this.ville = ville;
        nomVille.setText(ville.getVille());
        nomDepartement.getSelectionModel().select(ville.getDepartement());
        latitude.setText(String.valueOf(ville.getLatitude()));
        longitude.setText(String.valueOf(ville.getLongitude()));
    }
    private void createVille(){
        if(!nomVille.getText().isEmpty() && !nomDepartement.getSelectionModel().isEmpty() && !longitude.getText().isEmpty() && !latitude.getText().isEmpty()){
            ville = new Ville(0 , nomVille.getText() , Float.parseFloat(longitude.getText()) , Float.parseFloat(latitude.getText()) , nomDepartement.getSelectionModel().getSelectedItem().getId_departement());
            DAOFactory.getVilleDAO().insert(ville);
            gestionVilleController.filter();
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

    private void updateVille() {
        if(!nomVille.getText().isEmpty())
            ville.setVille(nomVille.getText());
        if(!latitude.getText().isEmpty())
            ville.setLatitude(Float.parseFloat(latitude.getText()));
        if(!longitude.getText().isEmpty())
            ville.setLongitude(Float.parseFloat(longitude.getText()));
        if(!nomDepartement.getSelectionModel().isEmpty())
            ville.setDepartement(nomDepartement.getSelectionModel().getSelectedItem());
        if(DAOFactory.getVilleDAO().update(ville)){
            gestionVilleController.filter();
            modal.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une erreur lors de la modification de la Ville.\n  Merci de vérifier que vous avez entrée des informations valides");
            alert.showAndWait();
        }
    }
}
