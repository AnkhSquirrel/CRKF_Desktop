package fr.kyo.crkf.Controller;

import fr.kyo.crkf.Entity.Personne;
import fr.kyo.crkf.Searchable.SearchableProfesseur;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProfesseurController {
    @FXML
    private TableView<Object> professeurTable;
    @FXML
    private TableColumn<Personne, String> nomColumn;
    @FXML
    private TableColumn<Personne, String> prenomColumn;
    @FXML
    private TableColumn<Personne, Integer> vehiculeCVColumn;
    @FXML
    private TextField nomEtPrenom;

    private SearchableProfesseur searchableProfesseur;

    @FXML
     private void initialize(){
        searchableProfesseur = new SearchableProfesseur();

        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().getPrenomStringProperty());
        vehiculeCVColumn.setCellValueFactory(cellData -> cellData.getValue().getVehiculeCVIntegerProperty().asObject());

        nomEtPrenom.textProperty().addListener(observable -> filter());
        
        professeurTable.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getAll()));
     }

    private void filter() {
        if(!nomEtPrenom.getText().isEmpty() || !nomEtPrenom.getText().equals(searchableProfesseur.getNomEtPrenom())){
            searchableProfesseur.setNomEtPrenom(nomEtPrenom.getText());
        }
        professeurTable.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getLike(searchableProfesseur)));
    }


}
