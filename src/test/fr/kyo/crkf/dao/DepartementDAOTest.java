package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Departement;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartementDAOTest {

    @Test
    void insert() {

        try(Connection connection = CRKFConnect.getInstance()) {

            try(Statement statement = connection.createStatement()) {

                connection.setAutoCommit(false);

                statement.executeUpdate("DELETE FROM Personne_Diplome");
                statement.executeUpdate("DELETE FROM Personne");
                statement.executeUpdate("DBCC CHECKIDENT ('Personne', RESEED, 0)");
                statement.executeUpdate("DELETE FROM Ecole");
                statement.executeUpdate("DBCC CHECKIDENT ('Ecole', RESEED, 0)");
                statement.executeUpdate("DELETE FROM Adresse");
                statement.executeUpdate("DBCC CHECKIDENT ('Adresse', RESEED, 0)");
                statement.executeUpdate("DELETE FROM Ville");
                statement.executeUpdate("DBCC CHECKIDENT ('Ville', RESEED, 0)");
                statement.executeUpdate("DELETE FROM Departement");
                statement.executeUpdate("DBCC CHECKIDENT ('Departement', RESEED, 0)");

                int departementId = 1;
                String departementNumero = "1";
                String departementLibelle = "departement_test";
                Departement departement = new Departement(departementId, departementNumero, departementLibelle);

                assertEquals(departementId, departement.getDepartementId());
                assertEquals(departementNumero, departement.getDepartementNumero());
                assertEquals(departementLibelle, departement.getDepartementLibelle());
                DepartementDAO departementDAO = new DepartementDAO(connection);
                departementDAO.insert(departement);

                try(ResultSet rs = statement.executeQuery("SELECT * FROM Departement")) {
                    assertTrue(rs.next());
                    assertEquals(departementId, rs.getInt("id_departement"));
                    assertEquals(departementNumero, rs.getString("numero_departement"));
                    assertEquals(departementLibelle, rs.getString("Departement"));
                    assertFalse(rs.next());
                }
                
                List<Departement> list = departementDAO.getAll(1);
                assertEquals(1, list.size());
                assertEquals(Departement.class, list.get(0).getClass());

            } finally {
                connection.rollback();
            }

        } catch (SQLException e) {
            fail(e.toString());
        }

    }

    @Test
    void update() {

        try(Connection connection = CRKFConnect.getInstance()) {

            try(Statement statement = connection.createStatement()) {

                connection.setAutoCommit(false);

                statement.executeUpdate("DELETE FROM Personne_Diplome");
                statement.executeUpdate("DELETE FROM Personne");
                statement.executeUpdate("DBCC CHECKIDENT ('Personne', RESEED, 0)");
                statement.executeUpdate("DELETE FROM Ecole");
                statement.executeUpdate("DBCC CHECKIDENT ('Ecole', RESEED, 0)");
                statement.executeUpdate("DELETE FROM Adresse");
                statement.executeUpdate("DBCC CHECKIDENT ('Adresse', RESEED, 0)");
                statement.executeUpdate("DELETE FROM Ville");
                statement.executeUpdate("DBCC CHECKIDENT ('Ville', RESEED, 0)");
                statement.executeUpdate("DELETE FROM Departement");
                statement.executeUpdate("DBCC CHECKIDENT ('Departement', RESEED, 0)");

                int departementId = 1;
                String departementNumero = "1";
                String departementLibelle = "departement_test";
                Departement departement = new Departement(departementId, departementNumero, departementLibelle);

                DepartementDAO departementDAO = new DepartementDAO(connection);
                departementDAO.insert(departement);

                String departementNumeroUpdated = "numero_updated";
                String departementLibelleUpdated = "libelle_updated";
                departement.setDepartementNumero(departementNumeroUpdated);
                departement.setDepartementLibelle(departementLibelleUpdated);

                departementDAO.update(departement);

                try(ResultSet rs = statement.executeQuery("SELECT * FROM Departement")) {
                    assertTrue(rs.next());
                    assertEquals(departementId, rs.getInt("id_departement"));
                    assertEquals(departementNumeroUpdated, rs.getString("numero_departement"));
                    assertEquals(departementLibelleUpdated, rs.getString("Departement"));
                    assertFalse(rs.next());
                }

            } finally {
                connection.rollback();
            }

        } catch (SQLException e) {
            fail(e.toString());
        }

    }

    @Test
    void delete() {

        try(Connection connection = CRKFConnect.getInstance()) {

            try(Statement statement = connection.createStatement()) {

                connection.setAutoCommit(false);

                statement.executeUpdate("DELETE FROM Personne_Diplome");
                statement.executeUpdate("DELETE FROM Personne");
                statement.executeUpdate("DBCC CHECKIDENT ('Personne', RESEED, 0)");
                statement.executeUpdate("DELETE FROM Ecole");
                statement.executeUpdate("DBCC CHECKIDENT ('Ecole', RESEED, 0)");
                statement.executeUpdate("DELETE FROM Adresse");
                statement.executeUpdate("DBCC CHECKIDENT ('Adresse', RESEED, 0)");
                statement.executeUpdate("DELETE FROM Ville");
                statement.executeUpdate("DBCC CHECKIDENT ('Ville', RESEED, 0)");
                statement.executeUpdate("DELETE FROM Departement");
                statement.executeUpdate("DBCC CHECKIDENT ('Departement', RESEED, 0)");

                int departementId = 1;
                String departementNumero = "1";
                String departementLibelle = "departement_test";
                Departement departement = new Departement(departementId, departementNumero, departementLibelle);

                DepartementDAO departementDAO = new DepartementDAO(connection);
                departementDAO.insert(departement);

                assertNotNull(departementDAO.getByID(1));
                assertNotNull(departementDAO.getLike("test", 1));
                assertEquals(1, departementDAO.getNumberOfDepartements("test"));
                assertEquals(1, departementDAO.getNumDepartement().size());

                departementDAO.delete(departement);

                try(ResultSet rs = statement.executeQuery("SELECT * FROM Departement")) {
                    assertFalse(rs.next());
                    assertNull(departementDAO.getByID(1));
                    assertEquals(0, departementDAO.getLike("test", 1).size());
                    assertEquals(0, departementDAO.getNumberOfDepartements(""));
                }

            } finally {
                connection.rollback();
            }

        } catch (SQLException e) {
            fail(e.toString());
        }

    }


/*

    @Test
    void insert_update_delete() {
        Departement departement = new Departement(0, "108","Test_Insert");
        int id = DAOFactory.getDepartementDAO().insert(departement);

        assertTrue( id != 0);

        departement.setId_departement(id);
        departement.setDepartement("Update");

        assertTrue(DAOFactory.getDepartementDAO().update(departement));

        assertTrue(DAOFactory.getDepartementDAO().delete(departement));
    }

    */

}