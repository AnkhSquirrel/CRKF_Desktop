package fr.kyo.crkf.controller;

import com.jfoenix.controls.JFXDrawer;
import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Personne;
import fr.kyo.crkf.Entity.Ville;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.Searchable.SearchableProfesseur;
import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.controller.ecole.DetailEcoleController;
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
    private Label pageNumber;
    private SearchableProfesseur searchableProfesseur;
    private Filter filter;
    private ApplicationCRKF applicationCRKF;
    private int page;
    @FXML
    private GridPane listeProfesseur;
    @FXML
    private JFXDrawer drawer;

    @FXML
     private void initialize(){
        page = 1;
        searchableProfesseur = new SearchableProfesseur();
        filter = new Filter();

        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().getPrenomStringProperty());

        villeColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresse().getVille().getVilleStringProperty());
        departementColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresse().getVille().getDepartement().getDepartementStringProperty());

        nomEtPrenomFiltre.textProperty().addListener(observable -> filter());

        departementFiltre.setItems(FXCollections.observableArrayList(filter.getDepartements()));
        departementFiltre.getSelectionModel().selectedItemProperty().addListener(observable -> filter());
        departementFiltre.getSelectionModel().select(0);

        villeFiltre.setItems(FXCollections.observableArrayList(filter.getVilles()));
        villeFiltre.getSelectionModel().selectedItemProperty().addListener(observable -> filter());
        villeFiltre.setEditable(true);
        villeFiltre.getEditor().textProperty().addListener(observable -> villeFilter());
        villeFiltre.getEditor().setText("Ville");

        professeurTable.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getLike(searchableProfesseur,1)));
        professeurTable.getSelectionModel().selectedItemProperty().addListener(cellData -> openDetailProfesseur());
     }
    private void villeFilter() {
        if(searchableProfesseur.getVilleId() != 0 && !villeFiltre.getEditor().getText().equals(searchableProfesseur.getVille().getVille())){
            villeFiltre.setItems(FXCollections.observableArrayList(filter.getVilleLike(villeFiltre.getEditor().getText(),searchableProfesseur.getDepartementId())));
        }
    }
    public void filter() {
        if(!nomEtPrenomFiltre.getText().isEmpty() || !nomEtPrenomFiltre.getText().equals(searchableProfesseur.getNomEtPrenom())){
            searchableProfesseur.setNomEtPrenom(nomEtPrenomFiltre.getText());
            page = 1;
        }

        if (departementFiltre.getSelectionModel().getSelectedItem() != null && departementFiltre.getSelectionModel().getSelectedItem().getId_departement() != searchableProfesseur.getDepartementId()){
            searchableProfesseur.setDepartementId(departementFiltre.getSelectionModel().getSelectedItem().getId_departement());
            villeFiltre.getSelectionModel().select(0);
            page = 1;
        }

        if (!villeFiltre.getSelectionModel().isEmpty() && villeFiltre.getSelectionModel().getSelectedItem() != null && villeFiltre.getSelectionModel().getSelectedItem().getId_ville() != searchableProfesseur.getVilleId()){
            searchableProfesseur.setDepartementId(villeFiltre.getSelectionModel().getSelectedItem().getDepartementID());
            searchableProfesseur.setVille(villeFiltre.getSelectionModel().getSelectedItem());
            if(villeFiltre.getSelectionModel().getSelectedItem().getId_ville() == 0)
                departementFiltre.getSelectionModel().select(0);
            else
                departementFiltre.getSelectionModel().select(villeFiltre.getSelectionModel().getSelectedItem().getDepartement());
            page = 1;
        }
        pageNumber.setText("Page " + page);
        professeurTable.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getLike(searchableProfesseur, page)));
    }
    @FXML
    private void pagePlus(){
        if(professeurTable.getItems().size() > 0){
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
    private void filterByDepartement() {
        if (departementFiltre.getSelectionModel().getSelectedItem() != null && (departementFiltre.getSelectionModel().getSelectedItem()).getId_departement() != 0) {
            villeFiltre.setItems(FXCollections.observableArrayList(DAOFactory.getVilleDAO().gettByDepartementID(departementFiltre.getSelectionModel().getSelectedItem().getId_departement())));
        } else {
            villeFiltre.setItems(FXCollections.observableArrayList(filter.getVilles()));
        }
        villeFiltre.getSelectionModel().select(0);
        filter();
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
    }

    public void openDetail(){
        drawer.setDisable(false);
        drawer.open();
        listeProfesseur.setEffect(new GaussianBlur());
        listeProfesseur.setDisable(true);
    }

    public void closeEcoleAroundPage() {
        openDetailProfesseur();
    }

    public void setApplicationCRKF(ApplicationCRKF applicationCRKF) {
        this.applicationCRKF = applicationCRKF;
    }
}
