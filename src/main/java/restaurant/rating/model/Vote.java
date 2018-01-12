package restaurant.rating.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(exclude = {"date", "user", "restaurant"})
@NoArgsConstructor
@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "restaurant_id", "user_id"}, name = "vote_unique_idx")})
public class Vote {

    @EmbeddedId
    private VoteId voteId;

    @Column(name = "date", nullable = false, updatable = false, insertable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    @NotNull
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    public Vote(VoteId voteId, LocalDate date) {
        this.voteId = voteId;
        this.date = date;
    }
}
