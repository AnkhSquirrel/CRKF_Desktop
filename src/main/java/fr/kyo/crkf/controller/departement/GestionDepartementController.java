package fr.kyo.crkf.controller.departement;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.entity.Departement;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Optional;

public class GestionDepartementController {

    @FXML
    private TableColumn<Departement,String> departementColumn;
    @FXML
    private TableColumn<Departement,Integer> nbreEcoleColumn;
    @FXML
    private TableColumn<Departement, String> numDepColumn;
    @FXML
    private TextField libelle;
    @FXML
    private TableView<Departement> departementTable;
    @FXML
    private Label pageNumber;
    @FXML
    private Label numberOfPage;
    private int pageTotale;
    private String departement;
    private ApplicationCRKF applicationCRKF;
    private int page;

    @FXML
    private void initialize(){
        departement="";
        page = 1;

        departementColumn.setCellValueFactory(cellData -> cellData.getValue().getDepartementStringProperty());
        numDepColumn.setCellValueFactory(cellData -> cellData.getValue().getNumDepartementString());
        nbreEcoleColumn.setCellValueFactory(cellData -> cellData.getValue().getNumberOfSchoolInDepartement());

        libelle.textProperty().addListener((observable, oldValue, newValue) -> {
            libelle.setText(newValue.replaceAll("[\\d'], ""));
            filter();
        });

        departementTable.setItems(FXCollections.observableArrayList(DAOFactory.getDepartementDAO().getLike(departement,0)));

        pageTotale = DAOFactory.getDepartementDAO().getNumberOfDepartements(departement) / 25;
        if (pageTotale == 0)
            pageTotale ++;
        numberOfPage.setText(String.valueOf(pageTotale));


        filter();
    }

    public void filter(){
        if(!libelle.getText().equals(departement)){
            departement = libelle.getText();
            page = 1;
        }
        departementTable.setItems(FXCollections.observableArrayList(DAOFactory.getDepartementDAO().getLike(departement,page)));

        pageTotale = DAOFactory.getDepartementDAO().getNumberOfDepartements(departement) / 25;
        if (pageTotale == 0)
            pageTotale ++;
        numberOfPage.setText(" / " + pageTotale);

        pageNumber.setText("Page " + page);
    }


    public void setApplicationCRKF(ApplicationCRKF applicationCRKF){
        this.applicationCRKF = applicationCRKF;
    }
    @FXML
    private void openMainMenu(){
        applicationCRKF.openMainMenu();
    }
    @FXML
    private void update(){
        if (departementTable.getSelectionModel().getSelectedItem() != null)
            applicationCRKF.openModalUpdateDepartement(this, departementTable.getSelectionModel().getSelectedItem());
    }
    @FXML
    private void openCreateModal(){
        applicationCRKF.openModalCreateDepartement(this);
    }

    @FXML
    private void remove(){
        if (departementTable.getSelectionModel().getSelectedItem() != null){
            delete();
        } else {
            Alert alertErrorInsert = new Alert(Alert.AlertType.ERROR);
            alertErrorInsert.setTitle("Erreur");
            alertErrorInsert.setHeaderText("Erreur lors de la suppression.");
            alertErrorInsert.showAndWait().ifPresent(btnTypeError -> {
                if (btnTypeError == ButtonType.OK) {
                    alertErrorInsert.close();
                }
            });
        }
    }

    private void delete() {
        if (DAOFactory.getDepartementDAO().getVilleByDepartement(departementTable.getSelectionModel().getSelectedItem().getDepartementId()).isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Supprimer");
            alert.setHeaderText("Voulez-vous vraiment supprimer cet element?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
                DAOFactory.getDepartementDAO().delete(departementTable.getSelectionModel().getSelectedItem());
            filter();
        }else {
            Alert alertErrorInsert = new Alert(Alert.AlertType.ERROR);
            alertErrorInsert.setTitle("Erreur");
            alertErrorInsert.setHeaderText("Le département ne peut pas être supprimé car il contient des villes");
            alertErrorInsert.showAndWait();
        }
    }

    @FXML
    private void reset(){
        libelle.setText("");
    }

    @FXML
    private void pagePlus(){
        if(!departementTable.getItems().isEmpty() && pageTotale > page){
            page++;
            filter();
        }
    }
    @FXML
    private void pageMoins(){
        if (page > 1 ){
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

