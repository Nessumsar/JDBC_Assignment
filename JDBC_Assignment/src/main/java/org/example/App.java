package org.example;


import java.sql.SQLException;

public class App
{
    public static void main( String[] args ) throws SQLException {
            CityDaoJDBC cityDaoJDBC = new CityDaoJDBC();

            // cityDaoJDBC.add(new City("Hej", "SWE", "Kalmar", 10000));
            // cityDaoJDBC.update(new City(4079, "Rafah", "PSE", "Rafah", 92020));
            // cityDaoJDBC.delete(new City(id));
            // System.out.println(cityDaoJDBC.findById(1).toString());
            // System.out.println(cityDaoJDBC.findAll());
            //  System.out.println(cityDaoJDBC.findByCode("SWE"));
            //  System.out.println(cityDaoJDBC.findByName("Los Angeles"));  //ger bara en stad, ska ge två
    }
}
