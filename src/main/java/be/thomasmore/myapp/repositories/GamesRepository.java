package be.thomasmore.myapp.repositories;

import be.thomasmore.myapp.model.Games;
import org.springframework.data.repository.CrudRepository;

public interface GamesRepository extends CrudRepository <Games,Integer> {


}
