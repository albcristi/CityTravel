package repository;

import lombok.SneakyThrows;
import model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NeighbourCityImplementation implements NeighbourCityRepository {
    private Connection connection;

    public NeighbourCityImplementation(){
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
    public List<City> getCityNeighbours(Integer city_id) {
        if(connection == null)
            establishConnection();
        //retrieve cities that are neighbours of the
        //city having id=city_id
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select c.* from neighbourcity n "+
                "inner join city c on n.neighbour_city = c.id "+
                "where n.origin_city = ?"
        );
        preparedStatement.setInt(1,city_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<City> resultList = new ArrayList<>();
        while (resultSet.next()){
              resultList.add(
                      City.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build());
        }
        resultSet.close();
        preparedStatement.close();
        return resultList;
    }
}
