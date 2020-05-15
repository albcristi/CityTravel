package repository;

import model.NeighbourCity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NeighbourCityRepository extends JpaRepository<NeighbourCity, Integer> {
}
