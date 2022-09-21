package fr.kyo.crkf.controller.ecole;

import fr.kyo.crkf.entity.Adresse;
import fr.kyo.crkf.entity.Departement;
import fr.kyo.crkf.entity.Ecole;
import fr.kyo.crkf.entity.Ville;
import fr.kyo.crkf.searchable.Filter;
import fr.kyo.crkf.searchable.SearchableEcole;
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
    private ComboBox<Ville> ville;
    @FXML
    private SearchableComboBox<Departement> nomDepartement;
    @FXML
    private Label nomModal;
    private Ville selectedVille;
    private SearchableEcole searchableEcole;
    private Ecole ecoleUpdate;
    private boolean create;
    private Filter filter;
    private EcoleController ecoleController;


    @FXML
    private void initialize(){
        filter = new Filter();

        nomDepartement.setItems(FXCollections.observableArrayList(filter.getDepartements()));
        nomDepartement.getSelectionModel().selectedItemProperty().addListener(observable -> filterDepartement());
        nomDepartement.getSelectionModel().select(0);

        ville.setEditable(true);
        ville.setItems(FXCollections.observableArrayList(filter.getVilleLike("", 0)));
        ville.getSelectionModel().select(0);
        ville.getSelectionModel().selectedItemProperty().addListener(a -> selectVille());
        ville.getEditor().textProperty().addListener(observable -> villeFilter());

        selectedVille = ville.getItems().get(0);

    }
    @FXML
    private void addEcole(){
        Adresse adresseObject = new Adresse(0, libeleAdresse.getText(),selectedVille.getVilleId());
        int idAdresse = DAOFactory.getAdresseDAO().insert(adresseObject);
        if (idAdresse != 0){
            Ecole ecole = new Ecole(0, nomEcole.getText(), idAdresse);
            if (DAOFactory.getEcoleDAO().insert(ecole) != 0){
                ecoleController.filter();
                closeModal();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Il y a eu une erreur lors de la création de l'école.\nMerci de vérifier que vous avez entrée des informations valides");
                alert.showAndWait();
            }
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il y a eu une erreur lors de la création de l'adresse.\nMerci de vérifier que vous avez entrée des informations valides");
            alert.showAndWait();
        }
    }

    public void setEcole(Ecole ecole){
        ecoleUpdate = ecole;
        nomEcole.setText(ecoleUpdate.getEcoleNom());
        libeleAdresse.setText(ecoleUpdate.getEcoleAdresse().getAdresseLibelle());
        nomDepartement.getSelectionModel().select(ecoleUpdate.getEcoleAdresse().getVille().getDepartement());
        ville.getSelectionModel().select(ecoleUpdate.getEcoleAdresse().getVille());

        selectedVille = ecoleUpdate.getEcoleAdresse().getVille();
    }

    public void updateEcole(){
        Adresse temp = new Adresse(ecoleUpdate.getEcoleAdresse().getAdresseId(), libeleAdresse.getText(), selectedVille.getVilleId());
        if (DAOFactory.getAdresseDAO().update(temp)){
            ecoleUpdate = new Ecole(ecoleUpdate.getEcoleId(), nomEcole.getText(), temp.getAdresseId());
            if (DAOFactory.getEcoleDAO().update(ecoleUpdate)){
                ecoleController.filter();
                closeModal();
            } else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Il y a eu une erreur lors de la modification de l'école.\n Merci de vérifier que vous avez entrée des informations valides");
                alert.showAndWait();
                }
            } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("\"Il y a eu une erreur lors de la modification de l'adresse.\\n Merci de vérifier que vous avez entrée des informations valides");
            alert.showAndWait();
        }
    }

    private void filterDepartement() {
        if(nomDepartement.getSelectionModel().getSelectedItem() != null && nomDepartement.getSelectionModel().getSelectedItem().getDepartementId() != 0){
            ville.setDisable(false);
            ville.setItems(FXCollections.observableArrayList(filter.getVilleLike("", nomDepartement.getSelectionModel().getSelectedItem().getDepartementId())));
            ville.getSelectionModel().select(0);
        }
    }

    private void villeFilter() {
        if(!ville.getEditor().getText().equals(selectedVille.getVilleLibelle())){
            ville.setItems(FXCollections.observableArrayList(filter.getVilleLike(ville.getEditor().getText(),nomDepartement.getSelectionModel().getSelectedItem().getDepartementId())));
        }
    }

    private void selectVille() {
        if(ville.getSelectionModel().getSelectedIndex() >= 1 && ville.getSelectionModel().getSelectedItem() != null)
            selectedVille = ville.getSelectionModel().getSelectedItem();
    }


    @FXML
    void validate() {
        boolean adresseComplete = selectedVille != null &&
                !libeleAdresse.getText().isEmpty() &&
                nomDepartement.getSelectionModel().getSelectedItem() != null &&
                ville.getSelectionModel().getSelectedItem() != null;
        boolean ecoleComplete = !nomEcole.getText().isEmpty();
        if(adresseComplete && ecoleComplete){
            if(create){
                addEcole();
            } else
                updateEcole();
        }
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
