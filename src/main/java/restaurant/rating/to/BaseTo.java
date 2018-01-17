package restaurant.rating.to;

import lombok.Getter;
import lombok.Setter;
import restaurant.rating.HasId;

public abstract class BaseTo implements HasId {
    @Getter
    @Setter
    protected Integer id;

    public BaseTo() {
    }

    public BaseTo(Integer id) {
        this.id = id;
    }
}
