package service;

import model.City;
import repository.NeighbourCityImplementation;
import repository.NeighbourCityRepository;

import java.util.List;

public class NeighbourCityService {
    private NeighbourCityRepository neighbourCityRepository = new NeighbourCityImplementation();

    public List<City> getCityNeighbours(Integer city_id){
        return neighbourCityRepository.getCityNeighbours(city_id);
    }
}
