package cmpt276.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cmpt276.demo.models.Users;

import java.util.List;
import java.util.ArrayList;

@Controller
public class UsersController {
    
    @GetMapping("/users/view")    
    public String getAllUsers(Model model){
        System.out.println("Get all users");
        List<Users> users = new ArrayList<>();
        users.add(new Users("Larry", "12345", false));
        model.addAttribute("us", users);
        return "users/showAll";
    }
}
