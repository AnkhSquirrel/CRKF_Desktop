package fr.kyo.crkf.controller.instrument;

import com.jfoenix.controls.JFXDrawer;
import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.Classification;
import fr.kyo.crkf.Entity.Famille;
import fr.kyo.crkf.Entity.Instrument;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.Searchable.SearchableInstrument;
import fr.kyo.crkf.controller.ecole.DetailEcoleController;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;

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
    @FXML
    private JFXDrawer drawer;
    @FXML
    private GridPane listeInstrument;
    private SearchableInstrument searchableInstrument;


    private ApplicationCRKF applicationCRKF;
    private int page;

     @FXML
     private void initialize() throws IOException {
         page = 1;
         Filter filter = new Filter();
         searchableInstrument = new SearchableInstrument();
         libelleColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
         classificationColumn.setCellValueFactory(cellData ->cellData.getValue().getFamilles().get(0).getclassification().getClassificationStringProperty());

         instrumentTable.getSelectionModel().selectedItemProperty().addListener(observable -> openDetailInstrument());

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
        if(instrumentTable.getSelectionModel().getSelectedItem() != null){
            try {
                FXMLLoader fxmlLoaderListeInstrument = new FXMLLoader();
                fxmlLoaderListeInstrument.setLocation(ApplicationCRKF.class.getResource("detail_instrument.fxml"));
                VBox detailInstrument = fxmlLoaderListeInstrument.load();
                DetailInstrumentController detailInstrumentController = fxmlLoaderListeInstrument.getController();
                detailInstrumentController.setInstrument(instrumentTable.getSelectionModel().getSelectedItem());
                detailInstrumentController.setInstrumentController(this);
                detailInstrumentController.setApplicationCRKF(applicationCRKF);
                drawer.setSidePane(detailInstrument);
                drawer.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
                openDetail();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void filter(){
        if(!libelle.getText().isEmpty() || !libelle.getText().equals(searchableInstrument.getNom())){
            searchableInstrument.setNom(libelle.getText());
        }
        if(classification.getSelectionModel().getSelectedItem() != null && classification.getSelectionModel().getSelectedItem().getId_classification() != searchableInstrument.getClassificationId()){
            searchableInstrument.setClassification(classification.getSelectionModel().getSelectedItem());
            famille.getSelectionModel().select(0);
        }
        if(famille.getSelectionModel().getSelectedItem() != null && famille.getSelectionModel().getSelectedItem().getId_famille() != searchableInstrument.getFamilleId()){
            searchableInstrument.setFamille(famille.getSelectionModel().getSelectedItem());
            if(searchableInstrument.getFamilleId() != 0){
                searchableInstrument.setClassificationId(famille.getSelectionModel().getSelectedItem().getId_classification());
                classification.getSelectionModel().select(famille.getSelectionModel().getSelectedItem().getclassification());
            }

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

    public void closeDetail(){
        drawer.close();
        drawer.setDisable(true);
        listeInstrument.setEffect(null);
        listeInstrument.setDisable(false);
        instrumentTable.getSelectionModel().clearSelection();
    }

    public void openDetail(){
        drawer.setDisable(false);
        drawer.open();
        listeInstrument.setEffect(new GaussianBlur());
        listeInstrument.setDisable(true);
    }
}