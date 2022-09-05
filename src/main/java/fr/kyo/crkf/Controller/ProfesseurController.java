package fr.kyo.crkf.Controller;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.Personne;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProfesseurController {
    @FXML
    private TableView<Personne> professeurTable;
    @FXML
    private TableColumn<Personne, String> nomColumn;
    @FXML
    private TableColumn<Personne, String> prenomColumn;
    @FXML
    private TableColumn<Personne, Integer> vehiculeCVColumn;
    private ApplicationCRKF applicationCRKF;

    @FXML
     private void initialize(){
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().getPrenomStringProperty());
        vehiculeCVColumn.setCellValueFactory(cellData -> cellData.getValue().getVehiculeCVIntegerProperty().asObject());
        
        professeurTable.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getAll(1)));

        professeurTable.getSelectionModel().selectedItemProperty().addListener(cellData -> openDetailPage());
     }

    private void openDetailPage() {
        applicationCRKF.openDetailProfesseur(professeurTable.getSelectionModel().getSelectedItem());
    }

    public void setApplicationCRKF(ApplicationCRKF applicationCRKF) {
        this.applicationCRKF = applicationCRKF;
    }
}
