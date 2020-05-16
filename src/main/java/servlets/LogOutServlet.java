package servlets;

import model.User;
import service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class LogOutServlet extends HttpServlet {
    private RequestHelper requestHelper = new RequestHelper();

    private AuthenticationService authenticationService = new AuthenticationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> userRequest = authenticationService.validateUserRequest(req);
        if(userRequest.isPresent()){
            Cookie finishSession = new Cookie("session","doneWithThis");
            finishSession.setMaxAge(0);
            resp.addCookie(finishSession);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        else
            requestHelper.writeBody(resp, HttpServletResponse.SC_UNAUTHORIZED,"Authenticate yourself");
    }
}
