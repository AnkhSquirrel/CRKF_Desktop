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
import javafx.scene.control.ComboBox;
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
    private ComboBox<Ville> villeFiltre;
    @FXML
    private SearchableComboBox<Departement> departementFiltre;
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

        villeFiltre.setItems(FXCollections.observableArrayList(filter.getVilles()));
        villeFiltre.getSelectionModel().selectedItemProperty().addListener(observable -> filter());
        villeFiltre.setEditable(true);
        villeFiltre.getEditor().textProperty().addListener(observable -> villeFilter());

        professeurTable.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getAll(1)));
        professeurTable.getSelectionModel().selectedItemProperty().addListener(cellData -> openDetailPage());
     }

    private void villeFilter() {
        villeFiltre.setItems(FXCollections.observableArrayList(filter.getVilleLike(villeFiltre.getEditor().getText())));
    }

    private void filter() {
        if(!nomEtPrenomFiltre.getText().isEmpty() || !nomEtPrenomFiltre.getText().equals(searchableProfesseur.getNomEtPrenom())){
            searchableProfesseur.setNomEtPrenom(nomEtPrenomFiltre.getText());
            page = 1;
        }

        if (departementFiltre.getSelectionModel().getSelectedItem() != null){
            searchableProfesseur.getVille().setDepartement(departementFiltre.getSelectionModel().getSelectedItem());
            page = 1;
        }

        if (villeFiltre.getSelectionModel().getSelectedItem() != null){
            searchableProfesseur.setVille(villeFiltre.getSelectionModel().getSelectedItem());
            page = 1;
        }
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
        applicationCRKF.openDetailProfesseur(professeurTable.getSelectionModel().getSelectedItem());
    }

    public void setApplicationCRKF(ApplicationCRKF applicationCRKF) {
        this.applicationCRKF = applicationCRKF;
    }

}
