package fr.kyo.crkf.Controller;

import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Personne;
import fr.kyo.crkf.Entity.Ville;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.Searchable.SearchableProfesseur;
import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private SearchableComboBox<Ville> villeFiltre;
    @FXML
    private SearchableComboBox<Departement> departementFiltre;
    private SearchableProfesseur searchableProfesseur;
    private Filter filter;
    private ApplicationCRKF applicationCRKF;

    @FXML
     private void initialize(){
        searchableProfesseur = new SearchableProfesseur();
        filter = new Filter();

        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().getPrenomStringProperty());

        villeColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresse().getVille().getVilleStringProperty());
        departementColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresse().getVille().getDepartement().getDepartementStringProperty());

        nomEtPrenomFiltre.textProperty().addListener(observable -> filter());

        departementFiltre.setItems(FXCollections.observableArrayList(filter.getDepartements()));
        departementFiltre.getSelectionModel().selectedItemProperty().addListener(observable -> filterByDepartement());

        villeFiltre.setItems(FXCollections.observableArrayList(filter.getVilles()));
        villeFiltre.getSelectionModel().selectedItemProperty().addListener(observable -> filter());

        professeurTable.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getAll(1)));
        professeurTable.getSelectionModel().selectedItemProperty().addListener(cellData -> openDetailPage());
     }

    private void filter() {
        if(!nomEtPrenomFiltre.getText().isEmpty() || !nomEtPrenomFiltre.getText().equals(searchableProfesseur.getNomEtPrenom()))
            searchableProfesseur.setNomEtPrenom(nomEtPrenomFiltre.getText());
        if (departementFiltre.getSelectionModel().getSelectedItem() != null)
            searchableProfesseur.getVille().setDepartement(departementFiltre.getSelectionModel().getSelectedItem());
        if (villeFiltre.getSelectionModel().getSelectedItem() != null){
            searchableProfesseur.setVille(villeFiltre.getSelectionModel().getSelectedItem());
            departementFiltre.getSelectionModel().select(villeFiltre.getSelectionModel().getSelectedItem().getDepartement());
        }


        professeurTable.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getLike(searchableProfesseur)));
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
        applicationCRKF.openDetailProfesseur(professeurTable.getSelectionModel().getSelectedItem());
    }

    public void setApplicationCRKF(ApplicationCRKF applicationCRKF) {
        this.applicationCRKF = applicationCRKF;
    }

}
