package restaurant.rating.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import restaurant.rating.model.Restaurant;
import restaurant.rating.model.User;
import restaurant.rating.model.Vote;
import restaurant.rating.repository.CrudVoteRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DataJpaVoteRepository {
    private final CrudVoteRepository repository;

    @Autowired
    public DataJpaVoteRepository(CrudVoteRepository repository) {
        this.repository = repository;
    }

    @PersistenceContext
    private EntityManager em;

    public Vote save(Vote vote, int restaurantId, int userId) {
        vote.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        vote.setUser(em.getReference(User.class, userId));
        return repository.save(vote);
    }
}
