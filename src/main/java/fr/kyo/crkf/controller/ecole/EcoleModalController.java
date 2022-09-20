package fr.kyo.crkf.controller.ecole;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.Adresse;
import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ecole;
import fr.kyo.crkf.Entity.Ville;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.Searchable.SearchableEcole;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

public class EcoleModalController {
    private Stage modal;
    @FXML
    private TextField nomEcole;
    @FXML
    private TextField libeleAdresse;
    @FXML
    private SearchableComboBox<Ville> ville;
    @FXML
    private SearchableComboBox<Departement> nomDepartement;
    @FXML
    private Label nomModal;
    private SearchableEcole searchableEcole;
    private Ecole ecoleUpdate;
    private boolean create;
    private Filter filter;
    private EcoleController ecoleController;


    @FXML
    private void initialize(){
        filter = new Filter();

        searchableEcole = new SearchableEcole();

        nomDepartement.setItems(FXCollections.observableArrayList(filter.getDepartements()));
        nomDepartement.getSelectionModel().selectedItemProperty().addListener(observable -> filterDepartement());
        nomDepartement.getSelectionModel().select(0);

        ville.getSelectionModel().selectedItemProperty().addListener(observable -> filter());
        ville.setItems(FXCollections.observableArrayList(filter.getVilles()));
        ville.getSelectionModel().select(0);
    }
    @FXML
    private void addEcole(){
        Ecole ecole = new Ecole(0, nomEcole.getText(), 0);

        if(!ecole.getNom().equals("") && !libeleAdresse.getText().equals("") && ville.getSelectionModel().getSelectedItem().getId_ville() != 0 && nomDepartement.getSelectionModel().getSelectedItem().getId_departement() != 0){
            Adresse adresse = new Adresse(0, libeleAdresse.getText(), ville.getSelectionModel().getSelectedItem().getId_ville());
            adresse.setId_adresse(DAOFactory.getAdresseDAO().insert(adresse));
            ecole.setAdresse(adresse);
            DAOFactory.getEcoleDAO().insert(ecole);
            closeModal();
            ecoleController.filter();
        }
        else{
            Alert alertErrorInsert = new Alert(Alert.AlertType.ERROR);
            alertErrorInsert.setTitle("Erreur");
            alertErrorInsert.setHeaderText("Erreur! Mauvaise(s) donnée(s)");
            alertErrorInsert.showAndWait().ifPresent(btnTypeError -> {
                if (btnTypeError == ButtonType.OK) {
                    alertErrorInsert.close();
                }
            });
        }
    }

    public void setEcole(Ecole ecole){
        ecoleUpdate = ecole;
        nomEcole.setText(ecoleUpdate.getNom());
        libeleAdresse.setText(ecoleUpdate.getAdresse().getAdresse());
        nomDepartement.getSelectionModel().select(ecoleUpdate.getAdresse().getVille().getDepartement());
        ville.getSelectionModel().select(ecoleUpdate.getAdresse().getVille());
    }

    public void updateEcole(){
        if (!nomEcole.getText().isEmpty() && !libeleAdresse.getText().isEmpty() && ville.getSelectionModel().getSelectedItem().getId_ville() != 0 && nomDepartement.getSelectionModel().getSelectedItem().getId_departement() != 0){
            ecoleUpdate.setNom(nomEcole.getText());
            ecoleUpdate.getAdresse().setAdresse(libeleAdresse.getText());
            ecoleUpdate.getAdresse().getVille().setVille(ville.getSelectionModel().getSelectedItem().getVille());
            ecoleUpdate.getAdresse().getVille().getDepartement().setDepartement(nomDepartement.getSelectionModel().getSelectedItem().getDepartement());
            DAOFactory.getVilleDAO().update(ecoleUpdate.getAdresse().getVille());
            DAOFactory.getDepartementDAO().update(ecoleUpdate.getAdresse().getVille().getDepartement());
            DAOFactory.getAdresseDAO().update(ecoleUpdate.getAdresse());
            DAOFactory.getEcoleDAO().update(ecoleUpdate);
                ecoleController.filter();
                closeModal();
            }
            else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("L'école n'a pas pu être modifiée");
            alert.showAndWait();
        }
    }

    private void filterDepartement() {
        ville.setItems(FXCollections.observableArrayList(filter.getVilleLike("", searchableEcole.getIdDepartement())));
        ville.getSelectionModel().select(0);
        filter();
    }

    @FXML
    private void filter(){
        if(nomDepartement.getSelectionModel().getSelectedItem() != null){
            searchableEcole.setIdDepartement(nomDepartement.getSelectionModel().getSelectedItem().getId_departement());
        }
    }

    @FXML
    private void validate(){
        if(create)
            addEcole();
        else
            updateEcole();
    }
    public void setCreate(boolean bool){
        create = bool;
        if(!create)
            nomModal.setText("Modifier école");
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }
    @FXML
    private void closeModal(){
        modal.close();
    }

    public void setEcoleController(EcoleController ecoleController) {
        this.ecoleController = ecoleController;
    }
}
