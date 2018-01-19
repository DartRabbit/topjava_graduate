package restaurant.rating.util;

import restaurant.rating.model.Dish;
import restaurant.rating.to.DishTo;

public class DishUtil {
    private static final int DIVIDER = 100;

    public static int convertPriceToInt(double price){
        return (int)(price*DIVIDER);
    }

    public static double convertPriceToDouble(double price){
        return price/DIVIDER;
    }

    public static Dish createNewFromTo(DishTo newDish) {
        return new Dish(null, newDish.getName(), newDish.getDate(), convertPriceToInt(newDish.getPrice()));
    }

    public static DishTo asTo(Dish Dish) {
        return new DishTo(Dish.getId(), Dish.getName(), Dish.getDate(), convertPriceToDouble(Dish.getPrice()));
    }

    public static Dish updateFromTo(Dish Dish, DishTo DishTo) {
        Dish.setName(DishTo.getName());
        Dish.setDate(DishTo.getDate());
        Dish.setPrice(convertPriceToInt(DishTo.getPrice()));
        return Dish;
    }
}
