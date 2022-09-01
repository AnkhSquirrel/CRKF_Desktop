package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonneDAOTest {

    @Test
    void getByID() {
        //TODO TEST WITH DATA IN DB
        //assertTrue(DAOFactory.getPersonneDAO().getByID(1) != null);
    }

    @Test
    void getAll() {
        ArrayList<Personne> personnes = new ArrayList<>();
        assertEquals(personnes.getClass(), DAOFactory.getPersonneDAO().getAll(1).getClass());
    }

    @Test
    void insert_update_delete() {
        Personne personne = new Personne(0,"Insert", "Personne",0,new Adresse(2,"Insert_Adresse",new Ville(1,"yes",0,0,new Departement(0,"",""))), new Ecole(1,"Insert_Ecole",new Adresse(2,"Insert_Adresse",new Ville(1,"yes",0,0,new Departement(0,"","")))));
        //TODO ADD TEST DIPLOME
        int id = DAOFactory.getPersonneDAO().insert(personne);

        assertTrue(id != 0);

        personne.setId_personne(id);
        personne.setNom("Update");

        assertTrue(DAOFactory.getPersonneDAO().update(personne));
        assertTrue(DAOFactory.getPersonneDAO().delete(personne));
    }
}