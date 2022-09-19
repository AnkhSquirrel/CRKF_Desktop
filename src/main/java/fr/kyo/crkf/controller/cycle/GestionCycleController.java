package fr.kyo.crkf.controller.cycle;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.Cycle;
import fr.kyo.crkf.Searchable.Filter;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class GestionCycleController {
    @FXML
    private TableColumn<Cycle,String> libelleColumn;
    @FXML
    private TableColumn<Cycle,Integer> cycleColumn;
    @FXML
    private TextField libelle;
    @FXML
    private TableView<Cycle> cycleTable;
    @FXML
    private Label pageNumber;
    private int page;
    private String cycle;
    private ApplicationCRKF applicationCRKF;

    @FXML
    private void initialize(){
        cycle = "";
        page = 1;
        Filter filter = new Filter();
        // initialize tableview
        libelleColumn.setCellValueFactory(cellData -> cellData.getValue().getCycleStringProperty());
        cycleColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCycle()));

        libelle.textProperty().addListener(observable -> filter());

        cycleTable.setItems(FXCollections.observableArrayList(DAOFactory.getCycleDAO().getLike(cycle,page)));

        reset();
        filter();
    }

    public void filter(){
        if(!libelle.getText().equals(cycle)){
            cycle = libelle.getText();
            page = 1;
        }
        cycleTable.setItems(FXCollections.observableArrayList(DAOFactory.getCycleDAO().getLike(cycle,page)));
        pageNumber.setText("Page " + page);
    }

    @FXML
    private void openCreateModal(){
        applicationCRKF.openCreateCycleModal(this);
    }
    @FXML
    private void reset(){
        libelle.setText("");
    }
    @FXML
    private void pagePlus(){
        if(!cycleTable.getItems().isEmpty()){
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

    public void setApplicationCRKF(ApplicationCRKF applicationCRKF){
        this.applicationCRKF = applicationCRKF;
    }
    @FXML
    private void openMainMenu(){
        applicationCRKF.openMainMenu();
    }

    @FXML
    private void remove(){
        if (cycleTable.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Supprimer");
            alert.setHeaderText("Voulez vous vraiment supprimer cet element?");
            Optional<ButtonType> result = alert.showAndWait();
            if(!(result.isPresent() && result.get() == ButtonType.OK) || cycleTable.getSelectionModel().getSelectedItem().getHighestCycle().getValue() > cycleTable.getSelectionModel().getSelectedItem().getCycle()){
                alert.close();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Erreur");
                alert1.setHeaderText("Il y a eu une erreur lors de la suppresion du cycle.\nIl est impossible de supprimer un cycle qui a des cycles supérieurs");
                alert1.showAndWait();
            }else{
                DAOFactory.getCycleDAO().delete(cycleTable.getSelectionModel().getSelectedItem());
                filter();

            }
        }
    }
    @FXML
    private void update(){
        if (cycleTable.getSelectionModel().getSelectedItem() != null)
            applicationCRKF.openUpdateCycleModal(this, cycleTable.getSelectionModel().getSelectedItem());
    }
}

