package ro.calin.FoodApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.calin.FoodApp.database.Ingredient;
import ro.calin.FoodApp.database.IngredientDao;
import ro.calin.FoodApp.database.IngredientsFromOwnmealPage;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    IngredientsFromOwnmealPage ingredientsFromOwnmealPage;

    public void saveIngredient(Ingredient ingredient) {
        ingredientDao.save(ingredient);
    }

    public void addIngredientsFromOwnmealPage(List<String> ingredientsFromPage) {
        ingredientsFromOwnmealPage.setIngredientsFromPageList(ingredientsFromPage);
    }

    public StringBuilder getUrlForOwnmealPage() {

        StringBuilder firstPartLink = new StringBuilder("http://localhost:8080/OwnMeal/show-recipes?");

        List<String> ingredientsName = ingredientsFromOwnmealPage.getIngredientsFromPageList();
        for (String ingredient: ingredientsName) {
            String secondPartLink = "ingredient=" + ingredient + "&";
            firstPartLink.append(secondPartLink);
        }

        return firstPartLink;
    }

}
