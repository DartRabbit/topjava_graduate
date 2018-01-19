package restaurant.rating.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"}, name = "vote_unique_idx")})
@EqualsAndHashCode(of = {"voteId"})
public class Vote {

    @EmbeddedId
    @Getter
    @Setter
    private VoteId voteId;

    @Column(name = "date", nullable = false, updatable = false, insertable = false)
    @NotNull
    @Getter
    @Setter
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    @NotNull
    @JsonIgnore
    @Getter
    @Setter
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @Getter
    @Setter
    private Restaurant restaurant;

    public Vote(VoteId voteId, LocalDate date) {
        this.voteId = voteId;
        this.date = date;
    }
}
