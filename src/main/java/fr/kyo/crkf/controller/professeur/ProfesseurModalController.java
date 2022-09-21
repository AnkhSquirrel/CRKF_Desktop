package fr.kyo.crkf.controller.professeur;

import fr.kyo.crkf.entity.*;
import fr.kyo.crkf.searchable.Filter;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

import java.util.List;

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
        if(!ville.getEditor().getText().equals(selectedVille.getVilleLibelle())){
            ville.setItems(FXCollections.observableArrayList(filter.getVilleLike(ville.getEditor().getText(),departement.getSelectionModel().getSelectedItem().getDepartementId())));
        }
    }

    private void filterDepartement() {
        if(departement.getSelectionModel().getSelectedItem() != null){
            ville.setItems(FXCollections.observableArrayList(filter.getVilleLike("", departement.getSelectionModel().getSelectedItem().getDepartementId())));
            ville.getSelectionModel().select(0);
        }
    }

    private void ecoleFilter() {
        if(ecole.getSelectionModel().getSelectedItem() == null || !ecole.getEditor().getText().equals(selectedEcole.getEcoleNom())){
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
        Adresse temp = new Adresse(professeur.getAdresseId().getAdresseId(), adresse.getText(), selectedVille.getVilleId());
        if(DAOFactory.getAdresseDAO().update(temp)){
            List<Diplome> diplomes = professeur.getDiplomes();
            professeur = new Personne(professeur.getPersonneId(),nom.getText(),prenom.getText(),Integer.parseInt(cv.getText()),temp.getAdresseId(),selectedEcole.getEcoleId());
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
        Adresse adresseObject = new Adresse(0, adresse.getText(),selectedVille.getVilleId());
        int adresseId = DAOFactory.getAdresseDAO().insert(adresseObject);
        if(adresseId != 0){
            Personne personne = new Personne(0,nom.getText(),prenom.getText(),Integer.parseInt(cv.getText()), adresseId,selectedEcole.getEcoleId());
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
        nom.setText(professeur.getPersonneNom());
        prenom.setText(professeur.getPersonnePrenom());
        cv.setText(String.valueOf(professeur.getVehiculeCv()));
        adresse.setText(professeur.getAdresseId().getAdresseLibelle());
        departement.getSelectionModel().select(professeur.getAdresseId().getVille().getDepartement());
        ville.getSelectionModel().select(professeur.getAdresseId().getVille());
        ecole.getSelectionModel().select(professeur.getEcoleID());

        selectedEcole = professeur.getEcoleID();
        selectedVille = professeur.getAdresseId().getVille();
    }

    public void setProfesseurController(ProfesseurController professeurController) {
        this.professeurController = professeurController;
    }
}

