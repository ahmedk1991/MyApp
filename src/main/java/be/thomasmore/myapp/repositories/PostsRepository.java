package be.thomasmore.myapp.repositories;

import be.thomasmore.myapp.model.Posts;
import org.springframework.data.repository.CrudRepository;

public interface PostsRepository extends CrudRepository<Posts, Integer> {

}
