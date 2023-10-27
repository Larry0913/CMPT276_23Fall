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

        // List<UserSchedule> allSchedules = userscheduleRepo.findAll();
        // UserSchedule userSchedule1 = new UserSchedule(newUser, week1, "-------"); 
        // userscheduleRepo.save(userSchedule1); 
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

    // @GetMapping("/users/view")
    // public String showProtected(HttpServletRequest request) {
    //     request.getSession().invalidate();
    //     return "users/protected"; 
    // }

    // @GetMapping("/users/admin_schedule")
    // public String showAdmin_Schedule(HttpServletRequest request) {
    //     request.getSession().invalidate();
    //     return "users/admin_schedule"; 
    // }

    @GetMapping("/users/admin_schedule")
    public String getAssociateWeekForm(Model model ,HttpServletRequest request) {
        // Retrieve a list of users and weeks here, e.g., userRepo.findAll() and weekRepo.findAll()
        List<User> users = userRepo.findAll();
        List<Week> weeks = weekRepo.findAll();
        List<UserSchedule> userSchedule = userscheduleRepo.findAll();
        // Add the lists to the model so they can be displayed in the form
        model.addAttribute("users", users);
        model.addAttribute("weeks", weeks); // Add weeks to the model
        model.addAttribute("userSchedule", userSchedule);
        
        return "users/admin_schedule";
    }

    @PostMapping("/users/associate-week")
    public String associateWeek(@RequestParam Map<String, String> formData,
                                @RequestParam("username") String username,
                                @RequestParam("weekname") String weekName,
                                @RequestParam(value = "days", required = false) List<String> selectedDays,
                                HttpServletRequest request) {
        // Retrieve the selected user and week objects from their respective repositories
        // String userName = formData.get("username");
        // String weekname= formData.get("weekname");


        // User user = (User) userRepo.findByUsername(userName);
        // Week week = (Week) weekRepo.findByWeekName(weekname);

        // Create the association object and save it to the database
        // UserSchedule schedule = new UserSchedule(user, week, daysString.toString());
        // userscheduleRepo.save(schedule);

        return "redirect:/users/editSchedule?username=" + username + "&weekName=" + weekName;
    }

    @GetMapping("/users/editSchedule")
    public String showEditSchedulePage(@RequestParam("username") String username, @RequestParam("weekName") String weekName, Model model) {
        // You can add any necessary logic here to fetch data related to the username and weekName.
        // For example, you can retrieve user and week information based on the provided parameters.

        // Then, add the user and week data to the model.
        model.addAttribute("username", username);
        model.addAttribute("weekName", weekName);

        // Finally, return the "editSchedule.html" template.
        return "users/editSchedule";
    }

    // @GetMapping("/users/editSchedule")
    // public String showEditSchedulePage(@RequestParam("username") String username, @RequestParam("weekName") String weekName, Model model) {

    //     // Example:
    //     User user = (User) userRepo.findByUsername(username);
    //     Week week = (Week) weekRepo.findByWeekName(weekName);

    //     // Add the user and week to the model, and render the editSchedule.html page
    //     model.addAttribute("user", user);
    //     model.addAttribute("week", week);

    //     // Initialize a 7-character string with all '-' characters
    //     StringBuilder daysString = new StringBuilder("-------");

    //     // Process selected days and update the string
    //     if (selectedDays != null) {
    //         for (String day : selectedDays) {
    //             switch (day) {
    //                 case "M":
    //                     daysString.setCharAt(0, 'M');
    //                     break;
    //                 case "T":
    //                     daysString.setCharAt(1, 'T');
    //                     break;
    //                 case "W":
    //                     daysString.setCharAt(2, 'W');
    //                     break;
    //                 case "H":
    //                     daysString.setCharAt(3, 'H');
    //                     break;
    //                 case "F":
    //                     daysString.setCharAt(4, 'F');
    //                     break;
    //                 case "S":
    //                     daysString.setCharAt(5, 'S');
    //                     break;
    //                 case "U":
    //                     daysString.setCharAt(6, 'U');
    //                     break;
    //             }
    //         }
    //     }

    //     return "users/editSchedule"; 
    // }
}


