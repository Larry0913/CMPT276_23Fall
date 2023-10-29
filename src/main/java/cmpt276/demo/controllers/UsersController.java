package cmpt276.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cmpt276.demo.dao.UserRepository;
import cmpt276.demo.models.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

@Controller
public class UsersController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/")
    public RedirectView process() {
        return new RedirectView("login");
    }

    @GetMapping("/register")
    public String getSignup(HttpServletRequest request) {
        request.getSession().invalidate();
        return "users/add";
    }

    /**
     * register
     * 
     * @param newuser
     * @param response
     * @return
     */
    @PostMapping("/users/add")
    public String addUser(@RequestParam Map<String, String> newuser, HttpServletResponse response) {
        String newName = newuser.get("name");
        String newPwd = newuser.get("password");
        boolean newAdmin = Boolean.parseBoolean(newuser.get("isAdmin"));
        String newEmail = newuser.get("email");
        String newPhone = newuser.get("phoneNumber");
        String department = newuser.get("department");

        User newUser = new User(newName, newPwd, newAdmin);
        newUser.setEmail(newEmail);
        newUser.setPhoneNumber(newPhone);
        newUser.setDepartment(department);
        userRepo.save(newUser);

        response.setStatus(201);
        return "users/addedUserSuccess";
    }

    /**
     * add user
     * 
     * @param newuser
     * @param response
     * @return
     */
    @PostMapping("/users/addb")
    public String addUserB(@RequestParam Map<String, String> newuser, HttpServletResponse response) {
        String newName = newuser.get("name");
        String newPwd = newuser.get("password");
        boolean newAdmin = Boolean.parseBoolean(newuser.get("isAdmin"));

        String newEmail = newuser.get("email");
        String newPhone = newuser.get("phoneNumber");
        String department = newuser.get("department");

        User newUser = new User(newName, newPwd, newAdmin);
        newUser.setEmail(newEmail);
        newUser.setPhoneNumber(newPhone);
        newUser.setDepartment(department);
        userRepo.save(newUser);

        response.setStatus(201);
        return "redirect:/users/addressBook";
    }

    @GetMapping("/login")
    public String getLogin(Model model, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("session-user");
        if (user == null) {
            return "users/login";
        } else {
            model.addAttribute("user", user);
            return "users/dashboard";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam Map<String, String> formData, Model model, HttpServletRequest request,
            HttpSession session) {
        // processing lohin
        String name = formData.get("name");
        String pwd = formData.get("password");
        List<User> userlist = userRepo.findByUsernameAndPassword(name, pwd);
        if (userlist.isEmpty()) {
            return "users/login";
        } else {
            // successfully login
            User user = userlist.get(0);
            request.getSession().setAttribute("user", user);
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

    @GetMapping("/users/dashboard")
    public ModelAndView homePage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        User session_user = (User) request.getSession().getAttribute("user");
        // we should not add session id in the model
        // this is just an example of adding dynamic data into model
        mv.addObject("user", session_user);
        mv.setViewName("users/dashboard");
        return mv;
    }

    @GetMapping("/users/performance")
    public String showPerformance(HttpServletRequest request) {
        return "users/performance";
    }

    @GetMapping("/users/addressBook")
    public String showAddressBook(HttpServletRequest request, Model model) {
        List<User> userinforlist = userRepo.findAll();
        model.addAttribute("allusers", userinforlist);
        return "users/addressBook";
    }

    @RequestMapping("/users/delete")
    public String deleteUser(HttpServletRequest request, Model model, Integer uid) {
        userRepo.deleteById(uid);
        return "users/addressBook";
    }

    @GetMapping("/users/toAddUserPage")
    public String toAddUserPage(HttpServletRequest request, Model model) {
        return "users/addUser";
    }

    @GetMapping("/users/personalCenter")
    public ModelAndView showInfo(HttpSession session) {
        ModelAndView model = new ModelAndView();
        User user = (User) session.getAttribute("user");
        Integer id = user.getUid();
        User user1 = userRepo.findById(id).get();
        session.setAttribute("user", user1);
        model.addObject("user", user1);
        model.setViewName("users/personalCenter");
        return model;
    }

    @GetMapping("/users/settings")
    public String showSettings(HttpServletRequest request) {
        return "users/settings";
    }

}
