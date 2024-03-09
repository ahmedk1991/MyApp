package be.thomasmore.myapp.repositories;

import be.thomasmore.myapp.model.Games;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GamesRepository extends CrudRepository <Games,Integer> {

    List<Games> findAllBy();


}
