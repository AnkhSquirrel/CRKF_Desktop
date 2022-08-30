package fr.kyo.crkf.Controller;

import fr.kyo.crkf.Entity.Instrument;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InstrumentController {
    @FXML
    private TableColumn<Instrument, String> libelle;
    @FXML
    private TableColumn<Instrument, String> classification;
    @FXML
    TableView<Object> instrumentTable;

     @FXML
     private void initialize(){
         libelle.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
         classification.setCellValueFactory(cellData ->cellData.getValue().getFamilles().get(0).getFamilleStringProperty());
         instrumentTable.setItems(FXCollections.observableArrayList(DAOFactory.getInstrumentDAO().getAll()));
     }
}
