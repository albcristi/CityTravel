package service;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.Session;
import model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import repository.SessionRepository;
import repository.SessionRepositoryImp;
import repository.UserRepository;
import repository.UserRepositoryImp;

import java.util.Date;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationService {


    UserRepository userRepository = new UserRepositoryImp();


    SessionRepository sessionRepository = new SessionRepositoryImp();


    public String generateSessionToken(){
        String token = "";
        Boolean not_unique = true;
        while (not_unique){
            token = RandomStringUtils.randomAlphabetic(255);
            if(!sessionRepository.sessionTokenExists(token))
                not_unique = false;
        }
        return token;
    }

    public boolean log_in(String user_name, String password){
        Optional<User> usr = userRepository.getUserByUser_name(user_name);
        if(!usr.isPresent())
            return false;
        return usr.get().getUser_password().equals(password);
    }

    public boolean sign_up(String user_name, String password){
        if(userRepository.getUserByUser_name(user_name).isPresent())
            return false;
        User newUser = User.builder()
                .user_name(user_name)
                .user_password(password)
                .build();
        userRepository.save(newUser);
        return true;
    }

    public String createUserSession(String userName){
        Optional<User> user = userRepository.getUserByUser_name(userName);
        if(user.isEmpty())
            return "ERROR";
        Optional<Session> sessionOptional = sessionRepository.getUserSession(userName);
        if(sessionOptional.isPresent()){
            Session session = sessionOptional.get();
            session.setToken(generateSessionToken());
            session.setTtl(DateUtils.addHours(new Date(),2));
            sessionRepository.save(session);
            return session.getToken();
        }
        Session session = Session.builder()
                .user_id(user.get().getId())
                .token(generateSessionToken())
                .ttl(DateUtils.addHours(new Date(), 2))
                .build();
        sessionRepository.save(session);
        return session.getToken();
    }
}
