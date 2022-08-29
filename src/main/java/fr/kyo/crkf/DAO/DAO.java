package fr.kyo.crkf.DAO;

import java.sql.Connection;
import java.util.ArrayList;

abstract class DAO<T> {
    protected Connection connexion;

    protected DAO(Connection connexion)
    {
        this.connexion = connexion;
    }

    protected Connection connexion(){
        return connexion;
    }

    public abstract T getByID(int id);
    public abstract ArrayList<T> getAll();
    public abstract ArrayList <T> getLike(T objet);
    public abstract boolean insert(T objet);
    public abstract boolean update(T object);
    public abstract boolean delete( T object);
}
