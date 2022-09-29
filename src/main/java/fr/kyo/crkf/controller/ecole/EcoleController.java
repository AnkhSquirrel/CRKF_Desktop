package fr.kyo.crkf.controller.ecole;


import com.jfoenix.controls.JFXDrawer;
import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.entity.Departement;
import fr.kyo.crkf.entity.Ecole;
import fr.kyo.crkf.entity.Instrument;
import fr.kyo.crkf.entity.Ville;
import fr.kyo.crkf.searchable.Filter;
import fr.kyo.crkf.searchable.SearchableEcole;
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

public class EcoleController {

        @FXML
        private TableView<Ecole> ecoleTable;
        @FXML
        private TableColumn<Ecole, String> nomColumn;
        @FXML
        private TableColumn<Ecole, String> adresseColumn;
        @FXML
        private TableColumn<Ecole, String> villeColumn;
        @FXML
        private TableColumn<Ecole, String> departementColumn;
        @FXML
        private ComboBox<Ville> ville;
        @FXML
        private SearchableComboBox<Departement> departement;
        @FXML
        private SearchableComboBox<Instrument> instrumentFilter;
        @FXML
        private TextField nomEcole;
        @FXML
        private Label pageNumber;
        @FXML
        private Label numberOfPage;
        @FXML
        private GridPane listeEcole;
        @FXML
        private JFXDrawer drawer;
        private int pageTotale;
        private SearchableEcole searchableEcole;
        private Filter filter;
        private ApplicationCRKF applicationCRKF;

        private int page;

        @FXML
        private void initialize() {
                page = 1;
                filter = new Filter();
                searchableEcole = new SearchableEcole();

                nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
                villeColumn.setCellValueFactory(cellData -> cellData.getValue().getEcoleAdresse().getVille().getVilleStringProperty());
                adresseColumn.setCellValueFactory(cellData -> cellData.getValue().getEcoleAdresse().getAdresseStringProperty());
                departementColumn.setCellValueFactory(cellData -> cellData.getValue().getEcoleAdresse().getVille().getDepartement().getDepartementStringProperty());

                villeFilter();
                ville.getSelectionModel().selectedItemProperty().addListener(observable -> filter());
                ville.setEditable(true);
                ville.getEditor().textProperty().addListener(observable -> villeFilter());
                ville.getEditor().setText("Ville");

                departement.setItems(FXCollections.observableArrayList(filter.getDepartements()));
                departement.getSelectionModel().selectedItemProperty().addListener(observable -> filterDepartement());

                instrumentFilter.setItems(FXCollections.observableArrayList(filter.getInstrument()));
                instrumentFilter.getSelectionModel().selectedItemProperty().addListener(observable -> filter());

                nomEcole.textProperty().addListener(observable -> filter());

                ecoleTable.getSelectionModel().selectedItemProperty().addListener(observable -> openDetailEcole());
                ecoleTable.setItems(FXCollections.observableArrayList(DAOFactory.getEcoleDAO().getLike(searchableEcole, page)));

                pageTotale = DAOFactory.getEcoleDAO().getLikeAllEcole(searchableEcole).size() / 25;
                if(pageTotale == 0)
                        pageTotale++;
                numberOfPage.setText(" / " + pageTotale);
        }

        private void filterDepartement() {
                filter();
                ville.setDisable(departement.getSelectionModel().getSelectedItem() == null || departement.getSelectionModel().getSelectedItem().getDepartementId() == 0);
                ville.setItems(FXCollections.observableArrayList(filter.getVilleLike("",searchableEcole.getIdDepartement())));
                ville.getSelectionModel().select(0);
        }

        private void villeFilter() {
                if((searchableEcole.getIdVille() == 0 && ville.getSelectionModel().getSelectedItem() == null) || !ville.getEditor().getText().equals(ville.getSelectionModel().getSelectedItem().getVilleLibelle())){
                        ville.setItems(FXCollections.observableArrayList(filter.getVilleLike(ville.getEditor().getText(),searchableEcole.getIdDepartement())));
                }
        }

