package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Cycle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CycleDAO extends DAO<Cycle> {

    protected CycleDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Cycle getByID(int id) {
        String requete = "SELECT id_libelle, libelle, cycle from Cycle where id_libelle = ?";
        return getCycle(id, requete);
    }

    @Override
    public ArrayList<Cycle> getAll(int page) {
        ArrayList<Cycle> liste = new ArrayList<>();
        String requete = "SELECT id_libelle, libelle, cycle from Cycle order by cycle";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) liste.add(new Cycle(rs.getInt(1), rs.getString(2), rs.getInt(3)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Cycle> getLike(String cycle, int page) {
        ArrayList<Cycle> list = new ArrayList<>();
        StringBuilder requete = new StringBuilder("SELECT id_libelle, libelle, cycle from Cycle");
        if(!cycle.isEmpty())
            requete.append(" where cycle like '%").append(cycle).append("%'");
        requete.append(" order by cycle OFFSET 25 * (").append(page).append(" - 1)  ROWS FETCH NEXT 25 ROWS ONLY");
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete.toString())){
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) list.add(new Cycle(rs.getInt(1),rs.getString(2), rs.getInt(3)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getLikeAllCycle(String cycle) {
        StringBuilder requete = new StringBuilder("SELECT COUNT(id_libelle) from Cycle");
        if(!cycle.isEmpty())
            requete.append(" where cycle like '%").append(cycle).append("%'");
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete.toString())){
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getHighestCycle() {
        String requete = "SELECT MAX(cycle) from Cycle";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int insert(Cycle objet) {
        String requete = "INSERT INTO Cycle (libelle,cycle) VALUES (?,?)";
        try (PreparedStatement  preparedStatement = connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString( 1 , objet.getCycleLibelle());
            preparedStatement.setInt(2, objet.getCycleNumero());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean update(Cycle object) {
        String requete = "UPDATE Cycle SET libelle = ?, cycle = ? WHERE id_libelle = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            preparedStatement.setString(1, object.getCycleLibelle());
            preparedStatement.setInt(2, object.getCycleNumero());
            preparedStatement.setInt(3, object.getCycleId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Cycle object) {
        String requete = "DELETE FROM Cycle WHERE id_libelle=?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            preparedStatement.setInt(1, object.getCycleId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Cycle getByCycle(int i) {
        String strCmd = "SELECT id_libelle, libelle, cycle from Cycle where cycle = ?";
        return getCycle(i, strCmd);
    }

    private Cycle getCycle(int i, String strCmd) {
        try (PreparedStatement preparedStatement = connexion.prepareStatement(strCmd)){
            preparedStatement.setInt(1,i);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return new Cycle(rs.getInt(1), rs.getString(2), rs.getInt(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
