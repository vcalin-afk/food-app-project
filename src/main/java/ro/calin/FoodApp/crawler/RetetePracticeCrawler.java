package ro.calin.FoodApp.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.calin.FoodApp.database.Ingredient;
import ro.calin.FoodApp.database.Recipe;
import ro.calin.FoodApp.database.RetetePracticePage;
import ro.calin.FoodApp.service.IngredientService;
import ro.calin.FoodApp.service.RecipeService;
import ro.calin.FoodApp.service.RetetePracticePageService;

import java.util.ArrayList;
import java.util.List;

@Component
public class RetetePracticeCrawler {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RetetePracticePageService retetePracticePageService;

    @Autowired
    private IngredientService ingredientService;


    //@Scheduled(fixedRate = 5000)
    public void extractRecipe() throws Exception {
        List<Recipe> recipeList = new ArrayList<>();

        Iterable<RetetePracticePage> retetePracticePages = retetePracticePageService.findAll();

        for (RetetePracticePage retetePracticePageList : retetePracticePages) {
            String pageListUrl = retetePracticePageList.getUrl();
            System.out.println(pageListUrl);

            Document document = Jsoup.connect(pageListUrl).get();
            Elements elements = document.select(".search-results .row");

            for (Element element : elements) {
                Element elementTitle = element.selectFirst(".col-md-7 h2");

                Element link = elementTitle.select("a").first();
                String recipeUrl = link.attr("href");

                System.out.println(recipeUrl);
                System.out.println(elementTitle.text());

                Recipe recipe = new Recipe();
                recipe.setName(elementTitle.text());
                recipe.setUrl(recipeUrl);

                recipeList.add(recipe);
            }

            for (Recipe recipe : recipeList) {
                Integer existingRecipeWithSameName = recipeService.countAllByName(recipe.getName());
                Integer existingRecipeWithSameUrl = recipeService.countAllByUrl(recipe.getUrl());
                if (existingRecipeWithSameName == 0 && existingRecipeWithSameUrl == 0) {
                    recipeService.saveRecipe(recipe);
                }
            }
            System.out.println(recipeList);
        }
    }

    //@Scheduled(fixedRate = 500000)
    public void extractIngredients() throws Exception {
        List<Ingredient> ingredientList = new ArrayList<>();

        Iterable<Recipe> recipes = recipeService.findAll();

        for (Recipe recipe : recipes) {
            String pageListUrl = recipe.getUrl();
            System.out.println(pageListUrl);

            Document document = Jsoup.connect(pageListUrl).get();
            Elements elements = document.select(".recipe-box .ingredients span");

            for (Element element : elements) {
                Elements ingredientName = element.select("span");
                System.out.println(ingredientName.text());

                Ingredient ingredient = new Ingredient();
                ingredient.setName(ingredientName.text());

                ingredientList.add(ingredient);

                recipe.setIngredients(ingredientList);
                ingredientService.saveIngredient(ingredient);
            }
            System.out.println(ingredientList);
        }
    }
}