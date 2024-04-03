package be.thomasmore.myapp.repositories;

import be.thomasmore.myapp.model.Games;
import be.thomasmore.myapp.model.Reviews;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GamesRepository extends CrudRepository<Games, Integer> {

    @Query("SELECT g FROM Games g WHERE " +
            "(:minPrice IS NULL OR g.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR g.price <= :maxPrice) AND " +
            "(:category IS NULL OR :category = g.category) AND" +
            "(:console IS NULL OR :console=g.console)")
    List<Games> findByFilter(@Param("minPrice") Integer minPrice,
                             @Param("maxPrice") Integer maxPrice,
                             @Param("category") String category,
                             @Param("console") String console);

    Optional<Games> findById(Integer id);

    List<Games> findByNameIsContainingIgnoreCase(String search);

    List<Games> findByCategoryIsContainingIgnoreCase(String categoryName);

    @Query("SELECT DISTINCT g.category FROM Games g")
    List<String> findDistinctCategories();


    Optional<Games> findFirstByCategoryAndIdGreaterThanOrderByIdAsc(String category, Integer id);

    Optional<Games> findFirstByCategoryAndIdOrderByIdAsc(String category, Integer id);

    Optional<Games> findFirstByCategoryAndIdLessThanOrderByIdDesc(String category, Integer id);

    Optional<Games> findFirstByCategoryAndIdOrderByIdDesc(String category, Integer id);


}
