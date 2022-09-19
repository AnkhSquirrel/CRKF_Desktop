package fr.kyo.crkf.controller.professeur;

import fr.kyo.crkf.Entity.*;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

import java.util.ArrayList;

public class ProfesseurModalController {
    //Professeur
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField cv;
    //Adresse
    @FXML
    private TextField adresse;
    @FXML
    private SearchableComboBox<Departement> departement;
    @FXML
    private ComboBox<Ville> ville;
    @FXML
    private ComboBox<Ecole> ecole;
    private Stage modal;
    private boolean create;
    private ProfesseurController professeurController;
    private Filter filter;
    private Ville selectedVille;
    private Ecole selectedEcole;
    private DetailProfesseurController detailProfesseurController;
    private Personne professeur;

    @FXML
    private void initialize(){
        filter = new Filter();

        ecole.setEditable(true);
        ecole.getEditor().textProperty().addListener(observable -> ecoleFilter());
        ecole.getSelectionModel().selectedItemProperty().addListener(a -> selectEcole());
        ecole.setItems(FXCollections.observableArrayList(filter.getEcolesLike("")));

        selectedEcole = ecole.getItems().get(0);

        cv.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cv.setText(newValue.replaceAll("\\D", ""));
            }
        });

        ville.setEditable(true);
        ville.setItems(FXCollections.observableArrayList(filter.getVilleLike("", 0)));
        ville.getSelectionModel().select(0);
        ville.getSelectionModel().selectedItemProperty().addListener(a -> selectVille());
        ville.getEditor().textProperty().addListener(observable -> villeFilter());

        selectedVille = ville.getItems().get(0);

        departement.setItems(FXCollections.observableArrayList(filter.getDepartements()));
        departement.getSelectionModel().selectedItemProperty().addListener(observable -> filterDepartement());
        departement.getSelectionModel().select(0);
    }

    private void selectEcole() {
        if(ecole.getSelectionModel().getSelectedIndex() >= 1 && ecole.getSelectionModel().getSelectedItem() != null)
            selectedEcole = ecole.getSelectionModel().getSelectedItem();
    }

    private void selectVille() {
        if(ville.getSelectionModel().getSelectedIndex() >= 1 && ville.getSelectionModel().getSelectedItem() != null)
            selectedVille = ville.getSelectionModel().getSelectedItem();
    }

    private void villeFilter() {
        if(!ville.getEditor().getText().equals(selectedVille.getVille())){
            ville.setItems(FXCollections.observableArrayList(filter.getVilleLike(ville.getEditor().getText(),departement.getSelectionModel().getSelectedItem().getId_departement())));
        }
    }

    private void filterDepartement() {
        if(departement.getSelectionModel().getSelectedItem() != null){
            ville.setItems(FXCollections.observableArrayList(filter.getVilleLike("", departement.getSelectionModel().getSelectedItem().getId_departement())));
            ville.getSelectionModel().select(0);
        }
    }

    private void ecoleFilter() {
        if(ecole.getSelectionModel().getSelectedItem() == null || !ecole.getEditor().getText().equals(selectedEcole.getNom())){
            ecole.setItems(FXCollections.observableArrayList(filter.getEcolesLike(ecole.getEditor().getText())));
        }
    }

    @FXML
    void validate() {
        System.out.println("validate");
        boolean adresseComplete = selectedVille != null &&
                !adresse.getText().isEmpty();
        boolean professeurComplete =!nom.getText().isEmpty() &&
                !prenom.getText().isEmpty() && !cv.getText().isEmpty() &&
                selectedEcole != null;
        if(adresseComplete && professeurComplete){
            System.out.println("ok");
            if(create){
                createPersonne();
            } else
                updatePersonne();
        }
    }

    private void updatePersonne() {
        Adresse temp = new Adresse(professeur.getAdresse().getId_adresse(), adresse.getText(), selectedVille.getId_ville());
        if(DAOFactory.getAdresseDAO().update(temp)){
            ArrayList<Diplome> diplomes = professeur.getDiplomes();
            professeur = new Personne(professeur.getId_personne(),nom.getText(),prenom.getText(),Integer.parseInt(cv.getText()),temp.getId_adresse(),selectedEcole.getId_ecole());
            professeur.setDiplomes(diplomes);
            if(DAOFactory.getPersonneDAO().update(professeur)){
                professeurController.filter();
                professeurController.openDetailProfesseur(professeur);
                modal.close();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Il y a eu une erreur lors de la modification du professeur.\n Merci de vérifier que vous avez entrée des informations valides");
                alert.showAndWait();
            }
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une erreur lors de la modification de l'adresse.\n Merci de vérifier que vous avez entrée des informations valides");
            alert.showAndWait();
        }
    }

    private void createPersonne() {
        Adresse adresseObject = new Adresse(0, adresse.getText(),selectedVille.getId_ville());
        int adresseId = DAOFactory.getAdresseDAO().insert(adresseObject);
        if(adresseId != 0){
            Personne personne = new Personne(0,nom.getText(),prenom.getText(),Integer.parseInt(cv.getText()), adresseId,selectedEcole.getId_ecole());
            if(DAOFactory.getPersonneDAO().insert(personne) != 0){
                professeurController.filter();
                modal.close();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Il y a eu une erreur lors de la création du professeur.\n Merci de vérifier que vous avez entrée des informations valides");
                alert.showAndWait();
            }
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une erreur lors de la création de l'adresse.\n Merci de vérifier que vous avez entrée des informations valides");
            alert.showAndWait();
        }
    }

    @FXML
    void closeModal() {
        modal.close();
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    public void setCreate(boolean bool) {
        this.create = bool;
    }
    public void setProfesseur(Personne personne){
        this.professeur = personne;
        nom.setText(professeur.getNom());
        prenom.setText(professeur.getPrenom());
        cv.setText(String.valueOf(professeur.getVehiculeCv()));
        adresse.setText(professeur.getAdresse().getAdresse());
        departement.getSelectionModel().select(professeur.getAdresse().getVille().getDepartement());
        ville.getSelectionModel().select(professeur.getAdresse().getVille());
        ecole.getSelectionModel().select(professeur.getEcole());

        selectedEcole = professeur.getEcole();
        selectedVille = professeur.getAdresse().getVille();
    }

    public void setProfesseurController(ProfesseurController professeurController) {
        this.professeurController = professeurController;
    }
}

