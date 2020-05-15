package repository;

import lombok.SneakyThrows;
import model.Session;

import java.sql.*;
import java.util.List;
import java.util.Optional;



public class SessionRepositoryImp implements SessionRepository{

    private Connection connection;

    public SessionRepositoryImp(){
        establishConnection();
    }

    private void establishConnection() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/city_travel",
                    "root","password");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Override
    public void save(Session session) {
        if(connection == null)
            establishConnection();
        if(getSessionByUserId(session.getUser_id()).isPresent()){
            PreparedStatement preparedStatement = connection.prepareStatement("update session " +
                    "set user_id=?, ttl=?,token=?" +
                    "where id=?");
            preparedStatement.setInt(1,session.getUser_id());
            preparedStatement.setDate(2, (Date) session.getTtl());
            preparedStatement.setString(3,session.getToken());
            preparedStatement.setInt(4,session.getId());
            preparedStatement.execute();
            return;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("insert into session(user_id, token, ttl) values (?,?,?)");
        preparedStatement.setInt(1,session.getUser_id());
        preparedStatement.setString(2,session.getToken());
        preparedStatement.setDate(3, (Date) session.getTtl());
        preparedStatement.execute();
    }

    @Override
    @SneakyThrows
    public  Boolean sessionTokenExists(String sessionToken){
        if(connection == null)
            establishConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from session where token=?");
        preparedStatement.setString(1,sessionToken);
        ResultSet set = preparedStatement.executeQuery();
        return set.next();
    }

    @Override
    @SneakyThrows
    public Optional<Session>  getUserSession(String user_name){
        if(connection == null)
            establishConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select s from session s " +
                "inner join user u on s.user_id = u.id " +
                "where u.user_name=?");
        preparedStatement.setString(1,user_name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            Session session = Session.builder()
                    .id(resultSet.getInt("id"))
                    .user_id(resultSet.getInt("user_id"))
                    .ttl(resultSet.getDate("ttl"))
                    .token(resultSet.getString("token"))
                    .build();
            return Optional.of(session);
        }
        return Optional.empty();
    }

    @Override
    @SneakyThrows
    public Optional<Session> getSessionByToken(String token){
        if(connection == null)
            establishConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from  session where token=?");
        preparedStatement.setString(1,token);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            Session session = Session.builder()
                    .id(resultSet.getInt("id"))
                    .user_id(resultSet.getInt("user_id"))
                    .ttl(resultSet.getDate("ttl"))
                    .token(resultSet.getString("token"))
                    .build();
            return Optional.of(session);
        }
        return Optional.empty();
    }

    @SneakyThrows
    private Optional<Session> getSessionByUserId(Integer user_id){
        if(connection == null)
            establishConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from  session where user_id=?");
        preparedStatement.setInt(1,user_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            Session session = Session.builder()
                    .id(resultSet.getInt("id"))
                    .user_id(resultSet.getInt("user_id"))
                    .ttl(resultSet.getDate("ttl"))
                    .token(resultSet.getString("token"))
                    .build();
            return Optional.of(session);
        }
        return Optional.empty();
    }

}
