package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Famille;
import fr.kyo.crkf.entity.Instrument;
import fr.kyo.crkf.searchable.SearchableInstrument;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstrumentDAO extends DAO<Instrument> {
    
    protected InstrumentDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Instrument getByID(int id) {
        String requete = "select id_instrument,Nom from Instrument where id_instrument = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                Instrument instrument = new Instrument(rs.getInt(1), rs.getString(2));
                giveFamillesToInstrument(id, instrument);
                return instrument;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Instrument> getAll(int page) {
        List<Instrument> liste = new ArrayList<>();
        String strCmd = "SELECT id_instrument, Nom from Instrument order by Nom";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(strCmd);
            while (rs.next()) {
                Instrument instrument = (new Instrument(rs.getInt(1), rs.getString(2)));
                giveFamillesToInstrument(rs.getInt(1), instrument);
                liste.add(instrument);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Instrument> getLike(SearchableInstrument searchableInstrument, int page) {
        String strCmd = "exec SP_INSTRUMENT_FILTER  @nom = ?, @famille = ?, @classification = ?, @lgpage = 25, @page = ?";
        List<Instrument> liste = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(strCmd);){
            preparedStatement.setString(1,searchableInstrument.getNom());
            preparedStatement.setInt(2,searchableInstrument.getFamilleId());
            preparedStatement.setInt(3,searchableInstrument.getClassificationId());
            preparedStatement.setInt(4, page);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Instrument instrument = (new Instrument(rs.getInt(1), rs.getString(2)));
                giveFamillesToInstrument(rs.getInt(1), instrument);
                liste.add(instrument);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Instrument> getLikeAllInstrument(SearchableInstrument searchableInstrument) {
        List<Instrument> liste = new ArrayList<>();
        String strCmd = "exec SP_INSTRUMENT_FILTER  @nom = ?, @famille = ?, @classification = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(strCmd)){
            preparedStatement.setString(1,searchableInstrument.getNom());
            preparedStatement.setInt(2,searchableInstrument.getFamilleId());
            preparedStatement.setInt(3,searchableInstrument.getClassificationId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Instrument instrument = (new Instrument(rs.getInt(1), rs.getString(2)));
                giveFamillesToInstrument(rs.getInt(1), instrument);
                liste.add(instrument);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }


    @Override
    public int insert(Instrument objet) {
        int id = 0;
        String requete = "INSERT INTO Instrument (Nom) VALUES (?)";
        try (PreparedStatement  preparedStatement = connection.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString( 1 , objet.getInstrumentLibelle());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) id = rs.getInt(1);
        } catch (SQLException e) {
           e.printStackTrace();
            return 0;
        }
        for(Famille famille : objet.getFamilles()){
            String requete2 = "INSERT INTO Instrument_Famille (id_instrument,id_famille) VALUES (?,?)";
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(requete2, Statement.RETURN_GENERATED_KEYS)){
                preparedStatement2.setInt( 1, id);
                preparedStatement2.setInt(2, famille.getFamilleId());
                preparedStatement2.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
                return 0;
            }
        }
        return id;
    }

    @Override
    public boolean update(Instrument object) {
        String requete = "DELETE FROM Instrument_Famille WHERE id_instrument=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            preparedStatement.setInt(1, object.getInstrumentId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        String requete1 = "UPDATE Instrument SET Nom = ? WHERE id_instrument = ?";
        try (PreparedStatement preparedStatement1 = connection.prepareStatement(requete1)){
            preparedStatement1.setString(1, object.getInstrumentLibelle());
            preparedStatement1.setInt(2, object.getInstrumentId());
            preparedStatement1.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        String requete2 = "INSERT INTO Instrument_Famille (id_instrument,id_famille) VALUES (?,?)";
        for(Famille famille : object.getFamilles()){
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(requete2)){
                preparedStatement2.setInt( 1, object.getInstrumentId());
                preparedStatement2.setInt(2, famille.getFamilleId());
                preparedStatement2.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean delete(Instrument object) {
        String requete = "DELETE FROM Instrument_Famille WHERE id_instrument=?";
        try (PreparedStatement preparedStatement2 = connection.prepareStatement(requete);){
            preparedStatement2.setInt(1, object.getInstrumentId());
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        String requete2 = "DELETE FROM Instrument WHERE id_instrument=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete2);){
            preparedStatement.setInt(1, object.getInstrumentId());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void giveFamillesToInstrument(int id, Instrument instrument) {
        String requete = "select id_famille from Instrument_Famille where id_instrument = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) instrument.addFamille(DAOFactory.getFamilleDAO().getByID(rs.getInt(1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
