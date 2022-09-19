package fr.kyo.crkf.controller;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.Cycle;
import fr.kyo.crkf.Entity.Diplome;
import fr.kyo.crkf.Entity.Instrument;
import fr.kyo.crkf.Entity.Personne;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.controller.professeur.ProfesseurController;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

import java.util.ArrayList;
import java.util.Optional;

public class GestionDiplomeController {

    @FXML
    private SearchableComboBox<Cycle> cycle;

    @FXML
    private TableColumn<Diplome, String> cylceColumn;

    @FXML
    private TableView<Diplome> diplomeTable;

    @FXML
    private SearchableComboBox<Instrument> instrument;

    @FXML
    private TableColumn<Diplome, String> instrumentColumn;

    @FXML
    private Label pageNumber;
    private int page;
    private Personne personne;
    private ProfesseurController professeurController;
    private ApplicationCRKF applicationCRKF;

    @FXML
    private void initialize(){
        Filter filter = new Filter();

        instrumentColumn.setCellValueFactory(cellData -> cellData.getValue().getInstrument().getNomStringProperty());
        cylceColumn.setCellValueFactory(cellData -> cellData.getValue().getCycle().getCycleStringProperty());

        instrument.setItems(FXCollections.observableArrayList(filter.getInstrument()));
        cycle.setItems(FXCollections.observableArrayList(filter.getCycles()));

        instrument.getSelectionModel().select(0);
        cycle.getSelectionModel().select(0);

        instrument.getSelectionModel().selectedItemProperty().addListener(a -> filter());
        cycle.getSelectionModel().selectedItemProperty().addListener(a -> filter());
    }

    @FXML
    void openCreateModal(ActionEvent event) {
        applicationCRKF.openAddDiplomeModal(this, personne);
    }

    @FXML
    void openDetailProfesseur(ActionEvent event) {
        professeurController.openDetailProfesseur(DAOFactory.getPersonneDAO().getByID(personne.getId_personne()));
    }

    @FXML
    void pageMoins(ActionEvent event) {
        if(page > 1){
            page--;
            pageNumber.setText("Page : " + page);
            filter();
        }
    }

    @FXML
    void pagePlus(ActionEvent event) {
        if(!diplomeTable.getItems().isEmpty()){
            page++;
            pageNumber.setText("Page : " + page);
            filter();
        }
    }

    @FXML
    void remove(ActionEvent event) {
        if(diplomeTable.getSelectionModel().getSelectedItem() != null){
            ArrayList<Diplome> diplomesSuperior = DAOFactory.getDiplomeDAO().getDiplomeSupriorOf(personne.getId_personne(), diplomeTable.getSelectionModel().getSelectedItem().getInstrument().getId_instrument(), diplomeTable.getSelectionModel().getSelectedItem().getCycle().getId_cycle());
            if(!diplomesSuperior.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Attention");
                alert.setHeaderText("Les diplomes de niveau supérieur du même instrument seront supprimer, êtes vous sur de vouloir continuer?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK){
                    for (Diplome diplome : diplomesSuperior){
                        DAOFactory.getDiplomeDAO().deleteDiplome(personne.getId_personne() , diplome.getCycle().getId_cycle(), diplome.getInstrument().getId_instrument());
                    }
                    DAOFactory.getDiplomeDAO().deleteDiplome(personne.getId_personne(), diplomeTable.getSelectionModel().getSelectedItem().getCycle().getId_cycle(), diplomeTable.getSelectionModel().getSelectedItem().getInstrument().getId_instrument());
                    filter();
                }
            }else if(applicationCRKF.deleteModal()){
                DAOFactory.getDiplomeDAO().deleteDiplome(personne.getId_personne(), diplomeTable.getSelectionModel().getSelectedItem().getCycle().getId_cycle(), diplomeTable.getSelectionModel().getSelectedItem().getInstrument().getId_instrument());
                filter();
            }



        }

    }
    @FXML
    void update(ActionEvent event){

    }

    @FXML
    void reset(ActionEvent event) {
        instrument.getSelectionModel().select(0);
        cycle.getSelectionModel().select(0);
    }

    public void filter(){
        int instrument_id = 0;
        int cycle_id = 0;

        if( instrument.getSelectionModel().getSelectedItem() != null)
            instrument_id =  instrument.getSelectionModel().getSelectedItem().getId_instrument();
        if(cycle.getSelectionModel().getSelectedItem() != null)
            cycle_id = cycle.getSelectionModel().getSelectedItem().getId_cycle();

        diplomeTable.setItems(FXCollections.observableArrayList(DAOFactory.getDiplomeDAO().getPersonneDiplomeLike(personne.getId_personne(),instrument_id, cycle_id)));

    }

    public void setPersonne(Personne personne){
        this.personne = personne;
        diplomeTable.setItems(FXCollections.observableArrayList(personne.getDiplomes()));
    }

    public void setProfesseurController(ProfesseurController professeurController){
        this.professeurController = professeurController;
    }

    public void setApplicationCRKF(ApplicationCRKF applicationCRKF) {
        this.applicationCRKF = applicationCRKF;
    }
}
