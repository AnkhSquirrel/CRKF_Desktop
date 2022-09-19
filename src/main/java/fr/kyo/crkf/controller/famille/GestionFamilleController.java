package fr.kyo.crkf.controller.famille;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.Classification;
import fr.kyo.crkf.Entity.Famille;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.Searchable.SearchableFamille;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.SearchableComboBox;

import java.util.Optional;

public class GestionFamilleController {
    @FXML
    private TableColumn<Famille,String> libelleColumn;
    @FXML
    private TableColumn<Famille,String> classificationColumn;
    @FXML
    private SearchableComboBox<Classification> classification;
    @FXML
    private TextField libelle;
    @FXML
    private TableView<Famille> familleTable;
    @FXML
    private Label pageNumber;
    private int page;
    private SearchableFamille searchableFamille;
    private ApplicationCRKF applicationCRKF;

    @FXML
    private void initialize(){
        searchableFamille = new SearchableFamille();
        page = 1;
        Filter filter = new Filter();
        // initialize tableview
        libelleColumn.setCellValueFactory(cellData -> cellData.getValue().getFamilleStringProperty());
        classificationColumn.setCellValueFactory(cellData ->cellData.getValue().getclassification().getClassificationStringProperty());

        // Initialisation des comboBox
        classification.setItems(FXCollections.observableArrayList(filter.getClassifications()));
        classification.getSelectionModel().selectedItemProperty().addListener(observable -> filter());
        classification.getSelectionModel().select(0);

        libelle.textProperty().addListener(observable -> filter());

        familleTable.setItems(FXCollections.observableArrayList(DAOFactory.getFamilleDAO().getLike(searchableFamille,page)));

        reset();
        filter();
    }

    public void filter(){
        if(!libelle.getText().equals(searchableFamille.getNom())){
            searchableFamille.setNom(libelle.getText());
            page = 1;
        }
        if(classification.getSelectionModel().getSelectedItem() != null && classification.getSelectionModel().getSelectedItem().getId_classification() != searchableFamille.getClassification()){
            searchableFamille.setClassification(classification.getSelectionModel().getSelectedItem().getId_classification());
            page = 1;
        }
        familleTable.setItems(FXCollections.observableArrayList(DAOFactory.getFamilleDAO().getLike(searchableFamille,page)));
        pageNumber.setText("Page " + page);
    }

    @FXML
    private void openCreateModal(){
        applicationCRKF.openModalCreateFamille(this);
    }
    @FXML
    private void reset(){
        libelle.setText("");
        classification.getSelectionModel().select(0);
    }
    @FXML
    private void pagePlus(){
        if(!familleTable.getItems().isEmpty()){
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

    public void setApplicationCRKF(ApplicationCRKF applicationCRKF){
        this.applicationCRKF = applicationCRKF;
    }
    @FXML
    private void openMainMenu(){
        applicationCRKF.openMainMenu();
    }

    @FXML
    private void remove(){
        if (familleTable.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Supprimer");
            alert.setHeaderText("Voulez vous vraiment supprimer cet element?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
                DAOFactory.getFamilleDAO().delete(familleTable.getSelectionModel().getSelectedItem());
            filter();
        }
    }
    @FXML
    private void update(){
        if (familleTable.getSelectionModel().getSelectedItem() != null)
        applicationCRKF.openModalUpdateFamille(this, familleTable.getSelectionModel().getSelectedItem());
    }
}

