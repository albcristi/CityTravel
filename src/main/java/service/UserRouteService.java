package service;

import model.UserRoute;
import repository.UserRouteRepoImp;
import repository.UserRouteRepository;

import java.util.List;

public class UserRouteService {
    private UserRouteRepository userRouteRepository = new UserRouteRepoImp();

    public List<UserRoute>  getUserRoutes(Integer user_id){
        return userRouteRepository.getRouteForUserId(user_id);
    }

    public Boolean saveRoute(Integer user_id, Integer city_id){
        return userRouteRepository.addRoute(user_id, city_id);
    }

    public Boolean removeFromUserRoute(Integer user_id, Integer city_id){
        return userRouteRepository.removeCityFromUserRoute(user_id, city_id);
    }
}
