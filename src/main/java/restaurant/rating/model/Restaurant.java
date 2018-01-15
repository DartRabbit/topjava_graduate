package restaurant.rating.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"dishes"})
@NoArgsConstructor
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurant_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    protected List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    @JsonIgnore
    protected List<Vote> votes;

    public Restaurant(Integer id, String name){
        super(id,name);
    }

    public Restaurant(Restaurant restaurant){
        this(restaurant.getId(),restaurant.getName());
    }
}
