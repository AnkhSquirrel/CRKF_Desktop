package fr.kyo.crkf.dao;

import java.sql.Connection;
import java.util.List;

abstract class DAO<T> {

    protected Connection connexion;

    protected DAO(Connection connexion) {
        this.connexion = connexion;
    }

    protected Connection connexion(){
        return connexion;
    }

    public abstract T getByID(int id);

    public abstract List<T> getAll(int page);

    public abstract int insert(T objet);

    public abstract boolean update(T object);

    public abstract boolean delete( T object);

}
