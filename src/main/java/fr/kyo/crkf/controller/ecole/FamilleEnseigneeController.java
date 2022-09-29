package fr.kyo.crkf.controller.ecole;

import com.jfoenix.controls.JFXDrawer;
import fr.kyo.crkf.dao.DAOFactory;
import fr.kyo.crkf.entity.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class FamilleEnseigneeController {
    @FXML
    private TableView<Personne> tabFamilleEnseignee;
    @FXML
    private TableColumn<Diplome, String> familleColumn;
    @FXML
    private TableColumn<Personne , String> nomColumn;
    @FXML
    private TableColumn<Diplome, String> instrumentColumn;
    private EcoleController ecoleController;
    private Ecole ecole;

    @FXML
    private void initialize(){
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
/*        familleColumn.setCellValueFactory(cellData -> (ObservableValue<String>) cellData.getValue().getCycle());
        instrumentColumn.setCellValueFactory(cellData -> (ObservableValue<String>) cellData.getValue().getInstrument());*/
    }

    public void setEcoleController(EcoleController ecoleController){
        this.ecoleController = ecoleController;
    }

    public void setEcole(Ecole ecole){
        this.ecole = ecole;
        tabFamilleEnseignee.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getByEcole(ecole.getEcoleId())));
        bouclePersonne(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getByEcole(ecole.getEcoleId())));
    }

    public void bouclePersonne (List<Personne> listPersonne){
        for (int i = 0; i < listPersonne.size(); i++) {
           List<Diplome> diplome = listPersonne.get(i).getDiplomes();
            System.out.println(diplome.size());
            System.out.println(diplome);
            System.out.println(i);
        }
    }
    @FXML
    private void closeFamilleEnseigneePage(){
        ecoleController.closeEcoleAroundPage();
    }


}
