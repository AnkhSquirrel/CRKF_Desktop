package fr.kyo.crkf.Controller;

import fr.kyo.crkf.Entity.Classification;
import fr.kyo.crkf.Entity.Famille;
import fr.kyo.crkf.Entity.Instrument;
import fr.kyo.crkf.Searchable.SearchableInstrument;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

public class InstrumentController {
    @FXML
    private TableColumn<Instrument, String> libelleColumn;
    @FXML
    private TableColumn<Instrument, String> classificationColumn;
    @FXML
    private SearchableComboBox<Classification> classification;
    @FXML
    private TextField libelle;
    @FXML
    private TableView<Object> instrumentTable;

    private SearchableInstrument searchableInstrument;

     @FXML
     private void initialize(){
         searchableInstrument = new SearchableInstrument();
         // initialize tableview
         libelleColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
         classificationColumn.setCellValueFactory(cellData ->cellData.getValue().getFamilles().get(0).getclassification().getClassificationStringProperty());
         //instrumentTable.setItems(FXCollections.observableArrayList(DAOFactory.getInstrumentDAO().getAll()));

         // Initialisation des comboBox
         classification.setItems(FXCollections.observableArrayList(DAOFactory.getClassificationDAO().getAll(1)));
         classification.valueProperty().addListener(observable -> filter());

         libelle.textProperty().addListener(observable -> filter());

         filter();
     }

    private void filter() {
         if(!libelle.getText().isEmpty() || !libelle.getText().equals(searchableInstrument.getNom())){
             searchableInstrument.setNom(libelle.getText());
         }
         if(classification.getSelectionModel().getSelectedItem() != null && classification.getSelectionModel().getSelectedItem() != searchableInstrument.getFamille().getclassification()){
             searchableInstrument.getFamille().setclassification(classification.getSelectionModel().getSelectedItem());
         }
         instrumentTable.setItems(FXCollections.observableArrayList(DAOFactory.getInstrumentDAO().getLike(searchableInstrument)));
    }
}
