package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Diplome;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiplomeDAO {

    protected Connection connexion;

    protected DiplomeDAO(Connection connexion){
        this.connexion = connexion;
    }

    protected Connection connexion(){
        return connexion;
    }

    public boolean deleteDiplome(int personneId, int cycleId, int instrumentId) {
        String requete = "DELETE FROM Personne_Diplome WHERE id_personne = " + personneId + " and id_libelle = " + cycleId + " and id_instrument = " + instrumentId;
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }

    public void personneAddDiplome(int id, int instrumentId, int cycleId) {
        String requete = "Insert into Personne_Diplome (id_personne, id_instrument, id_libelle) VALUES (" + id + "," + instrumentId + "," + cycleId + ")";
        try (PreparedStatement  preparedStatement = connexion.prepareStatement(requete)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Diplome> getPersonneDiplomeLike (int id, int instrumentId, int cycleId) {
        StringBuilder requete = new StringBuilder("SELECT id_libelle, instrumentId from Personne_Diplome where id_personne = " + id);
        if(cycleId != 0)
            requete.append(" and id_libelle = ").append(cycleId);
        if(instrumentId != 0)
            requete.append(" and instrumentId = ").append(instrumentId);
        ArrayList<Diplome> liste = new ArrayList<>();
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete.toString())){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) liste.add(new Diplome(rs.getInt(1), rs.getInt(2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Diplome> getDiplomeSupriorOf (int id, int instrumentId, int cycleId) {
        ArrayList<Diplome> liste = new ArrayList<>();
        String requete = "SELECT id_libelle, id_instrument from Personne_Diplome where id_personne = " + id + " and id_libelle > " + cycleId + " and id_instrument = " + instrumentId;
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) liste.add(new Diplome(rs.getInt(1), rs.getInt(2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }
}
