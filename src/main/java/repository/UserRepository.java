package repository;

import model.User;


import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> getUserByUser_name(String user_name);
}
