package restaurant.rating.repository.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import restaurant.rating.model.Restaurant;
import restaurant.rating.model.User;
import restaurant.rating.model.Vote;
import restaurant.rating.model.VoteId;
import restaurant.rating.repository.CrudVoteRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@AllArgsConstructor
public class DataJpaVoteRepository {
    private final CrudVoteRepository repository;
    @PersistenceContext
    private EntityManager em;

    public Vote save(Vote vote, int restaurantId, int userId) {
        vote.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        vote.setUser(em.getReference(User.class, userId));
        return repository.save(vote);
    }

    public Vote get(VoteId voteId){
        return repository.findById(voteId).orElse(null);
    }
}
