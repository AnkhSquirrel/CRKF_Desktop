package fr.kyo.crkf.controller.ville;

import com.jfoenix.controls.JFXDrawer;
import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.entity.Departement;
import fr.kyo.crkf.entity.Ville;
import fr.kyo.crkf.searchable.Filter;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class GestionVilleController {

    @FXML
    private TableView<Ville> villeTable;
    @FXML
    private TableColumn<Ville, String> villeColumn;
    @FXML
    private TableColumn<Ville, String> departementColumn;
    @FXML
    private SearchableComboBox<Departement> departement;
    @FXML
    private TextField libelle;
    @FXML
    private Label pageNumber;
    @FXML
    private Label numberOfPage;
    @FXML
    private GridPane listeVille;
    @FXML
    private JFXDrawer drawer;
    private int pageTotale;
    private String ville;
    private int page;
    private ApplicationCRKF applicationCRKF;
    private int departementId;

    @FXML
    private void initialize(){
        departementId = 0;
        ville = "";
        page = 1;
        Filter filter = new Filter();

        villeColumn.setCellValueFactory(cellDate -> cellDate.getValue().getVilleStringProperty());
        departementColumn.setCellValueFactory(cellDate -> cellDate.getValue().getDepartement().getDepartementStringProperty());

        departement.setItems(FXCollections.observableArrayList(filter.getDepartements()));
        departement.getSelectionModel().selectedItemProperty().addListener(observable -> filter());
        departement.getSelectionModel().select(0);

        libelle.textProperty().addListener((observable, oldValue, newValue) -> {
            libelle.setText(newValue.replaceAll("[\\d'], ""));
            filter();
        });

        pageTotale = DAOFactory.getVilleDAO().getNumberOfVilles(ville, departementId) / 25;
        numberOfPage.setText(String.valueOf(pageTotale));

        villeTable.getSelectionModel().selectedItemProperty().addListener(observable -> openDetailVille());
        villeTable.setItems(FXCollections.observableArrayList(DAOFactory.getVilleDAO().getLikeForGestion(ville, departement.getSelectionModel().getSelectedItem().getDepartementId(), page)));
    }

    @FXML
    private void openMainMenu(){
        applicationCRKF.openMainMenu();
    }

    public void setApplicationCRKF(ApplicationCRKF applicationCRKF){
        this.applicationCRKF = applicationCRKF;
    }

    private void openDetailVille(){
        if(villeTable.getSelectionModel().getSelectedItem() != null){
            try {
                FXMLLoader fxmlLoaderListeVille = new FXMLLoader();
                fxmlLoaderListeVille.setLocation(ApplicationCRKF.class.getResource("detail_ville.fxml"));
                VBox detailVille = fxmlLoaderListeVille.load();
                DetailVilleController detailVilleController = fxmlLoaderListeVille.getController();
                detailVilleController.setVille(villeTable.getSelectionModel().getSelectedItem());
                detailVilleController.setGestionVilleController(this);
                drawer.setSidePane(detailVille);
                drawer.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
                openDetail();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openDetail(){
        drawer.setDisable(false);
        drawer.open();
        listeVille.setEffect(new GaussianBlur());
        listeVille.setDisable(true);
        if (Boolean.TRUE.equals(applicationCRKF.getLightMode()))
            drawer.getStylesheets().add(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/fr/kyo/crkf/lightMode.css")).toExternalForm()));
        else
            drawer.getStylesheets().add(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/fr/kyo/crkf/darkMode.css")).toExternalForm()));

    }

    public void closeDetail(){
        drawer.close();
        drawer.setDisable(true);
        listeVille.setEffect(null);
        listeVille.setDisable(false);
        villeTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void openCreateModal(){
        applicationCRKF.openModalCreateVille(this);
    }

    @FXML
    private void openUpdateModal(){
        if (villeTable.getSelectionModel().getSelectedItem() != null)
            applicationCRKF.openModalUpdateVille(this, villeTable.getSelectionModel().getSelectedItem());
    }

    public void filter(){
        if(!libelle.getText().equals(ville)){
           ville = libelle.getText();
           page = 1;
        }
        if(departement.getSelectionModel().getSelectedItem() != null && departementId != departement.getSelectionModel().getSelectedItem().getDepartementId()){
           departementId = departement.getSelectionModel().getSelectedItem().getDepartementId();
            page = 1;
        }

        pageTotale = DAOFactory.getVilleDAO().getNumberOfVilles(ville, departementId) / 25;
        if(pageTotale == 0)
            pageTotale++;
        numberOfPage.setText(String.valueOf(pageTotale));

        villeTable.setItems(FXCollections.observableArrayList(DAOFactory.getVilleDAO().getLikeForGestion(ville, departementId, page)));
        pageNumber.setText("Page " + page + " / ");
    }

    @FXML
    private void remove(){
        if (villeTable.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Supprimer");
            alert.setHeaderText("Voulez-vous vraiment supprimer cet élément?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
                DAOFactory.getVilleDAO().delete(villeTable.getSelectionModel().getSelectedItem());
            filter();
        }
    }

    @FXML
    private void reset(){
        libelle.setText("");
        departement.getSelectionModel().select(0);
        page = 1;
    }

    @FXML
    private void pagePlus(){
        if(!villeTable.getItems().isEmpty() && pageTotale > page ){
            page++;
            filter();
        }
    }

    @FXML
    private void pageMoins(){
        if (page > 1){
            page--;
            filter();
        }
    }

    @FXML
    private void lastPage(){
        page = pageTotale;
        filter();
    }

    @FXML
    private void firstPage(){
        page = 1;
        filter();
    }

}
