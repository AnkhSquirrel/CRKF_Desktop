package fr.kyo.crkf.controller.ecole;

import com.jfoenix.controls.JFXDrawer;
import fr.kyo.crkf.dao.DAOFactory;
import fr.kyo.crkf.entity.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class FamilleEnseigneeController {
    @FXML
    private TableView<Personne> tabFamilleEnseignee;
    @FXML
    private TableColumn<Personne, String> familleColumn;
    @FXML
    private TableColumn<Personne, String> nomColumn;
    @FXML
    private TableColumn<Personne, String> instrumentColumn;
    @FXML
    private Label ecoleLabel;
    private EcoleController ecoleController;
    private Ecole ecole;

    @FXML
    private void initialize() {
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
        instrumentColumn.setCellValueFactory(cellData -> cellData.getValue().getDiplomes().get(0).getInstrument().getNomStringProperty());
        familleColumn.setCellValueFactory(cellData -> cellData.getValue().getDiplomes().get(0).getInstrument().getFamilles().get(0).getFamilleStringProperty());

    }

    public void setEcoleController(EcoleController ecoleController) {
        this.ecoleController = ecoleController;
    }

    public void setEcole(Ecole ecole) {
        this.ecole = ecole;
        ecoleLabel.setText("l' école : " + ecole.getEcoleNom());
        tabFamilleEnseignee.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getByEcole(ecole.getEcoleId())));
        for (int j = 0; j < FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getByEcole(ecole.getEcoleId())).size(); j++) {
            Personne personne = FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getByEcole(ecole.getEcoleId())).get(1);
            System.out.println("Personne : " + j);
            if (!personne.getDiplomes().isEmpty()) {
                for (int i = 0; i < personne.getDiplomes().size(); i++) {
                        Instrument instrument = personne.getDiplomes().get(i).getInstrument();
                        String famille = personne.getDiplomes().get(i).getInstrument().getFamilles().get(0).getfamille();
                        System.out.println(instrument);
                        System.out.println(famille);
                }
            }
        }
        
    }

    @FXML
    private void closeFamilleEnseigneePage(){
        ecoleController.closeEcoleAroundPage();
    }


}
