package be.thomasmore.myapp.repositories;

import be.thomasmore.myapp.model.Posts;
import be.thomasmore.myapp.model.Ratings;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RatingsRepository extends CrudRepository<Ratings, Integer> {

    Optional<Ratings> findById(Integer id);
}
