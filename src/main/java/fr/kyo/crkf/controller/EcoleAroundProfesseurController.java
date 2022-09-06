package fr.kyo.crkf.controller;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.Ecole;
import fr.kyo.crkf.Tools.Pair;
import fr.kyo.crkf.Entity.Personne;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class EcoleAroundProfesseurController {

    @FXML
    private Label ville;
    @FXML
    private TableView<Pair<Ecole, Double>> ecoleTable;
    @FXML
    private TableColumn<Pair<Ecole, Double>,String> nomColumn;
    @FXML
    private TableColumn<Pair<Ecole, Double>,String> adresseColumn;
    @FXML
    private TableColumn<Pair<Ecole, Double>,String> villeColumn;
    @FXML
    private TableColumn<Pair<Ecole, Double>,String> distanceColumn;
    private Personne personne;
    private ApplicationCRKF applicationCRKF;
    private ArrayList<Pair<Ecole, Double>> ecolesEtDistance;

    @FXML
    private void initialize(){
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getFirst().getNomStringProperty());
        adresseColumn.setCellValueFactory(cellData -> cellData.getValue().getFirst().getAdresse().getAdresseStringProperty());
        villeColumn.setCellValueFactory(cellData -> cellData.getValue().getFirst().getAdresse().getVille().getVilleStringProperty());
        distanceColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getSecond().toString()));
    }

    public void setApplicationCRKF(ApplicationCRKF applicationCRKF){
        this.applicationCRKF = applicationCRKF;
    }

    public void setPersonne(Personne personne){
        this.personne = personne;
        fillInfos();
    }

    public void fillInfos(){
        ville.setText(personne.getAdresse().getVille().getVille());
        ecoleTable.setItems(FXCollections.observableArrayList(DAOFactory.getEcoleDAO().getByDistance(personne.getAdresse().getVille().getLatitude(), personne.getAdresse().getVille().getLongitude(), 1)));
    }

}
