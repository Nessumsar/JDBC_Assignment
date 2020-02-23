package org.example;

import java.sql.SQLException;
import java.util.List;

public interface CityDao {
    City findById(int id) throws SQLException;
    List<City> findByCode(String code) throws SQLException;
    List<City> findByName(String name) throws SQLException;
    List<City> findAll() throws SQLException;
    City add(City city);
    City update(City city);
    int delete(City city) throws SQLException;
}
