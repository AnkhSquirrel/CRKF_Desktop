package fr.kyo.crkf.controller;

import fr.kyo.crkf.Entity.Classification;
import fr.kyo.crkf.Entity.Famille;
import fr.kyo.crkf.Searchable.Filter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

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
    private int page;

    @FXML
    private void initialize(){
        page = 1;
        Filter filter = new Filter();
        // initialize tableview
        libelleColumn.setCellValueFactory(cellData -> cellData.getValue().getFamilleStringProperty());
        classificationColumn.setCellValueFactory(cellData ->cellData.getValue().getclassification().getClassificationStringProperty());


        //familleTable.getSelectionModel().selectedItemProperty().addListener(observable -> openDetailFamille());

        // Initialisation des comboBox
        classification.setItems(FXCollections.observableArrayList(filter.getClassifications()));
        classification.valueProperty().addListener(observable -> filter());

        libelle.textProperty().addListener(observable -> filter());

        familleTable.setItems(FXCollections.observableArrayList(filter.getFamilles()));

        reset();
        filter();
    }

    private void filter(){
    }

    @FXML
    private void openCreateModal(){}
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
    private void pageMoin(){
        if (page > 1){
            page--;
            filter();
        }
    }
}
