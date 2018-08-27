package edu.metrostate.ics499.prim.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import edu.metrostate.ics499.prim.datatransfer.PasswordDataTransfer;
import edu.metrostate.ics499.prim.datatransfer.UserDataTransfer;
import edu.metrostate.ics499.prim.event.OnNewUserRegistrationCompleteEvent;
import edu.metrostate.ics499.prim.exception.EmailExistsException;
import edu.metrostate.ics499.prim.exception.InvalidCurrentPasswordException;
import edu.metrostate.ics499.prim.exception.SsoIdExistsException;
import edu.metrostate.ics499.prim.exception.UsernameExistsException;
import edu.metrostate.ics499.prim.model.Role;
import edu.metrostate.ics499.prim.model.SecurityToken;
import edu.metrostate.ics499.prim.model.User;
import edu.metrostate.ics499.prim.service.RoleService;
import edu.metrostate.ics499.prim.service.UserService;
import edu.metrostate.ics499.prim.util.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

/**
 * This controller handles all User based requests such as adding, editing, and deleting users.
 */
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

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
    public ModelAndView registerNewUser(@ModelAttribute("user") @Valid final UserDataTransfer userDataTransfer,
                                        BindingResult result,
                                        final HttpServletRequest request) {
        logger.info("Registering a new user account: {}", userDataTransfer);
        String resultView = "user/registration";

        User user = new User();

        if (!result.hasErrors()) {
            user = createUserAccount(userDataTransfer, result);
        }

        if (user == null || result.hasErrors()) {
            logger.info("Registering a new user account failed with errors");
            return new ModelAndView(resultView, "user", userDataTransfer);
        } else {
            logger.info("Registered new user account");
            resultView = "user/registrationSuccess";
            applicationEventPublisher.publishEvent(new OnNewUserRegistrationCompleteEvent(user, request.getLocale(), getAppUrl(request)));
            return new ModelAndView(resultView, "user", userDataTransfer);
        }
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
        return "user/registration";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating user in database. It also validates the user input
     */
    @RequestMapping(value = { "/user/edit/{ssoId}" }, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
                             ModelMap model, @PathVariable String ssoId) {

        if (result.hasErrors()) {
            return "user/registration";
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
        return "user/registrationSuccess";
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

    private String getAppUrl(HttpServletRequest request) {
        String answer = "";

        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            answer = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();;
        } else {
            answer = request.getScheme() + "://" + request.getServerName() + request.getContextPath();;
        }

        return answer;
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
}
