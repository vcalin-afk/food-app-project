package ro.calin.FoodApp.database;

import javax.persistence.*;

@Entity
@Table(name = "retete_practice_pages")
public class RetetePracticePage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "RetetePracticePages{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
