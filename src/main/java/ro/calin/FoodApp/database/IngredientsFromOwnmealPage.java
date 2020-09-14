package ro.calin.FoodApp.database;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredientsFromOwnmealPage {

    private List<String> ingredientsFromPageList;

    public List<String> getIngredientsFromPageList() {
        return ingredientsFromPageList;
    }

    public void setIngredientsFromPageList(List<String> ingredientsFromPageList) {
        this.ingredientsFromPageList = ingredientsFromPageList;
    }
}
