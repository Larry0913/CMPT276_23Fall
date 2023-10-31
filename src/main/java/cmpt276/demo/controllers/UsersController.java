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

import cmpt276.demo.dao.PayrollRepository;
import cmpt276.demo.dao.UserRepository;
import cmpt276.demo.dao.UserScheduleRepository;
import cmpt276.demo.dao.WeekRepository;
import cmpt276.demo.models.Payroll;
import cmpt276.demo.models.User;
import cmpt276.demo.models.Week;
import cmpt276.demo.models.UserSchedule;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UsersController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private WeekRepository weekRepo;

    @Autowired
    private UserScheduleRepository userscheduleRepo;

    @Autowired
    private PayrollRepository payrollRepository;

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
        return "redirect:/";
    }

    /**
     * add user
     * 
     * @param newuser
     * @param response
     * @return
     */
    @PostMapping("/users/addb")
    public String addUserB(@RequestParam Map<String, String> newuser, HttpServletResponse response,
            HttpServletRequest request, Model model) {
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
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
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
    public ModelAndView showPerformance(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        User session_user = (User) request.getSession().getAttribute("user");
        // we should not add session id in the model
        // this is just an example of adding dynamic data into model
        mv.addObject("user", session_user);
        mv.setViewName("users/performance");
        return mv;
    }

    @GetMapping("/users/addressBook")
    public String showAddressBook(HttpServletRequest request, Model model, HttpSession session) {
        List<User> userinforlist = userRepo.findAll();
        model.addAttribute("allusers", userinforlist);
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "users/addressBook";
    }

    @RequestMapping("/users/delete")
    public String deleteUser(HttpServletRequest request, Model model, Integer uid) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        userRepo.deleteById(uid);
        return "users/addressBook";
    }

    @RequestMapping("/users/update")
    public String updateUser(HttpServletRequest request, Model model, @RequestParam Map<String, String> newuser) {
        String newName = newuser.get("name");
        String newPwd = newuser.get("password");
        boolean newAdmin = false;

        if (newuser.get("isAdmin") != null) {
            newAdmin = true;
        }

        String newEmail = newuser.get("email");
        String newPhone = newuser.get("phoneNumber");
        String department = newuser.get("department");
        String uid = newuser.get("uid");

        User newUser = new User(newName, newPwd, newAdmin);
        newUser.setEmail(newEmail);
        newUser.setPhoneNumber(newPhone);
        newUser.setDepartment(department);
        newUser.setUid(Integer.valueOf(uid));
        userRepo.save(newUser);

        return "redirect:/users/addressBook";
    }

    @GetMapping("/users/toUpdateUserPage")
    public String toUpdateUserPage(HttpServletRequest request, Model model, Integer uid) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        Optional<User> byId = userRepo.findById(uid);
        model.addAttribute("byId", byId.get());
        return "users/updateUser";
    }

    @GetMapping("/users/toAddUserPage")
    public String toAddUserPage(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
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
    public ModelAndView showSettings(HttpSession session) {
        ModelAndView model = new ModelAndView();
        User user = (User) session.getAttribute("user");
        Integer id = user.getUid();
        User user1 = userRepo.findById(id).get();
        session.setAttribute("user", user1);
        model.addObject("user", user1);
        model.setViewName("users/settings");
        return model;
    }

    @RequestMapping("/users/update/my")
    public String updateMyself(HttpServletRequest request, Model model, User user) {
        User user1 = userRepo.findById(user.getUid()).get();
        user.setAdmin(user1.isAdmin());
        userRepo.save(user);
        User user11 = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user11);
        return "redirect:/users/dashboard";
    }
  
  //========================SCHEDULE IMPLEMENTATION=========================
  
  
    @GetMapping("/users/schedule")
    public String getAssociateWeekForm(Model model ,HttpServletRequest request, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user != null){

            if(user.isAdmin()){
                List<User> users = userRepo.findAll();
                List<Week> weeks = weekRepo.findAll();
                List<UserSchedule> userSchedule = userscheduleRepo.findAll();
                // Add the lists to the model so they can be displayed in the form
                model.addAttribute("users", users);
                model.addAttribute("weeks", weeks); 
                model.addAttribute("userSchedule", userSchedule);

                return "users/admin_schedule";

            }else{

                List<Week> weeks = weekRepo.findAll();
                List<UserSchedule> userSchedule = userscheduleRepo.findAll();
                model.addAttribute("weeks", weeks); 
                model.addAttribute("userSchedule", userSchedule);
                model.addAttribute("user", user); 

                return "/users/select_sched_week"; 
            }
            

        }else{

            return "/users/login"; 

        }
        
    }

    @PostMapping("/users/view-schedule")
    public String viewSchedule(@RequestParam Map<String, String> formData,
                                @RequestParam("username") String username,
                                @RequestParam("weekname") String weekName,
                                @RequestParam(value = "days", required = false) List<String> selectedDays,
                                HttpServletRequest request) {

        return "redirect:/users/viewSchedule?username=" + username + "&weekName=" + weekName;
    }

    @GetMapping("/users/viewSchedule")
    public String showViewSchedulePage(
        @RequestParam("username") String username, 
        @RequestParam("weekName") String weekName, 
        Model model) {

        // Retrieve the user and week information based on the provided username and weekName.
        User user = userRepo.findByUsername(username).get(0);
        Week week = weekRepo.findByWeekName(weekName).get(0);

        // Retrieve the UserSchedule for the user and week.
        // Then, add the user and week data to the model.
        model.addAttribute("username", username);
        model.addAttribute("weekName", weekName);

        UserSchedule userSchedule = userscheduleRepo.findByUserAndWeek(user, week);

        if (userSchedule != null) {
            // If a user schedule is found, add it to the model
            model.addAttribute("userSchedule", userSchedule.getDays());
            model.addAttribute("lateDays", userSchedule.getLateDays());
            model.addAttribute("totHours", userSchedule.calculateTotalHours());
        } else {
            // If no user schedule is found, add a message to the model
            model.addAttribute("noSchedule", "No Schedule Yet");
        }
        // Finally, return the "editSchedule.html" template.
        return "users/viewSchedule";
    }




    @PostMapping("/users/associate-week")
    public String associateWeek(@RequestParam Map<String, String> formData,
                                @RequestParam("username") String username,
                                @RequestParam("weekname") String weekName,
                                @RequestParam(value = "days", required = false) List<String> selectedDays,
                                HttpServletRequest request) {

        return "redirect:/users/editSchedule?username=" + username + "&weekName=" + weekName;
    }

    @GetMapping("/users/editSchedule")
    public String showEditSchedulePage(
        @RequestParam("username") String username, 
        @RequestParam("weekName") String weekName, 
        Model model) {

        // Retrieve the user and week information based on the provided username and weekName.
        User user = userRepo.findByUsername(username).get(0);
        Week week = weekRepo.findByWeekName(weekName).get(0);

        // Retrieve the UserSchedule for the user and week.
        // Then, add the user and week data to the model.
        model.addAttribute("username", username);
        model.addAttribute("weekName", weekName);

        UserSchedule userSchedule = userscheduleRepo.findByUserAndWeek(user, week);

        if (userSchedule != null) {
            // If a user schedule is found, add it to the model
            model.addAttribute("userSchedule", userSchedule.getDays());
            model.addAttribute("lateDays", userSchedule.getLateDays());
            model.addAttribute("totHours", userSchedule.calculateTotalHours());
        } else {
            // If no user schedule is found, add a message to the model
            model.addAttribute("noSchedule", "No Schedule Yet");
        }
        // Finally, return the "editSchedule.html" template.
        return "users/editSchedule";
    }

    @PostMapping("/users/updateSchedule")
    public String updateSchedulePage(
        @RequestParam Map<String, String> formData,
        @RequestParam("username") String username,
        @RequestParam("weekName") String weekName,
        @RequestParam("lateDays") int lateDays,
        @RequestParam(value = "selectedDays", required = false) List<String> selectedDays,
        Model model) {

        String userName = formData.get("username");
        String weekname= formData.get("weekName");
        User user = userRepo.findByUsername(userName).get(0);
        Week week = weekRepo.findByWeekName(weekname).get(0);

        // Initialize a 7-character string with all '-' characters
        StringBuilder daysString = new StringBuilder("-------");

        // Process selected days and update the string
        if (selectedDays != null) {
            for (String day : selectedDays) {
                switch (day) {
                    case "M":
                        daysString.setCharAt(0, 'M');
                        break;
                    case "T":
                        daysString.setCharAt(1, 'T');
                        break;
                    case "W":
                        daysString.setCharAt(2, 'W');
                        break;
                    case "H":
                        daysString.setCharAt(3, 'H');
                        break;
                    case "F":
                        daysString.setCharAt(4, 'F');
                        break;
                    case "S":
                        daysString.setCharAt(5, 'S');
                        break;
                    case "U":
                        daysString.setCharAt(6, 'U');
                        break;
                }
            }
        }

        // Create the association object and save it to the database
        UserSchedule existingSchedule = userscheduleRepo.findByUserAndWeek(user, week);

        if (existingSchedule != null) {
            // Update the existing record
            existingSchedule.setDays(daysString.toString());
            existingSchedule.setLateDays(lateDays);
            userscheduleRepo.save(existingSchedule);
            int tot_hours = existingSchedule.calculateTotalHours();
            existingSchedule.setTotHours(tot_hours);

        } else {
            // Create a new association
            UserSchedule newSchedule = new UserSchedule(user, week, daysString.toString());
            newSchedule.setLateDays(lateDays);
            userscheduleRepo.save(newSchedule);
            int tot_hours = newSchedule.calculateTotalHours();
            newSchedule.setTotHours(tot_hours);
        }

        return "redirect:/users/editSchedule?username=" + username + "&weekName=" + weekName; 
    }

    @GetMapping("/users/payrollUser")
    public String showPayroll(Model model ,HttpServletRequest request) {
        User user = (User) request.getAttribute("session_user"); // Assuming you set the user in the session.
        List<Payroll> payrolls = payrollRepository.findByUser(user);
        model.addAttribute("payrolls", payrolls);

        return "users/payrollUser"; 
    }

    @PostMapping("/setSalary")
    public String setSalary(@RequestParam int userId, @RequestParam BigDecimal hourlySalary) {
        User userToUpdate = userRepo.findById(userId).orElse(null);
        
        if (userToUpdate != null) {
            userToUpdate.setHourlySalary(hourlySalary);
            userRepo.save(userToUpdate);
        }

        return "redirect:/users/payrollAdmin";
    }

}



