package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.Diplome;
import fr.kyo.crkf.Entity.Personne;
import fr.kyo.crkf.Searchable.SearchableProfesseur;
import java.sql.*;
import java.util.ArrayList;

public class PersonneDAO extends DAO<Personne> {
    protected PersonneDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Personne getByID(int id) {
        Personne personne = null;
        try {

            //Search the Personne in the Data Base
            String strCmd = "select id_personne,Nom,Prenom,VehiculeCV,id_adresse,id_ecole from Personne where id_personne = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery();

            rs.next();
            personne = new Personne(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5), rs.getInt(6));
            rs.close();

            //Search the affiliate Diplome
            String strCmd2 = "select id_libelle, id_instrument from Personne_Diplome where id_personne = ?";
            PreparedStatement s2 = connexion.prepareStatement(strCmd2);
            s2.setInt(1,id);
            ResultSet rs2 = s2.executeQuery();

            while (rs2.next()){;
                personne.addDiplome(new Diplome(rs2.getInt(1),rs2.getInt(2)));
            }
            rs2.close();

        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return personne;
    }

    @Override
    public ArrayList<Personne> getAll(int page) {
        ArrayList<Personne> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {

            // Determine the column set column

            String strCmd = "SELECT id_personne,Nom,Prenom,VehiculeCV,id_adresse,id_ecole from Personne order by nom, prenom asc";
            ResultSet rs = stmt.executeQuery(strCmd);


            while (rs.next()) {
                int id = rs.getInt(1);
                Personne personne = new Personne(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5), rs.getInt(6));

                //Search the affiliate Diplome
                String strCmd2 = "select id_libelle, id_instrument from Personne_Diplome where id_personne = ?";
                PreparedStatement s2 = connexion.prepareStatement(strCmd2);
                s2.setInt(1,id);
                ResultSet rs2 = s2.executeQuery();

                while (rs2.next()){;
                    personne.addDiplome(new Diplome(rs2.getInt(1),rs2.getInt(2)));
                }
                rs2.close();
                liste.add(personne);
            }
            rs.close();
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public ArrayList<Personne> getLike(SearchableProfesseur searchableProfesseur, int page) {
        ArrayList<Personne> liste = new ArrayList<>();
        try {
            String strCmd = "exec SP_PROFESSEUR_FILTER  @nometprenom = ?, @vehiculecv = ?, @idville = ?, @iddepartement = ?, @lgpage = 25, @page = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setString(1,searchableProfesseur.getNomEtPrenom());
            s.setInt(2,searchableProfesseur.getVehiculeCV());
            s.setInt(3,searchableProfesseur.getVilleId());
            s.setInt(4,searchableProfesseur.getDepartementId());
            s.setInt(5,page);
            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                Personne personne = new Personne();
                int id = rs.getInt(1);
                personne.setId_personne(id);
                personne.setNom(rs.getString(2));
                personne.setPrenom(rs.getString(3));
                personne.setVehiculeCv(rs.getInt(4));
                personne.setAdresse(DAOFactory.getAdresseDAO().getByID(rs.getInt(5)));
                personne.setEcole(DAOFactory.getEcoleDAO().getByID(rs.getInt(6)));

                //Search the affiliate Diplome
                String strCmd2 = "select id_libelle, id_instrument from Personne_Diplome where id_personne = ?";
                PreparedStatement s2 = connexion.prepareStatement(strCmd2);
                s2.setInt(1,id);
                ResultSet rs2 = s2.executeQuery();

                while (rs2.next()){
                    personne.addDiplome(new Diplome(rs2.getInt(1),rs2.getInt(2)));
                }
                rs2.close();
                liste.add(personne);
            }
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public ArrayList<Personne> getByEcole (int id_ecole) {
        ArrayList<Personne> liste = new ArrayList<>();
        try {
            String strCmd = "SELECT nom, prenom from Personne where id_ecole = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1, id_ecole);
            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                Personne personne = new Personne();
                personne.setNom(rs.getString(1));
                personne.setPrenom(rs.getString(2));

                liste.add(personne);
            }
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public int insert(Personne objet) {
        try {
            String requete = "INSERT INTO Personne (Nom,Prenom,VehiculeCV,id_adresse,id_ecole) VALUES (?,?,?,?,?)";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString( 1 , objet.getNom());
            preparedStatement.setString(2,objet.getPrenom());
            preparedStatement.setInt(3,objet.getVehiculeCv());
            preparedStatement.setInt(4,objet.getAdresse().getId_adresse());
            preparedStatement.setInt(5,objet.getEcole().getId_ecole());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id = 0;
            if(rs.next())
                id = rs.getInt(1);
            preparedStatement.close();

            for(Diplome diplome : objet.getDiplomes()){
                String requete2 = "INSERT INTO Personne_Diplome (id_cycle,id_personne,id_instrument) VALUES (?,?,?)";
                PreparedStatement  preparedStatement2 = connexion().prepareStatement(requete2, Statement.RETURN_GENERATED_KEYS);
                preparedStatement2.setInt( 1, id);
                preparedStatement2.setInt(2, diplome.getCycle().getId_cycle());
                preparedStatement2.setInt(3, diplome.getInstrument().getId_instrument());
                preparedStatement2.executeUpdate();
                preparedStatement2.close();
            }
            return id;
        }catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean update(Personne object) {
        try {
            String requete = "UPDATE Personne SET Nom = ?, Prenom = ?, VehiculeCV = ?, id_adresse = ?, id_ecole = ? WHERE id_personne = ?";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setString(1, object.getNom());
            preparedStatement.setString(2, object.getPrenom());
            preparedStatement.setInt(3, object.getVehiculeCv());
            preparedStatement.setInt(4, object.getAdresse().getId_adresse());
            preparedStatement.setInt(5, object.getEcole().getId_ecole());
            preparedStatement.setInt(6, object.getId_personne());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Personne object) {
        try {
            String requete = "DELETE FROM Personne WHERE id_personne=?";
            PreparedStatement preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setInt(1, object.getId_personne());
            preparedStatement.executeUpdate();

            String requete2 = "DELETE FROM Personne_Diplome WHERE id_personne=?";
            PreparedStatement preparedStatement2 = connexion().prepareStatement(requete2);
            preparedStatement2.setInt(1, object.getId_personne());
            preparedStatement2.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
