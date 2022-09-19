package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.Diplome;

import java.sql.*;
import java.util.ArrayList;

public class DiplomeDAO {
    protected Connection connexion;

    protected DiplomeDAO(Connection connexion)
    {
        this.connexion = connexion;
    }

    protected Connection connexion(){
        return connexion;
    }

    public boolean deleteDiplome(int id, int id_cycle, int id_instrument) {
        try {
            String requete = "DELETE FROM Personne_Diplome WHERE id_personne = " + id + " and id_libelle = " + id_cycle + " and id_instrument = " + id_instrument;
            PreparedStatement preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean personneAddDiplome(int id, int id_instrument, int id_cycle) {
        try {
            String requete =  "Insert into Personne_Diplome (id_personne, id_instrument, id_libelle) VALUES (" + id + "," + id_instrument + "," + id_cycle + ")";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public ArrayList<Diplome> getPersonneDiplomeLike (int id, int id_instrument, int id_cycle) {
        ArrayList<Diplome> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()){

            String strCmd = "SELECT id_libelle, id_instrument from Personne_Diplome where id_personne = " + id;
            if(id_cycle != 0)
                strCmd += " and id_libelle = " + id_cycle;
            if(id_instrument != 0)
                strCmd += " and id_instrument = " + id_instrument;
            ResultSet rs = stmt.executeQuery(strCmd);

            while (rs.next()) {
                Diplome diplome = new Diplome(rs.getInt(1), rs.getInt(2));
                liste.add(diplome);
            }
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public ArrayList<Diplome> getDiplomeSupriorOf (int id, int id_instrument, int id_cycle) {
        ArrayList<Diplome> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()){

            String strCmd = "SELECT id_libelle, id_instrument from Personne_Diplome where id_personne = " + id + " and id_libelle > " + id_cycle + " and id_instrument = " + id_instrument;
            ResultSet rs = stmt.executeQuery(strCmd);

            while (rs.next()) {
                Diplome diplome = new Diplome(rs.getInt(1), rs.getInt(2));
                liste.add(diplome);
            }
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }
}
