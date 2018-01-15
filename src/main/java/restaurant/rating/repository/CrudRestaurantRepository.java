package restaurant.rating.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rating.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);

    @Override
    Optional<Restaurant> findById(Integer id);

    @Override
    List<Restaurant> findAll(Sort sort);

    Restaurant findByName(String name);

    //@EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.dishes d WHERE r.id=?1 AND d.date=?2")
    Restaurant getWithDishesByDate(int id, LocalDate date);

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.dishes d WHERE d.date=?1 ORDER BY r.name ASC")
    List<Restaurant> getAllWithDishesByDate(LocalDate date);

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.votes v WHERE v.date=?1 ORDER BY r.votes.size DESC")
    List<Restaurant> getAllWithVotesByDate(LocalDate date);
}
