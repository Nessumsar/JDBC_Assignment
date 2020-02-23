package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.Database.getConnection;

public class CityDaoJDBC implements CityDao {

    private static final String FIND_BY_ID = "SELECT*FROM city WHERE city.ID = ?";
    private static final String FIND_BY_COUNTRY_CODE = "SELECT*FROM city WHERE city.CountryCode = ?";
    private static final String FIND_BY_NAME = "SELECT*FROM city WHERE city.Name = ?";
    private static final String FIND_ALL = "SELECT*FROM city";
    private static final String INSERT = "INSERT INTO city (Name,CountryCode,District,Population) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE city SET Name=?,CountryCode=?,District=?,Population=? WHERE city.ID=?";
    private static final String DELETE = "DELETE FROM city WHERE city.ID = ?";

    @Override
    public City findById(int id) throws SQLException {
        City city;
        Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
        statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();

            rs.next();
            city = createCityFromResultSet(rs);


        return city;
    }

    @Override
    public List<City> findByCode(String code) {
        List<City> cityList = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_COUNTRY_CODE)
        ){
            statement.setString(1,code);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                cityList.add(createCityFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cityList;
    }

    private PreparedStatement create_findByName(Connection connection, String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME);
        statement.setString(1,name);
        return statement;
    }
    @Override
    public List<City> findByName(String name) {
        List<City> city = new ArrayList<>();
        try(
                PreparedStatement statement = create_findByName(getConnection(), name);
                ResultSet rs = statement.executeQuery()
        )
        {
            while(rs.next()){
                city.add(createCityFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public List<City> findAll() throws SQLException {
        List<City> city = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL);
            ResultSet rs = statement.executeQuery())
        {
            city.add(createCityFromResultSet(rs));
            while (rs.next()){
                city.add(createCityFromResultSet(rs));
            }
        }
        return city;
    }

    @Override
    public City add(City city) {
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet keySet = null;
            try{
                connection = getConnection();
                statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, city.getName());
                statement.setString(2, city.getCountryCode());
                statement.setString(3, city.getDistrict());
                statement.setInt(4, city.getPopulation());
                statement.execute();
                keySet = statement.getGeneratedKeys();
                while(keySet.next()){
                    city = new City(
                            keySet.getInt(1),
                            city.getName(),
                            city.getCountryCode(),
                            city.getDistrict(),
                            city.getPopulation()
                    );
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try{
                    if(keySet != null){
                        keySet.close();
                    }
                    if(statement != null){
                        statement.close();
                    }
                    if(connection != null){
                        connection.close();
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            return city;
    }

    @Override
    public City update(City city) {
            try(Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE))
            {
                statement.setString(1, city.getName());
                statement.setString(2, city.getCountryCode());
                statement.setString(3, city.getDistrict());
                statement.setInt(4, city.getPopulation());
                statement.setInt(5, city.getId());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return city;
    }

    @Override
    public int delete(City city) throws NullPointerException, SQLException {
        int deletedCities = 0;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE)
        ){
                statement.setInt(1,city.getId());
                deletedCities = statement.executeUpdate();
                statement.execute();

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return deletedCities;
    }


    private City createCityFromResultSet(ResultSet rs) throws NullPointerException, SQLException {
        while(rs.next()) {
                return new City(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5));
        }
        return null;
    }

}
