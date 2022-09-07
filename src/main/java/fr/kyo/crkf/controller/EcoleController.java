package fr.kyo.crkf.controller;


import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ecole;
import fr.kyo.crkf.Entity.Ville;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.Searchable.SearchableEcole;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.SearchableComboBox;

public class EcoleController {

        @FXML
        private TableView<Ecole> ecoleTable;
        @FXML
        private TableColumn<Ecole, String> nomColumn;
        @FXML
        private TableColumn<Ecole, String> adresseColumn;
        @FXML
        private TableColumn<Ecole, String> villeColumn;
        @FXML
        private TableColumn<Ecole, String> departementColumn;
        @FXML
        private ComboBox<Ville> ville;
        @FXML
        private SearchableComboBox<Departement> departement;
        @FXML
        private Button reset;
        @FXML
        private TextField nomEcole;
        private SearchableEcole searchableEcole;
        private Filter filter;
        private ApplicationCRKF applicationCRKF;

        private int page;

        @FXML
        private void initialize(){
                page = 1;
                filter = new Filter();

                searchableEcole = new SearchableEcole();

                // Intialisation des colomnes
                nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
                villeColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresse().getVille().getVilleStringProperty());
                adresseColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresse().getAdresseStringProperty());
                departementColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresse().getVille().getDepartement().getDepartementStringProperty());


                // Intialisation des comboBox
                villeFilter();
                ville.getSelectionModel().selectedItemProperty().addListener(observable -> filter());
                ville.setEditable(true);
                ville.getEditor().textProperty().addListener(observable -> villeFilter());
                ville.getEditor().setText("Ville");

                departement.setItems(FXCollections.observableArrayList(filter.getDepartements()));
                departement.getSelectionModel().selectedItemProperty().addListener(observable -> filterDepartement());

                nomEcole.textProperty().addListener(observable -> filter());

                ecoleTable.getSelectionModel().selectedItemProperty().addListener(observable -> openDetailEcole());

                ecoleTable.setItems(FXCollections.observableArrayList(DAOFactory.getEcoleDAO().getLike(searchableEcole, page)));



        }

        private void filterDepartement() {
                filter();
                ville.setItems(FXCollections.observableArrayList(filter.getVilleLike("",searchableEcole.getDepartement().getId_departement())));
                ville.getSelectionModel().select(0);
        }

        private void villeFilter() {
                if(!ville.getEditor().getText().equals(searchableEcole.getVille().getVille())){
                        ville.setItems(FXCollections.observableArrayList(filter.getVilleLike(ville.getEditor().getText(),searchableEcole.getDepartement().getId_departement())));
                }

        }

        @FXML
        private void reset(){
                page = 1;
                nomEcole.setText("");
                departement.getSelectionModel().selectFirst();
                ville.getSelectionModel().selectFirst();
                //filter();
        }

        @FXML
        private void filter(){
                if(!nomEcole.getText().isEmpty() || !nomEcole.getText().equals(searchableEcole.getNom())) {
                        searchableEcole.setNom(nomEcole.getText());
                        page = 1;
                }
                if(!ville.getSelectionModel().isEmpty() && ville.getSelectionModel().getSelectedItem() != null && ville.getSelectionModel().getSelectedItem() != searchableEcole.getVille()){
                        searchableEcole.setVille(ville.getSelectionModel().getSelectedItem());
                        page = 1;
                }

                if(departement.getSelectionModel().getSelectedItem() != null && departement.getSelectionModel().getSelectedItem() != searchableEcole.getDepartement() ){
                        searchableEcole.setDepartement(departement.getSelectionModel().getSelectedItem());
                        page = 1;
                }

                ecoleTable.setItems(FXCollections.observableArrayList(DAOFactory.getEcoleDAO().getLike(searchableEcole, page)));
        }

        private void openDetailEcole(){
                applicationCRKF.openDetailEcole(ecoleTable.getSelectionModel().getSelectedItem());
        }
        public void setApplicationCRKF (ApplicationCRKF applicationCRKF) {
                this.applicationCRKF = applicationCRKF;
        }

        @FXML
        private void pagePlus(){
                if(ecoleTable.getItems().size() > 0){
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
        private void openMainMenu(){
                applicationCRKF.openMainMenu();
        }

}
