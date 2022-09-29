package fr.kyo.crkf.controller.ville;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.dao.DAOFactory;
import fr.kyo.crkf.entity.Ecole;
import fr.kyo.crkf.entity.Famille;
import fr.kyo.crkf.entity.Ville;
import fr.kyo.crkf.searchable.Filter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DetailVilleController {

    @FXML
    private Label labelVille;
    @FXML
    private TableView<Ecole> listeEcole;
    @FXML
    private TableColumn<Ecole, String> ecoleNom;
    @FXML
    private TableColumn<Ecole, String> ecoleAdresse;
    @FXML
    private TableColumn<Ecole, String> villeColumn;
    @FXML
    private ComboBox<Famille> familleEnseignee;
    private Ville ville;
    private GestionVilleController gestionVilleController;

    @FXML
    private void initialize(){
        Filter filter = new Filter();

        ecoleNom.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
        ecoleAdresse.setCellValueFactory(cellData -> cellData.getValue().getEcoleAdresse().getAdresseStringProperty());
        villeColumn.setCellValueFactory(cellData -> cellData.getValue().getEcoleAdresse().getVille().getVilleStringProperty());
        familleEnseignee.setItems(FXCollections.observableArrayList(filter.getFamilles()));
        familleEnseignee.getSelectionModel().select(0);
        familleEnseignee.getSelectionModel().selectedItemProperty().addListener(observable -> filter());
    }

    public void filter(){
        if(!familleEnseignee.getSelectionModel().isEmpty() && familleEnseignee.getSelectionModel().getSelectedItem() != null){
            listeEcole.setItems(FXCollections.observableArrayList(DAOFactory.getEcoleDAO().getByDistanceAndInstrument(ville.getLatitude(), ville.getLongitude(), familleEnseignee.getSelectionModel().getSelectedItem().getFamilleId())));
        }
    }

    public void setVille(Ville ville) {
        this.ville = ville;
        labelVille.setText(ville.getVilleLibelle());
        listeEcole.setItems(FXCollections.observableArrayList(DAOFactory.getEcoleDAO().getByDistanceAndInstrument(ville.getLatitude(), ville.getLongitude(), familleEnseignee.getSelectionModel().getSelectedItem().getFamilleId())));
    }

    @FXML
    private void closeDetail(){
        gestionVilleController.closeDetail();
    }

    public void setGestionVilleController(GestionVilleController gestionVilleController) {
        this.gestionVilleController = gestionVilleController;
    }

}
