package ro.calin.FoodApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.calin.FoodApp.security.UserSession;
import ro.calin.FoodApp.service.RecipeService;
import ro.calin.FoodApp.service.UserService;

import java.util.List;

@Controller
public class RecipeTagsController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserSession userSession;

    @Autowired
    UserService userService;

    @PostMapping("/OwnMeal/show-recipes")
    public ModelAndView showRecipe(@RequestParam("ingredient") List<String> ingredientsFromPage) {

        ModelAndView modelAndView = new ModelAndView("Ownmeal");

        modelAndView.addObject("recipes", recipeService.getRecipesForPage(ingredientsFromPage));

        if (userService.verifySession()) {
            modelAndView.addObject("welcomeMessage", "Bine ai venit, " + userService.getSessionUserName() + "!");
            return modelAndView;
        }

        return modelAndView;
    }

}
