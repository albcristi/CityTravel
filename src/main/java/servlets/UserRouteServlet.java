package servlets;

import model.User;
import model.UserRoute;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import service.AuthenticationService;
import service.UserRouteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class UserRouteServlet extends HttpServlet {
    private UserRouteService userRouteService = new UserRouteService();

    private AuthenticationService authenticationService = new AuthenticationService();

    private RequestHelper requestHelper = new RequestHelper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> sessionUser = authenticationService.validateUserRequest(req);
        if(sessionUser.isPresent()){
            //yay..
            JSONArray jsonArray = new JSONArray();
            System.out.println(sessionUser.get());
            userRouteService.getUserRoutes(sessionUser.get().getId())
                    .stream()
                    .map(UserRoute::toJsonFormat)
                    .forEach(jsonArray::add);
            requestHelper.writeBody(resp, HttpServletResponse.SC_OK,jsonArray.toJSONString());
        }
        else {
            requestHelper.writeUnauthorizedResponse(resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> sessionUser = authenticationService.validateUserRequest(req);
        if(sessionUser.isPresent()){
            //yay..
            JSONObject reqBody = requestHelper.readRequestBody(req);
            Integer city_id = Integer.parseInt( (String) reqBody.get("city_id"));
            Boolean res = userRouteService.saveRoute(sessionUser.get().getId(), city_id);
            requestHelper.writeBody(resp, HttpServletResponse.SC_OK,res.toString());

        }
        else {
            requestHelper.writeUnauthorizedResponse(resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> userSession = authenticationService.validateUserRequest(req);
        if(userSession.isPresent()){
            System.out.println(req.getCookies());
            Integer city_id = Integer.parseInt(req.getParameter("city_id"));
            Integer user_id = userSession.get().getId();
            Boolean result = userRouteService.removeFromUserRoute(user_id,city_id);
            requestHelper.writeBody(resp, HttpServletResponse.SC_OK,result.toString());
        }
        else {
            requestHelper.writeUnauthorizedResponse(resp);
        }
    }
}
