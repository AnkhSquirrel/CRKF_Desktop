package fr.kyo.crkf.controller;

import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Personne;
import fr.kyo.crkf.Entity.Ville;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.Searchable.SearchableProfesseur;
import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.SearchableComboBox;


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
        professeurTable.getSelectionModel().selectedItemProperty().addListener(cellData -> openDetailPage());
     }
    private void villeFilter() {
        if(searchableProfesseur.getVilleId() != 0 && !villeFiltre.getEditor().getText().equals(searchableProfesseur.getVille().getVille())){
            villeFiltre.setItems(FXCollections.observableArrayList(filter.getVilleLike(villeFiltre.getEditor().getText(),searchableProfesseur.getDepartementId())));
        }
    }
    private void filter() {
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
    private void openDetailPage() {
        if(!professeurTable.getSelectionModel().isEmpty())
            applicationCRKF.openDetailProfesseur(professeurTable.getSelectionModel().getSelectedItem());
    }
    public void setApplicationCRKF(ApplicationCRKF applicationCRKF) {
        this.applicationCRKF = applicationCRKF;
    }
    @FXML
    private void openMainMenu(){
        applicationCRKF.openMainMenu();
    }
}
