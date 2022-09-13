package fr.kyo.crkf.controller.instrument;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.Classification;
import fr.kyo.crkf.Entity.Famille;
import fr.kyo.crkf.Entity.Instrument;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.Searchable.SearchableInstrument;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private SearchableComboBox<Famille> famille;
    @FXML
    private TextField libelle;
    @FXML
    private TableView<Instrument> instrumentTable;
    @FXML
    private Label pageNumber;

    private SearchableInstrument searchableInstrument;


    private ApplicationCRKF applicationCRKF;
    private int page;

     @FXML
     private void initialize(){
         page = 1;
         Filter filter = new Filter();
        searchableInstrument = new SearchableInstrument();
        // initialize tableview
        libelleColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
        classificationColumn.setCellValueFactory(cellData ->cellData.getValue().getFamilles().get(0).getclassification().getClassificationStringProperty());


         instrumentTable.getSelectionModel().selectedItemProperty().addListener(observable -> openDetailInstrument());

         // Initialisation des comboBox
         classification.setItems(FXCollections.observableArrayList(filter.getClassifications()));
         classification.valueProperty().addListener(observable -> filter());


        famille.setItems(FXCollections.observableArrayList(filter.getFamilles()));
        famille.valueProperty().addListener(observable -> filter());

        libelle.textProperty().addListener(observable -> filter());

        reset();
        filter();
    }

     public void setApplicationCRKF(ApplicationCRKF applicationCRKF){
         this.applicationCRKF = applicationCRKF;
     }

     private void openDetailInstrument(){
         applicationCRKF.openDetailInstrument(instrumentTable.getSelectionModel().getSelectedItem());
     }

    public void filter() {
        if(!libelle.getText().isEmpty() || !libelle.getText().equals(searchableInstrument.getNom())){
            searchableInstrument.setNom(libelle.getText());
        }
        if(classification.getSelectionModel().getSelectedItem() != null && classification.getSelectionModel().getSelectedItem() != searchableInstrument.getFamille().getclassification()){
            searchableInstrument.getFamille().setclassification(classification.getSelectionModel().getSelectedItem());
        }
        if(famille.getSelectionModel().getSelectedItem() != null && famille.getSelectionModel().getSelectedItem() != searchableInstrument.getFamille()){
            searchableInstrument.setFamille(famille.getSelectionModel().getSelectedItem());
            classification.getSelectionModel().select(famille.getSelectionModel().getSelectedItem().getclassification());
        }
        pageNumber.setText("Page " + page);
        instrumentTable.setItems(FXCollections.observableArrayList(DAOFactory.getInstrumentDAO().getLike(searchableInstrument, page)));
    }
    @FXML
    private void reset(){
        libelle.setText("");
        classification.getSelectionModel().selectFirst();
        famille.getSelectionModel().selectFirst();
    }
    @FXML
    private void pagePlus(){
         if(!instrumentTable.getItems().isEmpty()){
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
    @FXML
    private void openMainMenu(){
        applicationCRKF.openMainMenu();
    }
    @FXML
    private void openCreateModal(){
         applicationCRKF.openCreateInstrumentModal(this);
    }
}