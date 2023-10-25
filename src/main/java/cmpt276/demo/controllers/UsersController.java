package cmpt276.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import cmpt276.demo.dao.UserProfileRepository;
import cmpt276.demo.dao.UserRepository;
import cmpt276.demo.dao.UserScheduleRepository;
import cmpt276.demo.dao.WeekRepository;

import cmpt276.demo.models.User;
import cmpt276.demo.models.UserProfile;
import cmpt276.demo.models.Week;
import cmpt276.demo.models.UserSchedule;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
//import java.util.ArrayList;

@Controller
public class UsersController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserProfileRepository profileRepo;

    @Autowired
    private WeekRepository weekRepo;

    @Autowired
    private UserScheduleRepository userscheduleRepo;

    List<User> userlist;
    List<Week> weeklist;

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
        userlist = userRepo.findByUsernameAndPassword(name, pwd);
        if (userlist.isEmpty()) {
            return "users/login";
        } else {
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

    @GetMapping("/users/dashboard")
    public String homePage(HttpServletRequest request) {
        request.getSession().invalidate();
        return "users/dashboard";
    }

    @GetMapping("/users/performance")
    public String showPerformance(HttpServletRequest request) {
        request.getSession().invalidate();
        return "users/performance";
    }

    @GetMapping("/users/addressBook")
    public String showAddressBook(HttpServletRequest request) {
        request.getSession().invalidate();
        return "users/addressBook";
    }

    @GetMapping("/users/personalCenter")
    public String showInfo(HttpServletRequest request) {
        request.getSession().invalidate();
        return "users/personalCenter";
    }

    @GetMapping("/users/settings")
    public String showSettings(HttpServletRequest request) {
        request.getSession().invalidate();
        return "users/settings";
    }

    @GetMapping("/users/admin_schedule")
    public String showAdmin_Schedule(HttpServletRequest request) {
        request.getSession().invalidate();
        return "users/admin_schedule"; 
    }

    @GetMapping("/users/associate-week")
    public String getAssociateWeekForm(Model model) {
        // Retrieve a list of users and weeks here, e.g., userRepo.findAll() and weekRepo.findAll()
        List<User> users = userRepo.findAll();
        List<Week> weeks = weekRepo.findAll();
        
        // Add the lists to the model so they can be displayed in the form
        model.addAttribute("users", users);
        model.addAttribute("weeks", weeks);
        
        return "users/associateWeekForm";
    }

    @PostMapping("/users/associate-week")
    public String associateWeek(
            @RequestParam Map<String, String> formData,
            @RequestParam("userId") Long userId,
            
            @RequestParam("days") String days,
            HttpServletResponse response
    ) {
        // Retrieve the selected user and week objects from their respective repositories
        String usrname = formData.get("name");
        // String wkId = formData.get("weekId");
        User user = (User) userRepo.findByUsername(usrname);

        int weekId = Integer.parseInt(formData.get("weekId"));
        Week week = (Week) weekRepo.findById(weekId);
        
        // Create the association object and save it to the database
        UserSchedule schedule = new UserSchedule(user, week, days);
        userscheduleRepo.save(schedule);
        
        return "redirect:/users/admin_schedule";  // Redirect to the admin schedule page
    }
}
