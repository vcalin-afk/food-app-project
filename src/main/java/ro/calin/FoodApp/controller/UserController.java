package ro.calin.FoodApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ro.calin.FoodApp.service.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/dashboard")
    public ModelAndView showDashboardPage() {

        if (userService.verifySession()) {
            ModelAndView modelAndView = new ModelAndView("index-login");
            modelAndView.addObject("welcomeMessage", "Bine ai venit, " + userService.getSessionUserName() + "!");
            return modelAndView;
        }

        return new ModelAndView("index");

    }

    @GetMapping("/aboutOwnMeal")
    public ModelAndView showAboutOwnMealPage() {

        if (userService.verifySession()) {
            ModelAndView modelAndView = new ModelAndView("aboutOwnMeal-login");
            modelAndView.addObject("welcomeMessage", "Bine ai venit, " + userService.getSessionUserName() + "!");
            return modelAndView;
        }

        return new ModelAndView("aboutOwnMeal");

    }

    @GetMapping("/OwnMeal")
    public ModelAndView showPage() {

        if (userService.verifySession()) {
            ModelAndView modelAndView = new ModelAndView("OwnMeal");
            modelAndView.addObject("welcomeMessage", "Bine ai venit, " + userService.getSessionUserName() + "!");
            return modelAndView;
        }

        return new ModelAndView("redirect:/login-page");
    }

    @GetMapping("/login-page")
    public ModelAndView showLoginPage() {
        return new ModelAndView("login");
    }

    @GetMapping("/register-page")
    public ModelAndView showRegisterPage() {
        return new ModelAndView("register");
    }

    @PostMapping("/login-action")
    public ModelAndView loginAction(@RequestParam("email") String email,
                                    @RequestParam("password") String password) {

        if (userService.verifyLoginEmailAndPassword(email, password)) {
            return new ModelAndView("redirect:/dashboard");
        } else {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("loginIncorrectMessage", "Datele introduse nu sunt corecte. Incearca din nou");
            return modelAndView;
        }
    }

    @PostMapping("/register-action")
    public ModelAndView registerAction(@RequestParam("name") String name,
                                       @RequestParam("email") String email,
                                       @RequestParam("password") String password,
                                       @RequestParam("password-confirm") String passwordConfirm) {

        ModelAndView modelAndView = new ModelAndView("register");

        if (userService.verifyRegisterExistingEmail(email)) {
            modelAndView.addObject("registerIncorrectMessage","Email-ul este deja folosit de un utilizator");

            return modelAndView;
        }
        if (email.isEmpty()) {
            modelAndView.addObject("registerIncorrectMessage","Introduceti un email");

            return modelAndView;
        }
        if (name.isEmpty()) {
            modelAndView.addObject("registerIncorrectMessage","Introduceti un nume de utilizator");

            return modelAndView;
        }
        if (password.length() < 8) {
            modelAndView.addObject("registerIncorrectMessage","Parola este prea scurta. Incearca din nou");

            return modelAndView;
        }
        if (!password.equals(passwordConfirm)) {
            modelAndView.addObject("registerIncorrectMessage","Parolele nu se potrivesc");

            return modelAndView;
        }

        userService.saveUser(name, email, password);

        return new ModelAndView("redirect:/login-page");
    }

    @GetMapping("/logout-action")
    public ModelAndView logOut() {
        userService.logOut();

        return new ModelAndView("redirect:/login-page");
    }
}
