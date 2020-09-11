package ro.calin.FoodApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.calin.FoodApp.database.IngredientsFromPage;
import ro.calin.FoodApp.database.Recipe;
import ro.calin.FoodApp.security.UserSession;
import ro.calin.FoodApp.service.RecipeService;
import ro.calin.FoodApp.service.UserService;

import java.util.*;

@Controller
public class RecipeTagsController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserSession userSession;

    @Autowired
    UserService userService;

    @Autowired
    IngredientsFromPage ingredientsFromPageTemp;

    @GetMapping("/OwnMeal/show-recipes")
    public ModelAndView showRecipe(@RequestParam("ingredient") List<String> ingredientsFromPage) {

        ingredientsFromPageTemp.setIngredientsFromPageList(ingredientsFromPage);

        ModelAndView modelAndView = new ModelAndView("Ownmeal");

        modelAndView.addObject("recipes", recipeService.getRecipesForPage(ingredientsFromPage));

        if (userService.verifySession()) {
            modelAndView.addObject("welcomeMessage", "Bine ai venit, " + userService.getSessionUserName() + "!");
            return modelAndView;
        }

        return modelAndView;
    }

    @GetMapping("/favorite-recipes")
    public ModelAndView showPage() {
        ModelAndView modelAndView = new ModelAndView("FavoriteRecipes");

        modelAndView.addObject("favoriteRecipe", recipeService.getRecipesForFavoritePage());

        if (userService.verifySession()) {
            modelAndView.addObject("welcomeMessage", "Bine ai venit, " + userService.getSessionUserName() + "!");
            return modelAndView;
        }

        return modelAndView;
    }

    @GetMapping("/save-favorite-recipe")
    public ModelAndView saveToFavorites(@RequestParam("recipeId") int recipeId) {

        userSession.getFavoriteRecipes().add(recipeId);

        List<String> ingredientsName = ingredientsFromPageTemp.getIngredientsFromPageList();
        StringBuilder firstPartLink = new StringBuilder("http://localhost:8080/OwnMeal/show-recipes?");
        for (String ingredient: ingredientsName) {
            String secondPartLink = "ingredient=" + ingredient + "&";
            firstPartLink.append(secondPartLink);
        }

        return new ModelAndView("redirect:" + firstPartLink);
    }

    @PostMapping("/delete-favorite-recipe")
    public ModelAndView deleteFromFavorites(@RequestParam("recipeId") int recipeId) {

        List<Recipe> recipeListFavorite = recipeService.getRecipesForFavoritePage();
        List<Integer> recipeList = userSession.getFavoriteRecipes();
        recipeList.removeAll(Collections.singletonList(recipeId));

        return new ModelAndView("redirect:/favorite-recipes");
    }

}
