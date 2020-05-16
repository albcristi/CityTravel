package service;

import model.City;
import repository.CityRepository;
import repository.CityRepositoryImp;

import java.util.List;

public class CityService {
    public CityRepository cityRepository = new CityRepositoryImp();

    public List<City> getCities(){
        return cityRepository.getAll();
    }
}
