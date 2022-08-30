package fr.kyo.crkf.Controller;

import fr.kyo.crkf.Entity.Instrument;
import fr.kyo.crkf.Entity.Personne;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InstrumentController {
    @FXML
    private TableColumn<Instrument, String> libelle;
    @FXML
     TableView instrumentTable;

     @FXML
     private void initialize(){
         libelle.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
         instrumentTable.setItems(FXCollections.observableArrayList(DAOFactory.getInstrumentDAO().getAll()));
     }
}
