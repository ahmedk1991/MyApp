package be.thomasmore.myapp.repositories;

import be.thomasmore.myapp.model.Games;
import be.thomasmore.myapp.model.Reviews;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReviewsRepository extends CrudRepository<Reviews, Integer> {
    Optional<Reviews> findById(Integer id);


}
