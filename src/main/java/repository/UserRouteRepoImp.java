package repository;

import lombok.SneakyThrows;
import model.UserRoute;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRouteRepoImp implements UserRouteRepository {
    private Connection connection;
    
    public UserRouteRepoImp(){
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
    public List<UserRoute> getRouteForUserId(Integer user_id) {
        if(connection==null)
            establishConnection();
        PreparedStatement statement = connection.prepareStatement(
                "select r.*, c2.name as 'cname' from userroute r " +
                        "inner join city c2 on r.city_id = c2.id "+
                        "where r.user_id=?"
        );
        statement.setInt(1,user_id);
        ResultSet resultSet =statement.executeQuery();
        List<UserRoute> userRoutes = new ArrayList<>();
        while (resultSet.next()) {
            userRoutes.add(
                    UserRoute.builder()
                            .id(resultSet.getInt("id"))
                            .city_id(resultSet.getInt("city_id"))
                            .user_id(resultSet.getInt("user_id"))
                            .city_name(resultSet.getString("cname"))
                            .build()
            );
        }
        resultSet.close();
        statement.close();
        return userRoutes;
    }

    @Override
    @SneakyThrows
    public Boolean addRoute(Integer user_id, Integer city_id) {
        if(connection==null)
            establishConnection();
        PreparedStatement statement = connection.prepareStatement(
                "insert into userroute(user_id, city_id) values(?,?)"
        );
        statement.setInt(1,user_id);
        statement.setInt(2,city_id);
        return statement.execute();
    }

    @Override
    @SneakyThrows
    public Boolean removeCityFromUserRoute(Integer user_id, Integer city_id) {
        if(connection==null)
            establishConnection();
        PreparedStatement statement1 = connection
                .prepareStatement("select max(r.id) as 'mid' from userroute r where r.city_id=? and r.user_id=?");
        statement1.setInt(1,city_id);
        statement1.setInt(2,user_id);
        ResultSet s = statement1.executeQuery();
        Integer id = 2000000;
        if(s.next())
            id=s.getInt("mid");
        PreparedStatement statement = connection.prepareStatement(
                "delete from userroute where id>? and user_id=?"
        );
        statement.setInt(1,id);
        statement.setInt(2,user_id);
        return statement.executeUpdate() > 0;
    }
}
