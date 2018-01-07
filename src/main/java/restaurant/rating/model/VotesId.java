package restaurant.rating.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;


@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VotesId implements Serializable{

    @Column(name = "user_id")
    @Getter
    @Setter
    private int userId;

    @Column(name = "date")
    @Getter
    @Setter
    private LocalDate date;
}
