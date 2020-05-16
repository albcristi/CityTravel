package repository;

import lombok.SneakyThrows;
import model.User;

import java.sql.*;
import java.util.Optional;

public class UserRepositoryImp implements UserRepository {
    private Connection connection;

    public UserRepositoryImp(){
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
    public void save(User user) {
        if(connection==null) {
            establishConnection();
        }
        PreparedStatement statement = connection.prepareStatement(
                "insert into user(user_name, user_password) values" +
                        "(?,?)"
        );
        statement.setString(1,user.getUser_name());
        statement.setString(2,user.getUser_password());
        statement.execute();
        statement.close();
    }

    @Override
    @SneakyThrows
    public Optional<User> getUserByUser_name(String user_name) {
        if(connection==null)
            establishConnection();
        PreparedStatement statement = connection
                .prepareStatement("select * from user where user_name=?");
        statement.setString(1,user_name);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            User user = User.builder()
                    .id(resultSet.getInt("id"))
                    .user_name(resultSet.getString("user_name"))
                    .user_password(resultSet.getString("user_password"))
                    .build();
            resultSet.close();
            statement.close();
            return Optional.of(user);
        }
        resultSet.close();
        statement.close();
        return Optional.empty();
    }
}
