package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.Famille;
import fr.kyo.crkf.Entity.Instrument;
import fr.kyo.crkf.Searchable.SearchableInstrument;

import java.sql.*;
import java.util.ArrayList;

public class InstrumentDAO extends DAO<Instrument> {
    protected InstrumentDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Instrument getByID(int id) {
        Instrument instrument = null;
        try {

            //Search the Instrument in the Data Base
            String strCmd = "select id_instrument,Nom from Instrument where id_instrument = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery();

            rs.next();
            instrument =  new Instrument(rs.getInt(1), rs.getString(2));
            rs.close();

            //Search the affiliate Famille
            String strCmd2 = "select id_famille from Instrument_Famille where id_instrument = ?";
            PreparedStatement s2 = connexion.prepareStatement(strCmd2);
            s2.setInt(1,id);
            ResultSet rs2 = s2.executeQuery();

            while (rs2.next()){
                instrument.addFamille(DAOFactory.getFamilleDAO().getByID(rs2.getInt(1)));
            }
            rs2.close();

        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return instrument;
    }

    @Override
    public ArrayList<Instrument> getAll(int page) {
        ArrayList<Instrument> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {

            // Determine the column set column

            String strCmd = "SELECT id_instrument, Nom from Instrument order by Nom";
            ResultSet rs = stmt.executeQuery(strCmd);

            while (rs.next()) {
                int id = rs.getInt(1);
                Instrument instrument = (new Instrument(id,rs.getString(2)));

                //Search the affiliate Famille
                String strCmd2 = "select id_famille from Instrument_Famille where id_instrument = ?";
                PreparedStatement s2 = connexion.prepareStatement(strCmd2);
                s2.setInt(1,id);
                ResultSet rs2 = s2.executeQuery();

                while (rs2.next()){
                    instrument.addFamille(DAOFactory.getFamilleDAO().getByID(rs2.getInt(1)));
                }
                rs2.close();
                liste.add(instrument);
            }
            rs.close();
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public ArrayList<Instrument> getLike(SearchableInstrument searchableInstrument, int page) {
        ArrayList<Instrument> liste = new ArrayList<>();
        try {

            // Determine the column set column

            String strCmd = "exec SP_INSTRUMENT_FILTER  @nom = ?, @famille = ?, @classification = ?, @lgpage = 25, @page = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setString(1,searchableInstrument.getNom());
            s.setInt(2,searchableInstrument.getFamilleId());
            s.setInt(3,searchableInstrument.getClassificationId());
            s.setInt(4, page);
            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                Instrument instrument = (new Instrument(id,rs.getString(2)));

                //Search the affiliate Famille
                String strCmd2 = "select id_famille from Instrument_Famille where id_instrument = ?";
                PreparedStatement s2 = connexion.prepareStatement(strCmd2);
                s2.setInt(1,id);
                ResultSet rs2 = s2.executeQuery();

                while (rs2.next()){
                    instrument.addFamille(DAOFactory.getFamilleDAO().getByID(rs2.getInt(1)));
                }
                rs2.close();
                liste.add(instrument);
            }
            rs.close();
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public int insert(Instrument objet) {
        try {
            String requete = "INSERT INTO Instrument (Nom) VALUES (?)";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString( 1 , objet.getNom());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id = 0;
            if(rs.next())
                id = rs.getInt(1);
            preparedStatement.close();

            for(Famille famille : objet.getFamilles()){
                String requete2 = "INSERT INTO Instrument_Famille (id_instrument,id_famille) VALUES (?,?)";
                PreparedStatement  preparedStatement2 = connexion().prepareStatement(requete2, Statement.RETURN_GENERATED_KEYS);
                preparedStatement2.setInt( 1, id);
                preparedStatement2.setInt(2, famille.getId_famille());
                preparedStatement2.executeUpdate();
                preparedStatement2.close();
            }
            return id;
        }catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean update(Instrument object) {
        try {
            String requete = "DELETE FROM Instrument_Famille WHERE id_instrument=?";
            PreparedStatement preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setInt(1, object.getId_instrument());
            preparedStatement.executeUpdate();

            String requete1 = "UPDATE Instrument SET Nom = ? WHERE id_instrument = ?";
            PreparedStatement  preparedStatement1 = connexion().prepareStatement(requete1);
            preparedStatement1.setString(1, object.getNom());
            preparedStatement1.setInt(2, object.getId_instrument());
            preparedStatement1.executeUpdate();
            preparedStatement1.close();

            for(Famille famille : object.getFamilles()){
                String requete2 = "INSERT INTO Instrument_Famille (id_instrument,id_famille) VALUES (?,?)";
                PreparedStatement  preparedStatement2 = connexion().prepareStatement(requete2);
                preparedStatement2.setInt( 1, object.getId_instrument());
                preparedStatement2.setInt(2, famille.getId_famille());
                preparedStatement2.executeUpdate();
                preparedStatement2.close();
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Instrument object) {
        try {
            String requete2 = "DELETE FROM Instrument_Famille WHERE id_instrument=?";
            PreparedStatement preparedStatement2 = connexion().prepareStatement(requete2);
            preparedStatement2.setInt(1, object.getId_instrument());
            preparedStatement2.executeUpdate();

            String requete = "DELETE FROM Instrument WHERE id_instrument=?";
            PreparedStatement preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setInt(1, object.getId_instrument());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
