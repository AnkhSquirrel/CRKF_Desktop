package fr.kyo.crkf;

import fr.kyo.crkf.Controller.AccueilController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationCRKF extends javafx.application.Application {

    AccueilController accueilController;
    BorderPane mainWindow = new BorderPane();

    @Override
    public void start(Stage stage) throws IOException {

        try {
            FXMLLoader fxmlLoaderNavbar = new FXMLLoader();
            fxmlLoaderNavbar.setLocation(ApplicationCRKF.class.getResource("navbar.fxml"));
            MenuBar navbar = fxmlLoaderNavbar.load();

            FXMLLoader fxmlLoaderAccueil = new FXMLLoader();
            fxmlLoaderAccueil.setLocation(ApplicationCRKF.class.getResource("accueil.fxml"));
            VBox accueil = fxmlLoaderAccueil.load();

            accueilController = fxmlLoaderAccueil.getController();
            accueilController.setMainApp(this);

            mainWindow.setTop(navbar);
            mainWindow.setCenter(accueil);

            Scene scene = new Scene(mainWindow, 640, 480);

            stage.setTitle("CRKF");
            stage.setMinWidth(640);
            stage.setMinHeight(480);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openProfesseurList() {
        try {
            FXMLLoader fxmlLoaderListeProfesseur = new FXMLLoader();
            fxmlLoaderListeProfesseur.setLocation(ApplicationCRKF.class.getResource("liste_professeur.fxml"));
            GridPane listeProfesseur = fxmlLoaderListeProfesseur.load();

            accueilController.setMainApp(this);

            mainWindow.setCenter(listeProfesseur);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}