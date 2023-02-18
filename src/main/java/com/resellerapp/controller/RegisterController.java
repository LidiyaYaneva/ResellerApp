package com.resellerapp.controller;

import com.resellerapp.model.dtos.UserRegistrationDTO;
import com.resellerapp.service.UserService;
import com.resellerapp.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class RegisterController {

    private final UserService userService;

    private final CurrentUser currentUser;

    @Autowired
    public RegisterController(UserService userService, CurrentUser currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @ModelAttribute("userRegistrationDTO")
    public UserRegistrationDTO init (Model model){
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        model.addAttribute("userRegistrationDTO" , userRegistrationDTO);
        return userRegistrationDTO;
    }

    @GetMapping("register")
    public String displayRegisterPage(){

        if (currentUser.isLoggedIn())
            return "home";
        else return "register";

    }
    @PostMapping("register")
    public String register(@Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO", bindingResult);
            return "redirect:/users/register";
        }
        this.userService.registerUser(userRegistrationDTO);
        return "redirect:/login";

    }
}
