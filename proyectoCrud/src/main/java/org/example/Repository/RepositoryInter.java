package org.example.Repository;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryInter <T> {

    List<T> findAll() throws SQLException;
    T getByID(Integer id) throws SQLException;
    void save(T t) throws SQLException;
    void delete (Integer id) throws SQLException;

}
