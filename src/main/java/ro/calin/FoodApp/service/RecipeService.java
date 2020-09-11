package ro.calin.FoodApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.calin.FoodApp.database.Ingredient;
import ro.calin.FoodApp.database.Recipe;
import ro.calin.FoodApp.database.RecipeDao;
import ro.calin.FoodApp.security.UserSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    RecipeDao recipeDao;

    @Autowired
    UserSession userSession;

    public void saveRecipe(Recipe recipe) {
        recipeDao.save(recipe);
    }

    public Integer countAllByName(String name) {
        return recipeDao.countAllByName(name);
    }

    public Integer countAllByUrl(String url) {
        return recipeDao.countAllByUrl(url);
    }

    public Iterable<Recipe> findAll() {
        return recipeDao.findAll();
    }

    public List<Recipe> getRecipesForPage(List<String> ingredientsFromPage) {
        Iterable<Recipe> recipesFromList = this.findAll();
        List<Recipe> recipeForUserPage = new ArrayList<>();

        for (Recipe recipeFromList: recipesFromList) {
            List<Ingredient> ingredientsFromList = recipeFromList.getIngredients();
            for (Ingredient ingredientFromList: ingredientsFromList) {
                for (String ingredientFromPage: ingredientsFromPage) {
                    if (ingredientFromList.getName().contains(ingredientFromPage)) {
                        recipeForUserPage.add(recipeFromList);
                    }
                }
            }
        }

        return new ArrayList<>(new HashSet<>(recipeForUserPage));
    }

    public Recipe findById(int id) {
        return recipeDao.findById(id);
    }

    public List<Recipe> getRecipesForFavoritePage() {
        List<Recipe> recipeListForPage = new ArrayList<>();
        List<Integer> recipeList = userSession.getFavoriteRecipes();
        for (Integer id: recipeList) {
            recipeListForPage.add(this.findById(id));
        }

        return new ArrayList<>(new HashSet<>(recipeListForPage));
    }

}
