package fr.kyo.crkf.controller;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.Classification;
import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ecole;
import fr.kyo.crkf.Entity.Famille;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.Searchable.SearchableFamille;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.SearchableComboBox;

import java.util.Optional;

public class GestionDepartementController {
    @FXML
    private TableColumn<Departement,String> departementColumn;
    @FXML
    private TableColumn<Ecole,Integer> nbreEcoleColumn;
    @FXML
    private SearchableComboBox<Classification> classification;
    @FXML
    private TextField libelle;
    @FXML
    private TableView<Departement> departementTable;
    private String departement;
    private ApplicationCRKF applicationCRKF;
    private int page;

    @FXML
    private void initialize(){
        departement="";
        page = 1;
        Filter filter = new Filter();
        // initialize tableview
        departementColumn.setCellValueFactory(cellData -> cellData.getValue().getDepartementStringProperty());

        libelle.textProperty().addListener(observable -> filter());

        departementTable.setItems(FXCollections.observableArrayList(DAOFactory.getDepartementDAO().getLike(departement,page)));

        filter();
    }

    public void filter(){
        if(!libelle.getText().equals(departement)){
            departement = libelle.getText();
            page = 1;
        }
        departementTable.setItems(FXCollections.observableArrayList(DAOFactory.getDepartementDAO().getLike(departement,page)));
    }


    public void setApplicationCRKF(ApplicationCRKF applicationCRKF){
        this.applicationCRKF = applicationCRKF;
    }
    @FXML
    private void openMainMenu(){
        applicationCRKF.openMainMenu();
    }
    @FXML
    private void update(){
        applicationCRKF.openModalUpdateDepartement(this, departementTable.getSelectionModel().getSelectedItem());
    }
    @FXML
    private void openCreateModal(){
        applicationCRKF.openModalCreateDepartement(this);
    }

    @FXML
    private void remove(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet element?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
            DAOFactory.getDepartementDAO().delete(departementTable.getSelectionModel().getSelectedItem());
        filter();
    }

    @FXML
    private void pagePlus(){
        if(!departementTable.getItems().isEmpty()){
            page++;
            filter();
        }

    }
    @FXML
    private void pageMoins(){
        if (page > 1){
            page--;
            filter();
        }
    }
}

