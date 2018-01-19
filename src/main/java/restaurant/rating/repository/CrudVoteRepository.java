package restaurant.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rating.model.Vote;
import restaurant.rating.model.VoteId;

import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote,VoteId> {

    @Override
    @Transactional
    Vote save(Vote vote);

    @Override
    Optional<Vote> findById(VoteId voteId);
}
