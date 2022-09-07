package fr.kyo.crkf.controller;

import fr.kyo.crkf.Entity.Famille;
import fr.kyo.crkf.Entity.Instrument;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CreateInstrumentModalController {
    @FXML
    private GridPane grid;
    @FXML
    private TextField nom;

    private HBox add;
    private Stage modal;
    private int rowsCount;
    private Filter filter;


    @FXML
    private void initialize(){
        filter = new Filter();
        rowsCount = 0;

        Button button = new Button();
        button.setText("+");
        button.setOnAction(a -> addRow());
        add = new HBox();
        add.setAlignment(Pos.CENTER);
        add.getChildren().setAll(button);
        add.setId("add");
        grid.addRow(rowsCount,add);
    }

    @FXML
    private void addRow(){
        Label label = new Label();
        label.setText("Famille Instance: ");

        ComboBox<Famille> comboBox = new ComboBox<>();
        comboBox.setItems(FXCollections.observableArrayList(filter.getFamilles()));
        comboBox.getSelectionModel().select(0);

        Button button = new Button();
        int id = rowsCount;
        button.setOnAction(a -> removeRow(id));
        button.setText("Delete");

        HBox hBox = new HBox();
        hBox.getChildren().setAll(label,comboBox,button);
        hBox.setAlignment(Pos.CENTER);
        hBox.setMinHeight(80);
        hBox.setMaxHeight(80);
        hBox.setId(String.valueOf(id));

        grid.getChildren().remove(add);
        grid.addRow(rowsCount + 1,add);
        grid.addRow(rowsCount, hBox);

        rowsCount++;

    }

    private void removeRow(int row) {
        for(int i = 0; i < grid.getChildren().size(); i++){
            Node node = grid.getChildren().get(i);
            HBox hBox = (HBox) node;
            if(hBox.getId() != null && hBox.getId().equals(String.valueOf(row))){
                grid.getChildren().remove(hBox);
            }
        }
    }

    @FXML
    private void addInstrument(){
        Instrument instrument = new Instrument(0,nom.getText());
        for(Node node : grid.getChildren()){
            HBox hBox = (HBox) node;
            if(!hBox.getId().equals("add")){
                ComboBox<Famille> comboBox = (ComboBox<Famille>) hBox.getChildren().get(1);
                if(comboBox.getSelectionModel().getSelectedItem().getId_famille() != 0){
                    instrument.addFamille(comboBox.getSelectionModel().getSelectedItem());
                    System.out.println(comboBox.getSelectionModel().getSelectedItem());
                }

            }
        }
        if(!instrument.getNom().equals("")){
            if(DAOFactory.getInstrumentDAO().insert(instrument) != 0){
                closeModal();
            }
        }else{
            System.out.println("Erreur");
        }
    }
    @FXML
    private void closeModal(){
        modal.close();
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }
}
