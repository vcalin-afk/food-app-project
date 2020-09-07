package ro.calin.FoodApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.calin.FoodApp.database.Ingredient;
import ro.calin.FoodApp.database.IngredientDao;

@Service
public class IngredientService {

    @Autowired
    private IngredientDao ingredientDao;

    public void saveIngredient(Ingredient ingredient) {
        ingredientDao.save(ingredient);
    }

}
