package fr.kyo.crkf.Controller;

import fr.kyo.crkf.Entity.Classification;
import fr.kyo.crkf.Entity.Ecole;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

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
        private SearchableComboBox<Classification> classification;

        @FXML
        private TextField libelle;

        @FXML
        private Button reset;


        @FXML
        private void initialize(){
                nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
                villeColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresse().getVille().getVilleStringProperty());
                adresseColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresse().getAdresseStringProperty());
                departementColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresse().getVille().getDepartement().getDepartementStringProperty());
                ecoleTable.setItems(FXCollections.observableArrayList(DAOFactory.getEcoleDAO().getAll(1)));
        }


}
