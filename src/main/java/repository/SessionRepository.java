package repository;

import model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {

    Boolean sessionTokenExists(String sessionToken);

    Optional<Session> getUserSession(String user_name);

    Optional<Session> getSessionByToken(String token);

}
