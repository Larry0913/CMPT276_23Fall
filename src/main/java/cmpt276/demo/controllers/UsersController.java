package cmpt276.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import cmpt276.demo.models.User;
import cmpt276.demo.models.UserProfile;
import cmpt276.demo.models.UserProfileRepository;
import cmpt276.demo.models.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Controller
public class UsersController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserProfileRepository profileRepo;

    @GetMapping("/users/view")    
    public String getAllUsers(Model model){
        System.out.println("Get all users");
        List<User> users = userRepo.findAll();
        model.addAttribute("us", users);
        return "users/showAll";
    }

    @GetMapping("/")
    public RedirectView process() {
        return new RedirectView("login");
    }

    @GetMapping("/register")
    public String getSignup(HttpServletRequest request) {
        request.getSession().invalidate();
        return "users/add"; 
    }


    @PostMapping("/users/add")
    public String addUser(@RequestParam Map<String, String> newuser, HttpServletResponse response) {
        String newName = newuser.get("name");
        String newPwd = newuser.get("password");
        boolean newAdmin = Boolean.parseBoolean(newuser.get("isAdmin"));
        // System.out.println("Raw value: " + newuser.get("isAdmin"));
        // System.out.println("newAdmin: " + newAdmin);
        String newEmail = newuser.get("email");
        String newPhone = newuser.get("phoneNumber");
        UserProfile newProf = new UserProfile(newEmail);
        User newUser = new User(newName, newPwd, newAdmin); 
        newProf.setPhoneNumber(newPhone);
        newUser.setUserProfile(newProf);
        newProf.setUser(newUser);
        userRepo.save(newUser);
        profileRepo.save(newProf);
        response.setStatus(201);
        return "users/addedUser";
    }

    @GetMapping("/login")
    public String getLogin(Model model, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("session-user");
        if (user == null) {
            return "users/login";
        }
        else {
            model.addAttribute("user", user);
            return "users/protected";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam Map<String, String> formData, Model model, HttpServletRequest request, HttpSession session) {
        // processing lohin
        String name = formData.get("name");
        String pwd = formData.get("password");
        List<User> userlist = userRepo.findByUsernameAndPassword(name, pwd);
        if (userlist.isEmpty()) {
            return "users/login";
        }
        else {
            // successfully login
            User user = userlist.get(0);
            request.getSession().setAttribute("session_user", user);
            // we should not add session id in the model
            // this is just an example of adding dynamic data into model
            model.addAttribute("session_id", session.getId());
            model.addAttribute("user", user);
            return "users/dashboard";
        }
    }

    @GetMapping("/logout")
    public String destoySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "users/login";
    }
}
