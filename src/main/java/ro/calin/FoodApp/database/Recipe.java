package ro.calin.FoodApp.database;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String url;

    @OneToMany()
    @JoinColumn(name = "recipe_id")
    @Cascade(value = org.hibernate.annotations.CascadeType.PERSIST)
    private List<Ingredient> ingredients;

    @OneToMany()
    @JoinColumn(name = "recipe_id")
    @Cascade(value = org.hibernate.annotations.CascadeType.PERSIST)
    private List<Direction> directions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", ingredients=" + ingredients +
                ", directions=" + directions +
                '}';
    }
}
