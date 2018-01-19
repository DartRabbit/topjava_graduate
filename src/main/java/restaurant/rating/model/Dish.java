package restaurant.rating.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnore
    @Getter
    @Setter
    private Restaurant restaurant;

    @Column(name = "date", nullable = false)
    @NotNull
    @Getter
    @Setter
    private LocalDate date;

    @Column(name = "price", nullable = false)
    @NotNull
    @Getter
    @Setter
    private Integer price;

    public Dish(Integer id, String name, LocalDate date, Integer price) {
        super(id, name);
        this.date = date;
        this.price = price;
    }

    public Dish(Dish dish) {
        this(dish.getId(), dish.getName(), dish.getDate(), dish.getPrice());
    }
}
