package fr.kyo.crkf.controller.ville;

import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ville;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

public class VilleModalController {
    @FXML
    private Label nomModal;
    @FXML
    private SearchableComboBox nomDepartement;
    @FXML
    private TextField nomVille;
    private Stage modal;
    private boolean create;
    private GestionVilleController gestionVilleController;
    private Ville ville;


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
        nomDepartement.setItems(FXCollections.observableArrayList(DAOFactory.getDepartementDAO().getAll(1)));
    }
    private void createVille(){
/*        if(!nomVille.getText().isEmpty() && nomDepartement.getSelectionModel().isEmpty()){
        }*/
    }

    private void updateVille() {
    }
}