        @FXML
        private void reset(){
                page = 1;
                nomEcole.setText("");
                departement.getSelectionModel().selectFirst();
                ville.getSelectionModel().selectFirst();
                instrumentFilter.getSelectionModel().select(0);
                filter();
        }

        @FXML
        public void filter(){
                if(!nomEcole.getText().equals(searchableEcole.getNom())) {
                        searchableEcole.setNom(nomEcole.getText());
                        page = 1;
                }
                if(!ville.getSelectionModel().isEmpty() && ville.getSelectionModel().getSelectedItem() != null && ville.getSelectionModel().getSelectedItem().getVilleId() != searchableEcole.getIdVille()){
                        searchableEcole.setIdVille(ville.getSelectionModel().getSelectedItem().getVilleId());
                        page = 1;
                }

                if(isDepartementSelected()){
                        searchableEcole.setIdDepartement(departement.getSelectionModel().getSelectedItem().getDepartementId());
                        page = 1;
                }

                if(isInstrumentSelected()){
                        searchableEcole.setIdInstrument(instrumentFilter.getSelectionModel().getSelectedItem().getInstrumentId());
                        page = 1;
                }

                ecoleTable.setItems(FXCollections.observableArrayList(DAOFactory.getEcoleDAO().getLike(searchableEcole, page)));

                pageTotale = DAOFactory.getEcoleDAO().getLikeAllEcole(searchableEcole).size() / 25;
                if (pageTotale == 0)
                        pageTotale++;
                numberOfPage.setText( " / " + pageTotale);

                pageNumber.setText("Page " + page);
        }

        private boolean isDepartementSelected() {
                return departement.getSelectionModel().getSelectedItem() != null && departement.getSelectionModel().getSelectedItem().getDepartementId() != searchableEcole.getIdDepartement();
        }

        private boolean isInstrumentSelected() {
                return instrumentFilter.getSelectionModel().getSelectedItem() != null && instrumentFilter.getSelectionModel().getSelectedItem().getInstrumentId() != searchableEcole.getIdInstrument();
        }

        private void openDetailEcole(){
                if(ecoleTable.getSelectionModel().getSelectedItem() != null){
                        try {
                                FXMLLoader fxmlLoaderListeEcole = new FXMLLoader();
                                fxmlLoaderListeEcole.setLocation(ApplicationCRKF.class.getResource("detail_ecole.fxml"));
                                VBox detailEcole = fxmlLoaderListeEcole.load();
                                DetailEcoleController detailEcoleController = fxmlLoaderListeEcole.getController();
                                detailEcoleController.setEcole(ecoleTable.getSelectionModel().getSelectedItem());
                                detailEcoleController.setEcoleController(this);
                                detailEcoleController.setApplicationCRKF(applicationCRKF);
                                detailEcoleController.setDrawer(drawer);
                                drawer.setSidePane(detailEcole);
                                drawer.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
                                openDetail();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }

        public void setApplicationCRKF (ApplicationCRKF applicationCRKF) {
                this.applicationCRKF = applicationCRKF;
        }

        @FXML
        private void openCreateModal(){
                applicationCRKF.openCreateEcoleModal(this);
        }

        @FXML
        private void openMainMenu(){
                applicationCRKF.openMainMenu();
        }

        @FXML
        private void pagePlus(){
                if(!ecoleTable.getItems().isEmpty() && pageTotale > page){
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

        public void closeDetail(){
                drawer.close();
                drawer.setDisable(true);
                listeEcole.setEffect(null);
                listeEcole.setDisable(false);
                ecoleTable.getSelectionModel().clearSelection();
        }

        public void openDetail(){
                drawer.setDisable(false);
                drawer.open();
                listeEcole.setEffect(new GaussianBlur());
                listeEcole.setDisable(true);

                if (applicationCRKF.getLightMode()){
                        drawer.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fr/kyo/crkf/lightMode.css").toExternalForm()));
                }
                else{
                        drawer.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fr/kyo/crkf/darkMode.css").toExternalForm()));
                }
        }

        public void closeEcoleAroundPage(){
                openDetailEcole();
        }

}
