package fr.kyo.crkf.controller.cycle;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.entity.Cycle;
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
    @FXML
    private Label numberOfPage;
    private int pageTotale;
    private int page;
    private String cycle;
    private ApplicationCRKF applicationCRKF;

    @FXML
    private void initialize(){
        cycle = "";
        page = 1;
        libelleColumn.setCellValueFactory(cellData -> cellData.getValue().getCycleStringProperty());
        cycleColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCycleNumero()));

        libelle.textProperty().addListener(observable -> filter());

        cycleTable.setItems(FXCollections.observableArrayList(DAOFactory.getCycleDAO().getLike(cycle,page)));

        pageTotale = DAOFactory.getCycleDAO().getLikeAllCycle(cycle) / 25;

        if (pageTotale == 0)
            pageTotale ++;
        numberOfPage.setText(String.valueOf(pageTotale));

        reset();
        filter();
    }

    public void filter(){
        if(!libelle.getText().equals(cycle)){
            cycle = libelle.getText();
            page = 1;
        }
        cycleTable.setItems(FXCollections.observableArrayList(DAOFactory.getCycleDAO().getLike(cycle,page)));

        pageTotale = DAOFactory.getCycleDAO().getLikeAllCycle(cycle) / 25;
        if (pageTotale == 0)
            pageTotale ++;
        numberOfPage.setText(" / " + pageTotale);

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
        if(!cycleTable.getItems().isEmpty() && pageTotale > page){
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
            if(!(result.isPresent() && result.get() == ButtonType.OK) || cycleTable.getSelectionModel().getSelectedItem().getHighestCycle().getValue() > cycleTable.getSelectionModel().getSelectedItem().getCycleNumero()){
                alert.close();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Erreur");
                alert1.setHeaderText("Il y a eu une erreur lors de la suppresion du cycle.\nIl est impossible de supprimer un cycle qui a des cycles supérieurs");
                alert1.showAndWait();
            } else {
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

