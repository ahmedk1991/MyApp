package be.thomasmore.myapp.repositories;

import be.thomasmore.myapp.model.Games;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GamesRepository extends CrudRepository <Games,Integer> {

    Optional<Games> findById(Integer id);
    List<Games>findByNameIsContainingIgnoreCase(String search);
    List<Games>findByCategoryIsContainingIgnoreCase(String search);


}
