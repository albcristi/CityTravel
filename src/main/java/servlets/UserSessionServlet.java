package servlets;

import model.User;
import service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class UserSessionServlet extends HttpServlet {

    private AuthenticationService authenticationService = new AuthenticationService();

    private RequestHelper requestHelper = new RequestHelper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> user = authenticationService.validateUserRequest(req);
        if(user.isPresent()){
            requestHelper.writeBody(resp, HttpServletResponse.SC_OK, user.get().toJsonFormat().toJSONString());
            return;
        }
        requestHelper.writeBody(resp, HttpServletResponse.SC_UNAUTHORIZED,"Registration Needed");
    }
}
