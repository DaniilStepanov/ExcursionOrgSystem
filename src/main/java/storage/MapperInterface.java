package storage;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Danya on 21.05.2017.
 */
public interface MapperInterface<T> {
    T findByID(int id) throws SQLException;
    ArrayList<T> findAll() throws SQLException;
    void update(T item) throws SQLException;
    void closeConnection() throws SQLException;
    void clear();
}