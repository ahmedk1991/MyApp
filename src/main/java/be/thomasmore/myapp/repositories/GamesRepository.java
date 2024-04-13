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
            "(:category IS NULL OR g.category IN :category) AND " +
            "(:console IS NULL OR g.console IN :console) " +
            "ORDER BY " +
            "CASE WHEN :orderBy = 'price_desc' THEN g.price END DESC, " +
            "CASE WHEN :orderBy = 'price_asc' THEN g.price END ASC")
    List<Games> findByFilter(@Param("minPrice") Integer minPrice,
                             @Param("maxPrice") Integer maxPrice,
                             @Param("category") List<String> category,
                             @Param("console") List<String> console,
                             @Param("orderBy") String orderBy);


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
