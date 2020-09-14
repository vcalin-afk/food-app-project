package ro.calin.FoodApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.calin.FoodApp.service.IngredientService;
import ro.calin.FoodApp.service.RecipeService;
import ro.calin.FoodApp.service.UserService;

import java.util.*;

@Controller
public class RecipeTagsController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;

    @Autowired
    IngredientService ingredientService;

    @GetMapping("/OwnMeal/show-recipes")
    public ModelAndView showRecipe(@RequestParam("ingredient") List<String> ingredientsFromPage) {

        ingredientService.addIngredientsFromOwnmealPage(ingredientsFromPage);

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

        return new ModelAndView("redirect:/login-page");
    }

    @GetMapping("/save-favorite-recipe")
    public ModelAndView saveToFavorites(@RequestParam("recipeId") int recipeId) {

        recipeService.addRecipeIdToFavoriteList(recipeId);

        return new ModelAndView("redirect:" + ingredientService.getUrlForOwnmealPage());
    }

    @PostMapping("/delete-favorite-recipe")
    public ModelAndView deleteFromFavorites(@RequestParam("recipeId") int recipeId) {

        recipeService.removeFavoriteRecipe(recipeId);

        return new ModelAndView("redirect:/favorite-recipes");
    }

}
