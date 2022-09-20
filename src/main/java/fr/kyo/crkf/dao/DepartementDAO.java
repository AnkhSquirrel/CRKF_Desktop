package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.Classification;
import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ville;

import java.sql.*;
import java.util.ArrayList;

public class DepartementDAO extends DAO<Departement> {

    protected DepartementDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Departement getByID(int id) {
        try {

            // Determine the column set column

            String strCmd = "SELECT id_departement, numero_departement,departement from Departement as v where id_departement = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery();

            rs.next();
            Departement departement = new Departement(rs.getInt(1), rs.getString(2),rs.getString(3));

            rs.close();

            return departement;
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Departement> getAll(int page) {
        ArrayList<Departement> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {


            // Determine the column set column

            String strCmd = "SELECT id_departement, numero_departement,departement from Departement order by departement";
            ResultSet rs = stmt.executeQuery(strCmd);

            while (rs.next()) {
                //TO DO Add Ville
                //new Ville(rs.getInt(1), rs.getString(2))
                liste.add(new Departement(rs.getInt(1), rs.getString(2),rs.getString(3)));
            }
            rs.close();
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public ArrayList<String> getNumDepartement() {
        ArrayList<String> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {

            // Determine the column set column

            String strCmd = "SELECT numero_departement from Departement order by numero_departement";
            ResultSet rs = stmt.executeQuery(strCmd);

            while (rs.next()) {
                liste.add(rs.getString(1));
            }
            rs.close();
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public ArrayList<Departement> getLike(String departement, int page) {
        ArrayList<Departement> list = new ArrayList<>();
        try{
            String strCmd = "SELECT id_departement, numero_departement ,Departement from Departement";
            if(!departement.isEmpty())
                strCmd += " where departement like '%" + departement + "%'";
            if(page > 0)
            strCmd += " order by departement OFFSET 25 * (" + page + " - 1)  ROWS FETCH NEXT 25 ROWS ONLY";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            ResultSet rs = s.executeQuery();

            while(rs.next())
                list.add(new Departement(rs.getInt(1), rs.getString(2) , rs.getString(3)));
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public int getAllDepartement(String departement) {
        try{
            String strCmd = "SELECT COUNT(id_departement) from Departement";
            if(!departement.isEmpty())
                strCmd += " where departement like '%" + departement + "%'";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            ResultSet rs = s.executeQuery();
            rs.next();
            int departements = rs.getInt(1);
            rs.close();
            return departements;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Ville> getVilleByDepartement(int id) {
        ArrayList<Ville> liste = new ArrayList<>();
        try {
            PreparedStatement ps = connexion.prepareStatement("SELECT id_ville, ville, longitude,latitude,id_departement from Ville as V where id_departement = ? ");
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                liste.add(new Ville(rs.getInt(1), rs.getString(2),rs.getFloat(3),rs.getFloat(4) ,rs.getInt(5)));
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public int insert(Departement objet) {
        try {
            String requete = "INSERT INTO Departement (numero_departement, departement) VALUES (?,?)";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString( 1 , objet.getNumero_departement());
            preparedStatement.setString( 2 , objet.getDepartement());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id = 0;
            if(rs.next())
                id = rs.getInt(1);
            preparedStatement.close();
            return id;
        }catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean update(Departement object) {
        try {
            String requete = "UPDATE Departement SET numero_departement = ?, departement = ? WHERE id_departement = ?";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setString(1,object.getNumero_departement());
            preparedStatement.setString(2, object.getDepartement());
            preparedStatement.setInt(3, object.getId_departement());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Departement object) {
        try {
            String requete = "DELETE FROM Departement WHERE id_departement=?";
            PreparedStatement preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setInt(1, object.getId_departement());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
