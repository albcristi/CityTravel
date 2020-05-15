package repository;

import model.User;

import java.util.Optional;

public class UserRepositoryImp implements UserRepository {
    @Override
    public void save(User user) {

    }

    @Override
    public Optional<User> getUserByUser_name(String user_name) {
        return Optional.empty();
    }
}
