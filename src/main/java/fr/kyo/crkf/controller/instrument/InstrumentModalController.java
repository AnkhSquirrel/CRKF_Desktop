package fr.kyo.crkf.controller.instrument;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.Classification;
import fr.kyo.crkf.Entity.Famille;
import fr.kyo.crkf.Entity.Instrument;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class InstrumentModalController {
    @FXML
    private GridPane grid;
    @FXML
    private TextField nom;
    @FXML
    private Label nomModal;

    private HBox add;
    private Stage modal;
    private int rowsId;
    private int rowsCount;
    private Filter filter;
    private InstrumentController instrumentController;
    private boolean create;
    private Instrument instrumentUpdate;
    private ApplicationCRKF applicationCRKF;


    @FXML
    private void initialize(){
        filter = new Filter();
        rowsId = 0;
        rowsCount = 0;

        Button button = new Button();
        button.setText("+");
        button.setOnAction(a -> addRow());
        add = new HBox();
        add.setAlignment(Pos.CENTER);
        add.getChildren().setAll(button);
        add.setId("add");
        grid.addRow(rowsId,add);
    }

    @FXML
    private void addRow(){
        addFamilleChoice(null);
    }

    private void addFamilleChoice(Famille famille){
        ComboBox<Famille> comboBox = new ComboBox<>();
        boolean insert = setComboboxItem(comboBox);
        if(insert && rowsCount < 5){
            if(famille != null)
                comboBox.getSelectionModel().select(famille);

            Label label = new Label();
            label.setText("Famille : ");

            Button button = new Button();
            int id = rowsId;
            button.setOnAction(a -> removeRow(id));
            button.setText("Delete");

            HBox hBox = new HBox();
            hBox.getChildren().setAll(label,comboBox,button);
            hBox.setAlignment(Pos.CENTER);
            hBox.setMinHeight(80);
            hBox.setMaxHeight(80);
            hBox.setId(String.valueOf(id));

            grid.getChildren().remove(add);
            grid.addRow(rowsId, hBox);
            rowsId++;
            rowsCount++;
            if(rowsCount < 5)
                grid.addRow(rowsId + 1,add);
        }

    }

    private boolean setComboboxItem(ComboBox<Famille> comboBox) {
        if(grid.getChildren().get(0) == add){
            comboBox.setItems(FXCollections.observableArrayList(filter.getFamilles()));
            comboBox.getSelectionModel().select(0);
            return true;
        }else{
            HBox hBox = (HBox) grid.getChildren().get(0);
            ComboBox<Famille> comboBoxTemp = (ComboBox<Famille>) hBox.getChildren().get(1);
            if(comboBoxTemp.getSelectionModel().getSelectedItem().getId_famille() != 0){
                ArrayList<Famille> familles = DAOFactory.getFamilleDAO().getByClassification(comboBoxTemp.getSelectionModel().getSelectedItem().getclassification().getId_classification());
                familles.add(0,new Famille(0,"Famille",new Classification(0,"")));
                comboBox.setItems(FXCollections.observableArrayList(familles));
                comboBox.getSelectionModel().select(0);
                comboBoxTemp.setDisable(true);
                return true;
            }
        }
        return false;
    }

    private void removeRow(int row) {

        for(int i = 0; i < grid.getChildren().size(); i++){
            Node node = grid.getChildren().get(i);
            HBox hBox = (HBox) node;
            if((node != grid.getChildren().get(0) && node.getId().equals(String.valueOf(row))) || (node == grid.getChildren().get(0) && rowsCount == 1)){
                grid.getChildren().remove(hBox);
                rowsCount--;
                if(rowsCount == 1){
                    HBox temp = (HBox) grid.getChildren().get(0);
                    ComboBox<Famille> comboBox = (ComboBox<Famille>) temp.getChildren().get(1);
                    comboBox.setDisable(false);
                }
                if (rowsCount == 4){
                    grid.addRow(rowsId + 1,add);
                }

            }
        }
    }

    @FXML
    private void validate(){
        if (create)
            addInstrument();
        else
            updateInstrument();
    }

    private void updateInstrument() {
        instrumentUpdate.getFamilles().clear();
        boolean allFamilleSet = getAllFamille(instrumentUpdate);
        if(!nom.getText().equals(""))
            instrumentUpdate.setNom(nom.getText());
        if(allFamilleSet && !instrumentUpdate.getFamilles().isEmpty()){
            if(DAOFactory.getInstrumentDAO().update(instrumentUpdate)){
                applicationCRKF.openDetailInstrument(instrumentUpdate);
                closeModal();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une érreur lors de la modification de l'instrument.\n Merci de vérifier que vos avez entrer des informations valide");
            alert.showAndWait();
        }
    }

    private void addInstrument(){
        Instrument instrument = new Instrument(0,nom.getText());
        boolean allFamilleSet = getAllFamille(instrument);
        if(!instrument.getNom().equals("") && allFamilleSet && !instrument.getFamilles().isEmpty()){
            if(DAOFactory.getInstrumentDAO().insert(instrument) != 0){
                instrumentController.filter();
                closeModal();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une érreur lors de la création de l'instrument.\n Merci de vérifier que vos avez entrer des informations valide");
            alert.showAndWait();
        }
    }

    private boolean getAllFamille(Instrument instrument) {
        boolean allFamilleSet = true;
        for(Node node : grid.getChildren()){
            HBox hBox = (HBox) node;
            if(!hBox.getId().equals("add")){
                ComboBox<Famille> comboBox = (ComboBox<Famille>) hBox.getChildren().get(1);
                if(comboBox.getSelectionModel().getSelectedItem().getId_famille() != 0){
                    instrument.addFamille(comboBox.getSelectionModel().getSelectedItem());
                }else{
                    allFamilleSet = false;
                }
            }
        }
        return  allFamilleSet;
    }

    @FXML
    private void closeModal(){
        modal.close();
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    public void setInstrumentController(InstrumentController instrumentController) {
        this.instrumentController = instrumentController;
    }
    public void setCreate(boolean bool){
        create = bool;
        if(!create)
            nomModal.setText("Modifier Instrument");

    }
    public void setInstrumentUpdate(Instrument instrument){
        instrumentUpdate = instrument;
        nom.setText(instrumentUpdate.getNom());
        for(Famille famille : instrumentUpdate.getFamilles()){
            addFamilleChoice(famille);
        }
    }

    public void setApplicationCRKF(ApplicationCRKF applicationCRKF) {
        this.applicationCRKF = applicationCRKF;
    }
}
