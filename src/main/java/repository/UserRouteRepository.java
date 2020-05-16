package repository;


import model.UserRoute;

import java.util.List;

public interface UserRouteRepository {

    List<UserRoute>  getRouteForUserId(Integer user_id);

    Boolean addRoute(Integer user_id, Integer city_id);

    Boolean removeCityFromUserRoute(Integer user_id, Integer city_id);
}
