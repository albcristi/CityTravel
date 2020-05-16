package servlets;

import model.City;
import model.User;
import org.json.simple.JSONArray;
import service.AuthenticationService;
import service.CityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class CityServlet extends HttpServlet {
    private AuthenticationService authenticationService = new AuthenticationService();

    private CityService cityService = new CityService();

    private RequestHelper requestHelper = new RequestHelper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> sessionUser = authenticationService.validateUserRequest(req);
        if(sessionUser.isPresent()){
            // we have a user, yay
            JSONArray array = new JSONArray();
            cityService.getCities()
                    .stream()
                    .map(City::toJsonFormat)
                    .forEach(array::add);
            requestHelper.writeBody(resp, HttpServletResponse.SC_OK,array.toJSONString());
        }
        else{
            requestHelper.writeUnauthorizedResponse(resp);
        }
    }
}
