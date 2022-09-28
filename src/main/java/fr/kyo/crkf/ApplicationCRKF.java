package fr.kyo.crkf;

import fr.kyo.crkf.entity.*;
import fr.kyo.crkf.controller.*;
import fr.kyo.crkf.controller.classification.ClassificationModalController;
import fr.kyo.crkf.controller.classification.GestionClassificationController;
import fr.kyo.crkf.controller.cycle.CycleModalController;
import fr.kyo.crkf.controller.cycle.GestionCycleController;
import fr.kyo.crkf.controller.departement.DepartementModalController;
import fr.kyo.crkf.controller.departement.GestionDepartementController;
import fr.kyo.crkf.controller.diplome.DiplomeModalController;
import fr.kyo.crkf.controller.diplome.GestionDiplomeController;
import fr.kyo.crkf.controller.ecole.EcoleController;
import fr.kyo.crkf.controller.ecole.EcoleModalController;
import fr.kyo.crkf.controller.famille.FamilleModalController;
import fr.kyo.crkf.controller.famille.GestionFamilleController;
import fr.kyo.crkf.controller.instrument.InstrumentModalController;
import fr.kyo.crkf.controller.instrument.InstrumentController;
import fr.kyo.crkf.controller.professeur.ProfesseurController;
import fr.kyo.crkf.controller.professeur.ProfesseurModalController;
import fr.kyo.crkf.controller.ville.GestionVilleController;
import fr.kyo.crkf.controller.ville.VilleModalController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class ApplicationCRKF extends javafx.application.Application {

    private NavbarController navbarController;
    private AccueilController accueilController;
    private BorderPane mainWindow = new BorderPane();
    private Stage stage;
    private Boolean lightMode;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        lightMode = true;
        this.stage = stage;
        try {
            FXMLLoader fxmlLoaderNavbar = new FXMLLoader();
            fxmlLoaderNavbar.setLocation(ApplicationCRKF.class.getResource("navbar.fxml"));
            MenuBar navbar = fxmlLoaderNavbar.load();
            navbarController = fxmlLoaderNavbar.getController();
            navbarController.setMainApp(this);

            openMainMenu();
            mainWindow.setTop(navbar);

            Scene scene = new Scene(mainWindow);
            scene.getStylesheets().add(getStylesheets());
            accueilController.initialize(scene);

            stage.setMinWidth(760);
            stage.setMinHeight(620);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationCRKF.class.getResourceAsStream("icon.png"))));
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

            accueilController.setPositionToggleSwitch();
            if(stage.getScene() != null)
                accueilController.initialize(stage.getScene());

            mainWindow.setCenter(accueil);
            setTitle("CRKF - Accueil");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openProfesseurList() {
        try {
            FXMLLoader fxmlLoaderListeProfesseur = new FXMLLoader();
            fxmlLoaderListeProfesseur.setLocation(ApplicationCRKF.class.getResource("liste_professeur.fxml"));
            AnchorPane listeProfesseur = fxmlLoaderListeProfesseur.load();
            ProfesseurController professeurController = fxmlLoaderListeProfesseur.getController();
            professeurController.setApplicationCRKF(this);
            mainWindow.setCenter(listeProfesseur);
            setTitle("CRKF - Gestion des professeurs");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openEcoleList() {
        try {
            FXMLLoader fxmlLoaderListeEcole = new FXMLLoader();
            fxmlLoaderListeEcole.setLocation(ApplicationCRKF.class.getResource("liste_ecole.fxml"));
            AnchorPane listeEcole = fxmlLoaderListeEcole.load();
            EcoleController ecoleController = fxmlLoaderListeEcole.getController();
            ecoleController.setApplicationCRKF(this);
            mainWindow.setCenter(listeEcole);
            setTitle("CRKF - Gestion des écoles");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openInstrumentList() {
        try {
            FXMLLoader fxmlLoaderListeInstrument = new FXMLLoader();
            fxmlLoaderListeInstrument.setLocation(ApplicationCRKF.class.getResource("liste_instrument.fxml"));
            AnchorPane listeInstrument = fxmlLoaderListeInstrument.load();
            InstrumentController instrumentController = fxmlLoaderListeInstrument.getController();
            instrumentController.setApplicationCRKF(this);
            mainWindow.setCenter(listeInstrument);
            setTitle("CRKF - Gestion des instruments");
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


    public void openCreateInstrumentModal(InstrumentController instrumentController) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_instrument.fxml"));
            VBox modalPane = fxmlLoader.load();
            InstrumentModalController instrumentModalController = fxmlLoader.getController();

            instrumentModalController.setModal(modal);
            instrumentModalController.setCreate(true);
            instrumentModalController.setInstrumentController(instrumentController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Ajouter un instrument");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCreateEcoleModal(EcoleController ecoleController){
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_ecole.fxml"));
            VBox modalPane = fxmlLoader.load();
            EcoleModalController ecoleModalController = fxmlLoader.getController();

            ecoleModalController.setModal(modal);
            ecoleModalController.setCreate(true);
            ecoleModalController.setEcoleController(ecoleController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Ajout d'une école");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());


            modal.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void openUpdateEcole(Ecole ecole, EcoleController ecoleController) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_ecole.fxml"));
            VBox modalPane = fxmlLoader.load();
            EcoleModalController createEcoleModalController = fxmlLoader.getController();

            createEcoleModalController.setModal(modal);
            createEcoleModalController.setCreate(false);
            createEcoleModalController.setEcole(ecole);
            createEcoleModalController.setEcoleController(ecoleController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Modification d'une école");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void openUpdateInstrumentModal(Instrument instrument, InstrumentController instrumentController) {
        Stage modal = new Stage();
        modal.setResizable(true);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_instrument.fxml"));
            VBox modalPane = fxmlLoader.load();
            InstrumentModalController instrumentModalController = fxmlLoader.getController();

            instrumentModalController.setModal(modal);
            instrumentModalController.setCreate(false);
            instrumentModalController.setInstrumentUpdate(instrument);
            instrumentModalController.setInstrumentController(instrumentController);

            modal.setScene(new Scene(modalPane));
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Modifier un instrument");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteModal() {
        boolean delete = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet element?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
            delete = true;
        return delete;
    }

    public void openVilleGestion(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ApplicationCRKF.class.getResource("gestion_ville.fxml"));
            GridPane gestionVille = fxmlLoader.load();
            GestionVilleController gestionVilleController = fxmlLoader.getController();
            gestionVilleController.setApplicationCRKF(this);
            mainWindow.setCenter(gestionVille);
            setTitle("CRKF - Gestion des villes");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openFamilleGestion() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ApplicationCRKF.class.getResource("gestion_famille.fxml"));
            GridPane gestionFamille = fxmlLoader.load();
            GestionFamilleController gestionFamilleController = fxmlLoader.getController();
            gestionFamilleController.setApplicationCRKF(this);
            mainWindow.setCenter(gestionFamille);
            setTitle("CRKF - Gestion des familles");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openModalCreateFamille(GestionFamilleController gestionFamilleController) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_famille.fxml"));
            VBox modalPane = fxmlLoader.load();
            FamilleModalController familleModalController = fxmlLoader.getController();

            familleModalController.setModal(modal);
            familleModalController.setCreate(true);
            familleModalController.setGestionFamilleController(gestionFamilleController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Ajouter une famille");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openModalUpdateFamille(GestionFamilleController gestionFamilleController, Famille famille) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_famille.fxml"));
            VBox modalPane = fxmlLoader.load();
            FamilleModalController familleModalController = fxmlLoader.getController();

            familleModalController.setModal(modal);
            familleModalController.setCreate(false);
            familleModalController.setGestionFamilleController(gestionFamilleController);
            familleModalController.setFamille(famille);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Modifier une famille");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDepartementGestion(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ApplicationCRKF.class.getResource("gestion_departement.fxml"));
            GridPane gestionDepartement = fxmlLoader.load();
            GestionDepartementController gestionDepartementController = fxmlLoader.getController();
            gestionDepartementController.setApplicationCRKF(this);
            setTitle("CRKF - Gestion des départements");

            mainWindow.setCenter(gestionDepartement);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openModalUpdateDepartement(GestionDepartementController gestionDepartementController, Departement departement) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_departement.fxml"));
            VBox modalPane = fxmlLoader.load();
            DepartementModalController departementModalController = fxmlLoader.getController();

            departementModalController.setModal(modal);
            departementModalController.setCreate(false);
            departementModalController.setGestionDepartementController(gestionDepartementController);
            departementModalController.setDepartement(departement);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Modifier un département");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openModalCreateDepartement(GestionDepartementController gestionDepartementController) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_departement.fxml"));
            VBox modalPane = fxmlLoader.load();
            DepartementModalController departementModalController = fxmlLoader.getController();

            departementModalController.setModal(modal);
            departementModalController.setCreate(true);
            departementModalController.setGestionDepartementController(gestionDepartementController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Créer un département");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openModalCreateVille(GestionVilleController gestionVilleController) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_ville.fxml"));
            VBox modalPane = fxmlLoader.load();
            VilleModalController villeModalController = fxmlLoader.getController();

            villeModalController.setModal(modal);
            villeModalController.setCreate(true);
            villeModalController.setGestionVilleController(gestionVilleController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Créer une ville");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openModalUpdateVille(GestionVilleController gestionVilleController, Ville ville) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_ville.fxml"));
            VBox modalPane = fxmlLoader.load();
            VilleModalController villeModalController = fxmlLoader.getController();

            villeModalController.setModal(modal);
            villeModalController.setCreate(false);
            villeModalController.setGestionVilleController(gestionVilleController);
            villeModalController.setVille(ville);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Modifier une ville");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openClassificationGestion() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ApplicationCRKF.class.getResource("gestion_classification.fxml"));
            GridPane gestionClassification = fxmlLoader.load();
            GestionClassificationController gestionClassificationController = fxmlLoader.getController();
            gestionClassificationController.setApplicationCRKF(this);
            setTitle("CRKF - Gestion des classifications");

            mainWindow.setCenter(gestionClassification);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCreateClassificationModal(GestionClassificationController gestionClassificationController) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_classification.fxml"));
            VBox modalPane = fxmlLoader.load();
            ClassificationModalController classificationModalController = fxmlLoader.getController();

            classificationModalController.setModal(modal);
            classificationModalController.setCreate(true);
            classificationModalController.setGestionClassificationController(gestionClassificationController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Ajouter une Classification");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openUpdateClassificationModal(GestionClassificationController gestionClassificationController, Classification classification) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_classification.fxml"));
            VBox modalPane = fxmlLoader.load();
            ClassificationModalController classificationModalController = fxmlLoader.getController();

            classificationModalController.setModal(modal);
            classificationModalController.setCreate(false);
            classificationModalController.setClassification(classification);
            classificationModalController.setGestionClassificationController(gestionClassificationController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Modifier une Classification");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCreateProfesseurModal(ProfesseurController professeurController) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_professeur.fxml"));
            VBox modalPane = fxmlLoader.load();
            ProfesseurModalController professeurModalController = fxmlLoader.getController();

            professeurModalController.setModal(modal);
            professeurModalController.setCreate(true);
            professeurModalController.setProfesseurController(professeurController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Ajouter un Professeur");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openUpdateProfesseurModal(ProfesseurController professeurController, Personne personne) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_professeur.fxml"));
            VBox modalPane = fxmlLoader.load();
            ProfesseurModalController professeurModalController = fxmlLoader.getController();

            professeurModalController.setModal(modal);
            professeurModalController.setCreate(false);
            professeurModalController.setProfesseurController(professeurController);
            professeurModalController.setProfesseur(personne);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Modifier un Professeur");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAddDiplomeModal(GestionDiplomeController gestionDiplomeController, Personne personne) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_diplome.fxml"));
            VBox modalPane = fxmlLoader.load();
            DiplomeModalController diplomeModalController = fxmlLoader.getController();

            diplomeModalController.setModal(modal);
            diplomeModalController.setCreate(true);
            diplomeModalController.setPersonne(personne);
            diplomeModalController.setGestionDiplomeController(gestionDiplomeController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Ajout d'un diplome");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openCreateCycleModal(GestionCycleController gestionCycleController) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_cycle.fxml"));
            VBox modalPane = fxmlLoader.load();
            CycleModalController cycleModalController = fxmlLoader.getController();

            cycleModalController.setModal(modal);
            cycleModalController.setCreate(true);
            cycleModalController.setGestionCycleController(gestionCycleController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Ajouter un cycle");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openUpdateCycleModal(GestionCycleController gestionCycleController, Cycle cycle) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_cycle.fxml"));
            VBox modalPane = fxmlLoader.load();
            CycleModalController cycleModalController = fxmlLoader.getController();

            cycleModalController.setModal(modal);
            cycleModalController.setCreate(false);
            cycleModalController.setCycle(cycle);
            cycleModalController.setGestionCycleController(gestionCycleController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Modifier un cycle");

            if(modal.getScene() != null)
                modal.getScene().getStylesheets().add(getStylesheets());

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCycleGestion() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ApplicationCRKF.class.getResource("gestion_cycle.fxml"));
            GridPane gestionCycle = fxmlLoader.load();
            GestionCycleController gestionCycleController = fxmlLoader.getController();
            gestionCycleController.setApplicationCRKF(this);
            mainWindow.setCenter(gestionCycle);
            setTitle("CRKF - Gestion des cycles");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTitle(String title){
        stage.setTitle(title);
    }

    public Boolean getLightMode() {
        return lightMode;
    }

    public void setLightMode(Boolean lightMode) {
        this.lightMode = lightMode;
    }

    public String getStylesheets(){
        if(lightMode){
            return getClass().getResource("/fr/kyo/crkf/lightMode.css").toExternalForm();
        }
        else{
            return getClass().getResource("/fr/kyo/crkf/darkMode.css").toExternalForm();
        }
    }
}