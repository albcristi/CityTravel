package servlets;

import lombok.SneakyThrows;
import model.City;
import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import service.AuthenticationService;
import service.NeighbourCityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CityNeighboursServlet  extends HttpServlet {

    private AuthenticationService authenticationService = new AuthenticationService();

    private NeighbourCityService neighbourCityService = new NeighbourCityService();

    private RequestHelper requestHelper = new RequestHelper();

    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> userSession = authenticationService.validateUserRequest(req);
        if(userSession.isPresent()){
            // we have a session, we can retrieve data
            //JSONObject requestBody = requestHelper.readRequestBody(req);
            long nr = Long.parseLong(req.getParameter("city_id"));
            Integer city_id = ((Long) nr).intValue();
            List<City> neighbours = neighbourCityService.getCityNeighbours(city_id);
            JSONArray result = new JSONArray();
            neighbours.stream()
                    .map(City::toJsonFormat)
                    .forEach(result::add);
            requestHelper.writeBody(resp, HttpServletResponse.SC_OK,result.toJSONString());
        }
        else{
            requestHelper.writeUnauthorizedResponse(resp);
        }
    }
}
