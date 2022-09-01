package fr.kyo.crkf.Controller;

import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ProfesseurController {
     TableView profTable;

     private void initialize(){
         profTable.setItems((ObservableList) DAOFactory.getPersonneDAO().getAll(1));
     }
}
