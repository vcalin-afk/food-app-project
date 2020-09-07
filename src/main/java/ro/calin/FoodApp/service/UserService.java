package ro.calin.FoodApp.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.calin.FoodApp.database.User;
import ro.calin.FoodApp.database.UserDao;
import ro.calin.FoodApp.security.UserSession;


@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    UserSession userSession;

    @Autowired
    UserService userService;

    public void saveUser(String name, String email, String password) {
        User newUser = new User();

        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(DigestUtils.md5Hex(password));

        userDao.save(newUser);
    }

    public Iterable<User> findAll() {
        return userDao.findAll();
    }

    public boolean verifySession() {
        return userSession.getUserId() != 0;
    }

    public String getSessionUserName() {
        Iterable<User> usersFromDatabase = userService.findAll();
        for (User user: usersFromDatabase) {
            if (userSession.getUserId() == user.getId()) {
                return user.getName();
            }
        }
        return null;
    }

    public boolean verifyLoginEmailAndPassword(String email, String password) {
        Iterable<User> usersFromDatabase = userService.findAll();
        for (User user: usersFromDatabase) {
            if (user.getEmail().equals(email) && user.getPassword().equals(DigestUtils.md5Hex(password))) {
                userSession.setUserId(user.getId());

                return true;
            }
        }
        return false;
    }

    public boolean verifyRegisterExistingEmail(String email) {
        Iterable<User> usersFromDatabase = userService.findAll();
        for (User user: usersFromDatabase) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
