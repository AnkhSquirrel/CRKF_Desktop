package fr.kyo.crkf.controller.ecole;

import fr.kyo.crkf.entity.Ecole;
import fr.kyo.crkf.tools.Pair;
import fr.kyo.crkf.entity.Personne;
import fr.kyo.crkf.controller.professeur.ProfesseurController;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    @FXML
    private TableColumn<Pair<Ecole, Double>,String> tarifColumn;
    private Personne personne;
    private ProfesseurController professeurController;

    @FXML
    private void initialize(){
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getFirst().getNomStringProperty());
        adresseColumn.setCellValueFactory(cellData -> cellData.getValue().getFirst().getEcoleAdresse().getAdresseStringProperty());
        villeColumn.setCellValueFactory(cellData -> cellData.getValue().getFirst().getEcoleAdresse().getVille().getVilleStringProperty());
        distanceColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getSecond().toString().concat(" km(s)")));
        tarifColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(String.valueOf(calculIndemnite(personne.getVehiculeCv(), cellData.getValue().getSecond())).concat(" €")));
    }

    public void setPersonne(Personne personne){
        this.personne = personne;
        fillInfos();
    }

    public void fillInfos(){
        ville.setText(personne.getAdresseId().getVille().getVilleLibelle());
        ecoleTable.setItems(FXCollections.observableArrayList(DAOFactory.getEcoleDAO().getBySmallestDistance(personne.getAdresseId().getVille().getLatitude(),
                                                                                                            personne.getAdresseId().getVille().getLongitude(),
                                                                                                            DAOFactory.getEcoleDAO().getByID(personne.getEcoleID()).getEcoleAdresse().getVille().getLatitude(),
                                                                                                            DAOFactory.getEcoleDAO().getByID(personne.getEcoleID()).getEcoleAdresse().getVille().getLongitude())));
    }

    @FXML
    private void closeEcoleAroundPage(){
        professeurController.closeEcoleAroundPage();
    }

    public void setProfesseurController(ProfesseurController professeurController){
        this.professeurController = professeurController;
    }

    private double calculIndemnite(int cv, double km){
        double indemnite = km;
        if (cv < 5)
            indemnite *= 0.08;
        else if (cv < 8)
            indemnite *= 0.10;
        else
            indemnite *= 0.06;
        return Math.round(indemnite * 100.00) / 100.00;
    }

}
