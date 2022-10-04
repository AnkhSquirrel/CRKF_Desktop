package fr.kyo.crkf.controller.ecole;

import fr.kyo.crkf.dao.DAOFactory;
import fr.kyo.crkf.entity.*;
import fr.kyo.crkf.tools.Pair;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class FamilleEnseigneeController {
    @FXML
    private TableView<Pair<Personne,Instrument>> tabFamilleEnseignee;
    @FXML
    private TableColumn<Pair<Personne,Instrument>, String> familleColumn;
    @FXML
    private TableColumn<Pair<Personne,Instrument>, String> nomColumn;
    @FXML
    private TableColumn<Pair<Personne,Instrument>, String> instrumentColumn;
    @FXML
    private Label ecoleLabel;
    private EcoleController ecoleController;

    @FXML
    private void initialize() {
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getFirst().getNomStringProperty());
        instrumentColumn.setCellValueFactory(cellData -> cellData.getValue().getSecond().getNomStringProperty());
        familleColumn.setCellValueFactory(cellData -> {
            ObservableValue<String> text = new SimpleStringProperty();
            String temp = "";
            for(Famille famille : cellData.getValue().getSecond().getFamilles()){
                if(famille.getFamilleId() == cellData.getValue().getSecond().getFamilles().get(0).getFamilleId())
                    temp = famille.getfamille();
                else
                    temp += ", " + famille.getfamille();
                text = new SimpleStringProperty(temp);
            }
            return text;
        });
    }

    public void setEcoleController(EcoleController ecoleController) {
        this.ecoleController = ecoleController;
    }

    public void setEcole(Ecole ecole) {
        ecoleLabel.setText("l' école : " + ecole.getEcoleNom());
        List<Personne> personneList = DAOFactory.getPersonneDAO().getByEcole(ecole.getEcoleId());
        tabFamilleEnseignee.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getInstrumentEnseignerByPersonnes(personneList)));
    }

    @FXML
    private void closeFamilleEnseigneePage(){
        ecoleController.closeEcoleAroundPage();
    }


}
