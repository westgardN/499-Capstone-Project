package edu.metrostate.ics499.prim.controller;

import java.util.List;

import javax.validation.Valid;

import edu.metrostate.ics499.prim.datatransfer.UserDataTransfer;
import edu.metrostate.ics499.prim.exception.EmailExistsException;
import edu.metrostate.ics499.prim.exception.SsoIdExistsException;
import edu.metrostate.ics499.prim.exception.UsernameExistsException;
import edu.metrostate.ics499.prim.model.Role;
import edu.metrostate.ics499.prim.model.User;
import edu.metrostate.ics499.prim.service.RoleService;
import edu.metrostate.ics499.prim.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller handles all User based requests such as adding, editing, and deleting users.
 */
@Controller
@SessionAttributes("roles")
public class UserController {

    static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    MessageSource messageSource;

    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = { "/user", "/user/list" }, method = RequestMethod.GET)
    public ModelAndView listUsers(ModelMap model) {

        List<User> users = userService.findAll();
        return new ModelAndView("/user/userList", "users", users);
    }

    /**
     * This method will provide the medium to add a new user.
     */
    @RequestMapping(value = { "/user/new" }, method = RequestMethod.GET)
    public String showNewUserRegistrationForm(ModelMap model) {
        UserDataTransfer user = new UserDataTransfer();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", getPrincipal());
        return "user/registration";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input
     */
    @RequestMapping(value = { "/user/new" }, method = RequestMethod.POST)
    public ModelAndView registerNewUser(@ModelAttribute("user") @Valid UserDataTransfer userDataTransfer,
                                        BindingResult result,
                                        WebRequest request) {
        logger.info("Registering a new user account: {}", userDataTransfer);
        String resultView = "user/registration";

        User user = new User();

        if (!result.hasErrors()) {
            user = createUserAccount(userDataTransfer, result);
        }

        if (user == null || result.hasErrors()) {
            return new ModelAndView(resultView, "user", userDataTransfer);
        } else {
            resultView = "/user/registrationSuccess";
            return new ModelAndView(resultView, "user", userDataTransfer);
        }
    }

    private User createUserAccount(UserDataTransfer userDataTransfer, BindingResult result) {
        User user = null;

        try {
            user = userService.registerNewUser(userDataTransfer);
        } catch (EmailExistsException e) {
            result.rejectValue("email", "message.regEmailError");
        } catch (SsoIdExistsException e) {
            result.rejectValue("ssoId", "message.regSsoIdError");
        } catch (UsernameExistsException e) {
            result.rejectValue("username", "message.regUsernameError");
        }

        return user;
    }


    /**
     * This method will provide the medium to update an existing user.
     */
    @RequestMapping(value = { "/user/edit/{ssoId}" }, method = RequestMethod.GET)
    public String editUser(@PathVariable String ssoId, ModelMap model) {
        User user = userService.findBySsoId(ssoId);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        return "/user/registration";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating user in database. It also validates the user input
     */
    @RequestMapping(value = { "/user/edit/{ssoId}" }, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
                             ModelMap model, @PathVariable String ssoId) {

        if (result.hasErrors()) {
            return "registration";
        }

        /* Disable updating the ssoId for now.
        if(!userService.isSsoIdUnique(user.getId(), user.getSsoId())){
            FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }*/


        userService.update(user);

        model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        return "/user/registrationsuccess";
    }

    /**
     * This method will allow the currently logged in user to view their settings.
     */
    @RequestMapping(value = { "/user/profile" }, method = RequestMethod.GET)
    public String viewProfile(ModelMap model) {
        User user = userService.findBySsoId(getPrincipal());

        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        return "member/profile";
    }

    /**
     * This method updates the currently logged in user's profile.
     */
    @RequestMapping(value = { "/user/profile" }, method = RequestMethod.POST)
    public String updateProfile(@Valid User user, BindingResult result,
                                ModelMap model) {

        if (result.hasErrors()) {
            return "member/profile";
        }

        /* Disable updating the ssoId for now.
        if(!userService.isSsoIdUnique(user.getId(), user.getSsoId())){
            FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }*/


        userService.update(user);

        model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
        model.addAttribute("loggedinuser", getPrincipal());

        return "member/profile";
    }

    /**
     * This method will delete an user by it's SSOID value.
     */
    @RequestMapping(value = { "/user/delete/{ssoId}" }, method = RequestMethod.POST)
    public String deleteUser(@PathVariable String ssoId) {
        userService.deleteBySsoId(ssoId);
        return "redirect:/user/list";
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal(){
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    /**
     * This method will provide Role list to views
     */
    @ModelAttribute("roles")
    public List<Role> initializeRoles() {
        return roleService.findAll();
    }

}
