package ro.calin.FoodApp.security;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class UserSession {

    private int userId;

    private List<Integer> favoriteRecipes = new ArrayList<>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(List<Integer> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }
}
