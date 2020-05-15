package repository;

import model.Session;

import java.util.Optional;

public interface SessionRepository{

    void save(Session session);

    Boolean sessionTokenExists(String sessionToken);

    Optional<Session> getUserSession(String user_name);

    Optional<Session> getSessionByToken(String token);

}
