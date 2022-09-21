package fr.kyo.crkf.controller.diplome;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.entity.Cycle;
import fr.kyo.crkf.entity.Diplome;
import fr.kyo.crkf.entity.Instrument;
import fr.kyo.crkf.entity.Personne;
import fr.kyo.crkf.searchable.Filter;
import fr.kyo.crkf.controller.professeur.ProfesseurController;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    void openCreateModal() {
        applicationCRKF.openAddDiplomeModal(this, personne);
    }

    @FXML
    void openDetailProfesseur() {
        professeurController.openDetailProfesseur(DAOFactory.getPersonneDAO().getByID(personne.getPersonneId()));
    }

    @FXML
    void pageMoins() {
        if(page > 1){
            page--;
            pageNumber.setText("Page : " + page);
            filter();
        }
    }

    @FXML
    void pagePlus() {
        if(!diplomeTable.getItems().isEmpty()){
            page++;
            pageNumber.setText("Page : " + page);
            filter();
        }
    }

    @FXML
    void remove() {
        if(diplomeTable.getSelectionModel().getSelectedItem() != null){
            ArrayList<Diplome> diplomesSuperior = DAOFactory.getDiplomeDAO().getDiplomeSupriorOf(personne.getPersonneId(), diplomeTable.getSelectionModel().getSelectedItem().getInstrument().getInstrumentId(), diplomeTable.getSelectionModel().getSelectedItem().getCycle().getCycleId());
            if(!diplomesSuperior.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Attention");
                alert.setHeaderText("Les diplomes de niveau supérieur du même instrument seront supprimer, êtes vous sur de vouloir continuer?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK){
                    for (Diplome diplome : diplomesSuperior){
                        DAOFactory.getDiplomeDAO().deleteDiplome(personne.getPersonneId() , diplome.getCycle().getCycleId(), diplome.getInstrument().getInstrumentId());
                    }
                    DAOFactory.getDiplomeDAO().deleteDiplome(personne.getPersonneId(), diplomeTable.getSelectionModel().getSelectedItem().getCycle().getCycleId(), diplomeTable.getSelectionModel().getSelectedItem().getInstrument().getInstrumentId());
                    filter();
                }
            } else if (applicationCRKF.deleteModal()){
                DAOFactory.getDiplomeDAO().deleteDiplome(personne.getPersonneId(), diplomeTable.getSelectionModel().getSelectedItem().getCycle().getCycleId(), diplomeTable.getSelectionModel().getSelectedItem().getInstrument().getInstrumentId());
                filter();
            }
        }
    }

    @FXML
    void reset() {
        instrument.getSelectionModel().select(0);
        cycle.getSelectionModel().select(0);
    }

    public void filter(){
        int instrument_id = 0;
        int cycle_id = 0;
        if( instrument.getSelectionModel().getSelectedItem() != null)
            instrument_id =  instrument.getSelectionModel().getSelectedItem().getInstrumentId();
        if(cycle.getSelectionModel().getSelectedItem() != null)
            cycle_id = cycle.getSelectionModel().getSelectedItem().getCycleId();
        diplomeTable.setItems(FXCollections.observableArrayList(DAOFactory.getDiplomeDAO().getPersonneDiplomeLike(personne.getPersonneId(),instrument_id, cycle_id)));
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
