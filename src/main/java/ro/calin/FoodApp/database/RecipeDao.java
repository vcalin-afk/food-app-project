package ro.calin.FoodApp.database;

import org.springframework.data.repository.CrudRepository;


public interface RecipeDao extends CrudRepository<Recipe, Integer> {

    Integer countAllByName(String name);

    Integer countAllByUrl(String url);
}
