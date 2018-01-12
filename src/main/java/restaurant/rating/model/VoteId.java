package restaurant.rating.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

//Composite Primary Key
//http://javasampleapproach.com/spring-framework/spring-data/create-composite-primary-key-spring-jpa-mysql

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VoteId implements Serializable{
    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    @Getter
    @Setter
    private int userId;

    @Column(name = "date")
    @Getter
    @Setter
    private LocalDate date;
}
