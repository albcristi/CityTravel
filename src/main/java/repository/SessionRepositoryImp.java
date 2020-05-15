package repository;

import model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


@Component
public class SessionRepositoryImp{

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SessionRepository sessionRepository;

    public  Boolean sessionTokenExists(String sessionToken){
        String sql_string = "select s from session s where s.token = :stoken";
        TypedQuery<Session> select_query = entityManager.createQuery(sql_string, Session.class);
        select_query.setParameter("stoken", sessionToken);
        return select_query.getResultList().size() > 0;
    }

    public Optional<Session>  getUserSession(String user_name){
        String sql_string = "select s from session s where s.user_name=:usr_id";
        TypedQuery<Session> select_query = entityManager.createQuery(sql_string, Session.class);
        select_query.setParameter("usr_id", user_name);
        List<Session>  result = select_query.getResultList();
        if(result.size()==0)
            return Optional.empty();
        return Optional.of(result.get(0));
    }

    public Optional<Session> getSessionByToken(String token){
        String sql_string = "select s from session s where s.token=:usr_token";
        TypedQuery<Session> select_query = entityManager.createQuery(sql_string, Session.class);
        select_query.setParameter("usr_token", token);
        List<Session>  result = select_query.getResultList();
        if(result.size()==0)
            return Optional.empty();
        return Optional.of(result.get(0));
    }
}
