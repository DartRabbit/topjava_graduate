package restaurant.rating.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"restaurant", "date", "price"})
@Entity
@Table(
        name = "dishes",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"restaurant_id", "date"},
                name = "dishes_unique_restaurant_date_idx")}
)
public class Dish extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDateTime date;

    @Column(name = "price", nullable = false)
    @NotNull
    private Integer price;
}
