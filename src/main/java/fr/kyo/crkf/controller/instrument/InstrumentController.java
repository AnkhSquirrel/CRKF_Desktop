package fr.kyo.crkf.controller.instrument;

import com.jfoenix.controls.JFXDrawer;
import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.controller.AccueilController;
import fr.kyo.crkf.entity.Classification;
import fr.kyo.crkf.entity.Famille;
import fr.kyo.crkf.entity.Instrument;
import fr.kyo.crkf.searchable.Filter;
import fr.kyo.crkf.searchable.SearchableInstrument;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.SearchableComboBox;
import java.io.IOException;
import java.util.Objects;

public class InstrumentController {
    AccueilController accueilController;
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
    @FXML
    private Label numberOfPage;
    private int pageTotale;
    private SearchableInstrument searchableInstrument;

    private ApplicationCRKF applicationCRKF;
    private int page;

     @FXML
     private void initialize() {
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

         libelle.textProperty().addListener((observable, oldValue, newValue) -> {
             libelle.setText(newValue.replaceAll("[\\d'], ""));
             filter();
         });

         pageTotale = DAOFactory.getInstrumentDAO().getLikeAllInstrument(searchableInstrument).size() / 25;
         if (pageTotale == 0)
             pageTotale ++;
         numberOfPage.setText(String.valueOf(pageTotale));

         pageNumber.setText("Page " + page);

         reset();
         filter();
    }

     public void setApplicationCRKF(ApplicationCRKF applicationCRKF){
         this.applicationCRKF = applicationCRKF;
     }

    public void openDetailInstrument(){
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
    public void openDetailInstrument(Instrument instrument){
         filter();
         instrumentTable.getSelectionModel().select(instrument);
         openDetailInstrument();
    }

    public void filter(){
        if(isNomSet())
            searchableInstrument.setNom(libelle.getText());
        if(isClassificationSelected()){
            searchableInstrument.setClassification(classification.getSelectionModel().getSelectedItem());
            famille.getSelectionModel().select(0);
        }
        if(isFamilleSelected()){
            searchableInstrument.setFamille(famille.getSelectionModel().getSelectedItem());
            if(searchableInstrument.getFamilleId() != 0){
                searchableInstrument.setClassificationId(famille.getSelectionModel().getSelectedItem().getClassificationId());
                classification.getSelectionModel().select(famille.getSelectionModel().getSelectedItem().getclassification());
            }
        }
        pageNumber.setText("Page " + page + " / ");
        instrumentTable.setItems(FXCollections.observableArrayList(DAOFactory.getInstrumentDAO().getLike(searchableInstrument, page)));
        pageTotale = DAOFactory.getInstrumentDAO().getLikeAllInstrument(searchableInstrument).size() / 25;
        if(page == 0)
            pageTotale ++;
        numberOfPage.setText(String.valueOf(pageTotale));
        pageNumber.setText("Page " + page + " / ");
    }

    private boolean isNomSet() {
        return !libelle.getText().isEmpty() || !libelle.getText().equals(searchableInstrument.getNom());
    }

    private boolean isFamilleSelected() {
        return famille.getSelectionModel().getSelectedItem() != null && famille.getSelectionModel().getSelectedItem().getFamilleId() != searchableInstrument.getFamilleId();
    }

    private boolean isClassificationSelected() {
        return classification.getSelectionModel().getSelectedItem() != null && classification.getSelectionModel().getSelectedItem().getClassificationId() != searchableInstrument.getClassificationId();
    }

    @FXML
    private void reset(){
        libelle.setText("");
        classification.getSelectionModel().selectFirst();
        famille.getSelectionModel().selectFirst();
    }

    @FXML
    private void pagePlus(){
         if(!instrumentTable.getItems().isEmpty() && pageTotale > page){
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

    @FXML
    private void lastPage(){
        page = pageTotale;
        filter();
    }

    @FXML
    private void firstPage(){
        page = 1;
        filter();
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

        if(applicationCRKF.getLightMode()){
            drawer.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fr/kyo/crkf/lightMode.css").toExternalForm()));
        }
        else{
            drawer.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fr/kyo/crkf/darkMode.css").toExternalForm()));
        }
    }

}