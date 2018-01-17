package restaurant.rating.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
public class DishTo extends BaseTo implements Serializable {

    @Getter
    @Setter
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    protected String name;

    @Getter
    @Setter
    @NotNull
    private LocalDate date;

    @Getter
    @Setter
    @NotNull
    private Double price;

    public DishTo(Integer id, String name, LocalDate date, Double price) {
        super(id);
        this.name = name;
        this.date = date;
        this.price = price;
    }
}
