package fr.kyo.crkf.Controller;


import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ecole;
import fr.kyo.crkf.Entity.Ville;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.Searchable.SearchableEcole;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.SearchableComboBox;

import java.util.ArrayList;

public class EcoleController {

        @FXML
        private TableView<Object> ecoleTable;

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

        private Filter filter;

        @FXML
        private Button reset;

        @FXML
        private TextField nomEcole;
        private SearchableEcole searchableEcole;
        @FXML
        private void initialize(){
                 filter = new Filter();

                searchableEcole = new SearchableEcole();

                // Intialisation des colomnes
                nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
                villeColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresse().getVille().getVilleStringProperty());
                adresseColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresse().getAdresseStringProperty());
                departementColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresse().getVille().getDepartement().getDepartementStringProperty());


                // Intialisation des comboBox
                ville.setItems(FXCollections.observableArrayList(filter.getVilles()));
                ville.valueProperty().addListener(observable -> filter());
                ville.setEditable(true);
                ville.getEditor().textProperty().addListener(observable -> villeFilter());

                departement.setItems(FXCollections.observableArrayList(filter.getDepartements()));
                departement.getSelectionModel().selectedItemProperty().addListener(observable -> filterByDepartement());

                nomEcole.textProperty().addListener(observable -> filter());

                ecoleTable.setItems(FXCollections.observableArrayList(DAOFactory.getEcoleDAO().getLike(searchableEcole)));


        }

        private void villeFilter() {
                ville.setItems(FXCollections.observableArrayList(filter.getVilleLike(ville.getEditor().getText())));
        }

        @FXML
        private void reset(){
                nomEcole.setText("");
                departement.getSelectionModel().selectFirst();
                ville.getSelectionModel().selectFirst();
        }

        @FXML
        private void filter(){
                if(!nomEcole.getText().isEmpty() || !nomEcole.getText().equals(searchableEcole.getNom())) {
                        searchableEcole.setNom(nomEcole.getText());
                }
                if(ville.getSelectionModel().getSelectedItem() != null && ville.getSelectionModel().getSelectedItem() != searchableEcole.getVille()){
                        searchableEcole.setVille(ville.getSelectionModel().getSelectedItem());
                }

                if(departement.getSelectionModel().getSelectedItem() != null && departement.getSelectionModel().getSelectedItem() != searchableEcole.getVille().getDepartement() ){
                        searchableEcole.setDepartement(departement.getSelectionModel().getSelectedItem());
                }

                ecoleTable.setItems(FXCollections.observableArrayList(DAOFactory.getEcoleDAO().getLike(searchableEcole)));
        }

        private void filterByDepartement() {
                if (departement.getSelectionModel().getSelectedItem() != null && departement.getSelectionModel().getSelectedItem().getId_departement() != 0) {
                        ArrayList<Ville> villes = DAOFactory.getVilleDAO().gettByDepartementID(departement.getSelectionModel().getSelectedItem().getId_departement());
                        villes.add(0,new Ville(0,"Ville",0f,0f,new Departement(0,"", "")));
                        ville.setItems(FXCollections.observableArrayList(villes));
                } else {
                        ville.setItems(FXCollections.observableArrayList(filter.getVilles()));
                }
                ville.getSelectionModel().select(0);
                filter();
        }

}
