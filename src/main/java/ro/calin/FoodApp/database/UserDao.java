package ro.calin.FoodApp.database;

import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {

}
