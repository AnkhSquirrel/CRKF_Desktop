package fr.kyo.crkf;


import fr.kyo.crkf.controller.*;
import fr.kyo.crkf.Entity.Ecole;

import fr.kyo.crkf.Entity.Instrument;
import fr.kyo.crkf.Entity.Personne;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationCRKF extends javafx.application.Application {

    NavbarController navbarController;
    AccueilController accueilController;
    BorderPane mainWindow = new BorderPane();

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoaderNavbar = new FXMLLoader();
            fxmlLoaderNavbar.setLocation(ApplicationCRKF.class.getResource("navbar.fxml"));
            MenuBar navbar = fxmlLoaderNavbar.load();
            navbarController = fxmlLoaderNavbar.getController();
            navbarController.setMainApp(this);

            openMainMenu();

            mainWindow.setTop(navbar);

            Scene scene = new Scene(mainWindow);

            stage.setTitle("CRKF");
            stage.setMinWidth(840);
            stage.setMinHeight(620);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openMainMenu(){
        try{
            FXMLLoader fxmlLoaderAccueil = new FXMLLoader();
            fxmlLoaderAccueil.setLocation(ApplicationCRKF.class.getResource("accueil.fxml"));
            VBox accueil = fxmlLoaderAccueil.load();
            accueilController = fxmlLoaderAccueil.getController();
            accueilController.setMainApp(this);

            mainWindow.setCenter(accueil);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openProfesseurList() {
        try {
            FXMLLoader fxmlLoaderListeProfesseur = new FXMLLoader();
            fxmlLoaderListeProfesseur.setLocation(ApplicationCRKF.class.getResource("liste_professeur.fxml"));
            GridPane listeProfesseur = fxmlLoaderListeProfesseur.load();
            ProfesseurController professeurController = fxmlLoaderListeProfesseur.getController();
            professeurController.setApplicationCRKF(this);

            mainWindow.setCenter(listeProfesseur);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openEcoleList() {
        try {
            FXMLLoader fxmlLoaderListeEcole = new FXMLLoader();
            fxmlLoaderListeEcole.setLocation(ApplicationCRKF.class.getResource("liste_ecole.fxml"));
            GridPane listeEcole = fxmlLoaderListeEcole.load();
            EcoleController ecoleController = fxmlLoaderListeEcole.getController();
            ecoleController.setApplicationCRKF(this);

            mainWindow.setCenter(listeEcole);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openInstrumentList() {
        try {
            FXMLLoader fxmlLoaderListeInstrument = new FXMLLoader();
            fxmlLoaderListeInstrument.setLocation(ApplicationCRKF.class.getResource("liste_instrument.fxml"));
            GridPane listeInstrument = fxmlLoaderListeInstrument.load();
            InstrumentController instrumentController = fxmlLoaderListeInstrument.getController();
            instrumentController.setApplicationCRKF(this);

            mainWindow.setCenter(listeInstrument);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAboutModal(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("A Propos");
        alert.setHeaderText("CRKF est un outil de gestion pour conservatoire");
        alert.setContentText("K.Y.O");
        alert.showAndWait();
    }
    public void openDetailInstrument(Instrument instrument){
        try {
            FXMLLoader fxmlLoaderListeInstrument = new FXMLLoader();
            fxmlLoaderListeInstrument.setLocation(ApplicationCRKF.class.getResource("detail_instrument.fxml"));
            AnchorPane detailInstrument = fxmlLoaderListeInstrument.load();
            DetailInstrumentController detailInstrumentController = fxmlLoaderListeInstrument.getController();
            detailInstrumentController.setApplicationCRKF(this);
            detailInstrumentController.setInstrument(instrument);

            mainWindow.setCenter(detailInstrument);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openDetailProfesseur(Personne personne){
        try {
            FXMLLoader fxmlLoaderListeInstrument = new FXMLLoader();
            fxmlLoaderListeInstrument.setLocation(ApplicationCRKF.class.getResource("detail_professeur.fxml"));
            VBox detailProfesseur = fxmlLoaderListeInstrument.load();
            DetailProfesseurController detailProfesseurController = fxmlLoaderListeInstrument.getController();
            detailProfesseurController.setApplicationCRKF(this);
            detailProfesseurController.setPersonne(personne);

            mainWindow.setCenter(detailProfesseur);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openDetailEcole(Ecole ecole){
        try {
            FXMLLoader fxmlLoaderListeEcole = new FXMLLoader();
            fxmlLoaderListeEcole.setLocation(ApplicationCRKF.class.getResource("detail_ecole.fxml"));
            VBox detailEcole = fxmlLoaderListeEcole.load();
            DetailEcoleController detailEcoleController = fxmlLoaderListeEcole.getController();
            detailEcoleController.setApplicationCRKF(this);
            detailEcoleController.setEcole(ecole);

            mainWindow.setCenter(detailEcole);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openEcoleAroundPage(Personne personne){
        try {
            FXMLLoader fxmlLoaderEcoleAroundPage = new FXMLLoader();
            fxmlLoaderEcoleAroundPage.setLocation(ApplicationCRKF.class.getResource("ecole_around_page.fxml"));
            VBox ecoleAroundProf = fxmlLoaderEcoleAroundPage.load();
            EcoleAroundProfesseurController ecoleAroundProfesseurController = fxmlLoaderEcoleAroundPage.getController();
            ecoleAroundProfesseurController.setApplicationCRKF(this);
            ecoleAroundProfesseurController.setPersonne(personne);
            mainWindow.setCenter(ecoleAroundProf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public void openCreateInstrumentModal() {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_instrument.fxml"));
            AnchorPane modalPane = fxmlLoader.load();
            CreateInstrumentModalController createInstrumentModalController = fxmlLoader.getController();

            createInstrumentModalController.setModal(modal);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Ajouter un instrument");

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}