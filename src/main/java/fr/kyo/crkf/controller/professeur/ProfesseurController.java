package fr.kyo.crkf.controller.professeur;

import com.jfoenix.controls.JFXDrawer;
import fr.kyo.crkf.entity.Departement;
import fr.kyo.crkf.entity.Instrument;
import fr.kyo.crkf.entity.Personne;
import fr.kyo.crkf.entity.Ville;
import fr.kyo.crkf.searchable.Filter;
import fr.kyo.crkf.searchable.SearchableProfesseur;
import fr.kyo.crkf.ApplicationCRKF;
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

public class ProfesseurController {

    @FXML
    private TableView<Personne> professeurTable;
    @FXML
    private TableColumn<Personne, String> nomColumn;
    @FXML
    private TableColumn<Personne, String> prenomColumn;
    @FXML
    private TableColumn<Personne, String> villeColumn;
    @FXML
    private TableColumn<Personne, String> departementColumn;
    @FXML
    private TextField nomEtPrenomFiltre;
    @FXML
    private ComboBox<Ville> villeFiltre;
    @FXML
    private SearchableComboBox<Departement> departementFiltre;
    @FXML
    private SearchableComboBox<Instrument> instrumentFiltre;
    @FXML
    private Label pageNumber;
    @FXML
    private Label numberOfPage;
    @FXML
    private GridPane listeProfesseur;
    @FXML
    private JFXDrawer drawer;
    private int pageTotale;
    private SearchableProfesseur searchableProfesseur;
    private Filter filter;
    private ApplicationCRKF applicationCRKF;
    private int page;

    @FXML
     private void initialize(){
        page = 1;
        searchableProfesseur = new SearchableProfesseur();
        filter = new Filter();

        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().getPrenomStringProperty());

        villeColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresseId().getVille().getVilleStringProperty());
        departementColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresseId().getVille().getDepartement().getDepartementStringProperty());

        nomEtPrenomFiltre.textProperty().addListener((observable, oldValue, newValue) -> {
            nomEtPrenomFiltre.setText(newValue.replaceAll("[\\d'], ""));
            filter();
        });

        villeFiltre.setEditable(true);
        villeFiltre.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            villeFiltre.getEditor().setText(newValue.replaceAll("[\\d'], ""));
            villeFilter();
        });
        villeFiltre.getSelectionModel().select(0);

        departementFiltre.setItems(FXCollections.observableArrayList(filter.getDepartements()));
        departementFiltre.getSelectionModel().selectedItemProperty().addListener(observable -> filterDepartement());

        instrumentFiltre.setItems(FXCollections.observableArrayList(filter.getInstrument()));
        instrumentFiltre.getSelectionModel().selectedItemProperty().addListener(observable -> filter());

        professeurTable.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getLike(searchableProfesseur,1)));

        pageTotale = FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getLikeAllPersonne(searchableProfesseur)).size() / 25;
        if (pageTotale == 0)
            pageTotale ++;
        numberOfPage.setText(String.valueOf(pageTotale));

        professeurTable.getSelectionModel().selectedItemProperty().addListener(cellData -> openDetailProfesseur());
     }

    private void villeFilter() {
        if((searchableProfesseur.getVilleId() == 0 && villeFiltre.getSelectionModel().getSelectedItem() == null) || !villeFiltre.getEditor().getText().equals(villeFiltre.getSelectionModel().getSelectedItem().getVilleLibelle())){
            villeFiltre.setItems(FXCollections.observableArrayList(filter.getVilleLike(villeFiltre.getEditor().getText(),searchableProfesseur.getDepartementId())));
        }
    }

    private void filterDepartement() {
        filter();
        villeFiltre.setDisable(departementFiltre.getSelectionModel().getSelectedItem() == null || departementFiltre.getSelectionModel().getSelectedItem().getDepartementId() == 0);
        villeFiltre.setItems(FXCollections.observableArrayList(filter.getVilleLike("",searchableProfesseur.getDepartementId())));
        villeFiltre.getSelectionModel().select(0);
    }

    public void filter() {
        if(!nomEtPrenomFiltre.getText().isEmpty() || !nomEtPrenomFiltre.getText().equals(searchableProfesseur.getNomEtPrenom())){
            searchableProfesseur.setNomEtPrenom(nomEtPrenomFiltre.getText());
            page = 1;
        }
        if (isDepartementSelected()){
            searchableProfesseur.setDepartementId(departementFiltre.getSelectionModel().getSelectedItem().getDepartementId());
            villeFiltre.getSelectionModel().select(0);
            page = 1;
        }
        if(isInstrumentSelected()){
            searchableProfesseur.setInstrument(instrumentFiltre.getSelectionModel().getSelectedItem().getInstrumentId());
            page = 1;
        }
        if (!villeFiltre.getSelectionModel().isEmpty() && villeFiltre.getSelectionModel().getSelectedItem() != null && villeFiltre.getSelectionModel().getSelectedItem().getVilleId() != searchableProfesseur.getVilleId()){
            searchableProfesseur.setVille(villeFiltre.getSelectionModel().getSelectedItem());
            page = 1;
        }

        pageTotale = FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getLikeAllPersonne(searchableProfesseur)).size() / 25;
        if (pageTotale == 0)
            pageTotale ++;
        numberOfPage.setText(String.valueOf(pageTotale));

        pageNumber.setText("Page " + page + " / ");
        professeurTable.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getLike(searchableProfesseur, page)));
    }

    private boolean isDepartementSelected() {
        return departementFiltre.getSelectionModel().getSelectedItem() != null && departementFiltre.getSelectionModel().getSelectedItem().getDepartementId() != searchableProfesseur.getDepartementId();
    }

    private boolean isInstrumentSelected() {
        return instrumentFiltre.getSelectionModel().getSelectedItem() != null && instrumentFiltre.getSelectionModel().getSelectedItem().getInstrumentId() != searchableProfesseur.getInstrumentId();
    }

    @FXML
    private void pagePlus(){
        if(!professeurTable.getItems().isEmpty() && pageTotale > page ){
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
    private void reset(){
        page = 1;
        nomEtPrenomFiltre.setText("");
        villeFiltre.getSelectionModel().selectFirst();
        departementFiltre.getSelectionModel().selectFirst();
        instrumentFiltre.getSelectionModel().select(0);
    }

    private void openDetailProfesseur() {
        if(professeurTable.getSelectionModel().getSelectedItem() != null){
            try {
                FXMLLoader fxmlLoaderListeProfesseur = new FXMLLoader();
                fxmlLoaderListeProfesseur.setLocation(ApplicationCRKF.class.getResource("detail_professeur.fxml"));
                VBox detailEcole = fxmlLoaderListeProfesseur.load();
                DetailProfesseurController detailProfesseurController = fxmlLoaderListeProfesseur.getController();
                detailProfesseurController.setPersonne(professeurTable.getSelectionModel().getSelectedItem());
                detailProfesseurController.setProfesseurController(this);
                detailProfesseurController.setApplicationCRKF(applicationCRKF);
                detailProfesseurController.setDrawer(drawer);
                drawer.setSidePane(detailEcole);
                drawer.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
                openDetail();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openDetailProfesseur(Personne personne){
        professeurTable.getSelectionModel().select(personne);
        openDetailProfesseur();
    }

    @FXML
    private void openMainMenu(){
        applicationCRKF.openMainMenu();
    }

    @FXML
    private void openCreateModal(){
        applicationCRKF.openCreateProfesseurModal(this);
    }

    public void closeDetail(){
        drawer.close();
        drawer.setDisable(true);
        listeProfesseur.setEffect(null);
        listeProfesseur.setDisable(false);
        professeurTable.getSelectionModel().clearSelection();
    }

    public void openDetail(){
        drawer.setDisable(false);
        drawer.open();
        listeProfesseur.setEffect(new GaussianBlur());
        listeProfesseur.setDisable(true);

        if (applicationCRKF.getLightMode()){
            drawer.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fr/kyo/crkf/lightMode.css").toExternalForm()));
        }
        else{
            drawer.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fr/kyo/crkf/darkMode.css").toExternalForm()));
        }
    }

    public void closeEcoleAroundPage() {
        openDetailProfesseur();
    }

    public void setApplicationCRKF(ApplicationCRKF applicationCRKF) {
        this.applicationCRKF = applicationCRKF;
    }

}
