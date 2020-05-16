package repository;


import model.City;

import java.util.List;

public interface NeighbourCityRepository {

    List<City> getCityNeighbours(Integer city_id);

}
