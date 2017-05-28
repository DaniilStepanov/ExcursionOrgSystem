package storage.Mappers.user;

import storage.MapperInterface;

import java.sql.SQLException;

/**
 * Created by Danya on 21.05.2017.
 */
public interface UserMapperInterface<T> extends MapperInterface<T> {
    T findByLogin(String login) throws SQLException;
}