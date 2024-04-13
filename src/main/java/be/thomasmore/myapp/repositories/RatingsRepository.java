package be.thomasmore.myapp.repositories;

import be.thomasmore.myapp.model.Posts;
import be.thomasmore.myapp.model.Ratings;
import org.springframework.data.repository.CrudRepository;

public interface RatingsRepository extends CrudRepository<Ratings, Integer> {
}
