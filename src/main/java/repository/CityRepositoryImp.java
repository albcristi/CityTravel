package repository;

import lombok.SneakyThrows;
import model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityRepositoryImp implements CityRepository {
    private Connection connection;

    public CityRepositoryImp(){
        establishConnection();
    }

    private void establishConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/city_travel",
                    "root","password");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public List<City> getAll() {
        if(connection==null)
            establishConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from city;");
        ResultSet set = preparedStatement.executeQuery();
        List<City> list = new ArrayList<>();
        while (set.next()){
            list.add(City.builder()
                    .id(set.getInt("id"))
                    .name(set.getString("name"))
                    .build());
        }
        set.close();
        preparedStatement.close();
        return list;
    }
}
