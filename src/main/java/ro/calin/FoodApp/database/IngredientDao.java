package ro.calin.FoodApp.database;

import org.springframework.data.repository.CrudRepository;


public interface IngredientDao extends CrudRepository<Ingredient, Integer> {

}
