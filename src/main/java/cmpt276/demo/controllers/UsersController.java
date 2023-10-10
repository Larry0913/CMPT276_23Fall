package cmpt276.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cmpt276.demo.models.User;
import cmpt276.demo.models.UserRepository;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Controller
public class UsersController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/users/view")    
    public String getAllUsers(Model model){
        System.out.println("Get all users");
        List<User> users = userRepo.findAll();
        model.addAttribute("us", users);
        return "users/showAll";
    }

    @PostMapping("/users/add")
    public String addUser(@RequestParam Map<String, String> newuser, HttpServletResponse response) {
        System.out.println("Add user");
        String newName = newuser.get("name");
        String newPwd = newuser.get("password");
        //String newAdmin = newuser.get("isAdmin");
        boolean newAdmin = Boolean.parseBoolean(newuser.get("isAdmin"));
        userRepo.save(new User(newName, newPwd, newAdmin));
        response.setStatus(201);
        return "users/addedUser";
    }
}
