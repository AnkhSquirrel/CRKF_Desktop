package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Classification;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClassificationDAOTest {

    @Test
    void getByID() {
        assertTrue(DAOFactory.getClassificationDAO().getByID(1) != null);
    }

    @Test
    void getAll() {
        ArrayList<Classification> classifications = new ArrayList<>();
        assertEquals(classifications.getClass(), DAOFactory.getClassificationDAO().getAll(1).getClass());
    }

    @Test
    void insert_update_delete() {
        Classification classification = new Classification(1,"Insert_Classification");
        int id = DAOFactory.getClassificationDAO().insert(classification);
        assertTrue(id != 0);

        classification.setId_classification(id);
        classification.setclassification("Update_Classification");

        assertTrue(DAOFactory.getClassificationDAO().update(classification));

        assertTrue(DAOFactory.getClassificationDAO().delete(classification));
    }
}