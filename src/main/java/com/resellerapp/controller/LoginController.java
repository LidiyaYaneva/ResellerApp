package com.resellerapp.controller;

import com.resellerapp.model.dtos.LoginUserDTO;
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
@RequestMapping("/")
public class LoginController {

    private final UserService userService;

    private final CurrentUser currentUser;

    @Autowired
    public LoginController(UserService userService, CurrentUser currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @ModelAttribute("loginUserDTO")
    public LoginUserDTO init (Model model){
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        model.addAttribute("loginUserDTO" , loginUserDTO);
        return loginUserDTO;
    }

    @GetMapping("login")
    public String displayLoginPage(){

        if (currentUser.isLoggedIn())
            return "home";

        else return "login";
    }
    @PostMapping("login")
    public String login(@Valid LoginUserDTO loginUserDTO, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("loginUserDTO", loginUserDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginUserDTO", bindingResult);
            return "redirect:/login";
        }

        boolean validCredentials = this.userService.login(loginUserDTO);

        if (!validCredentials) {
            redirectAttributes
                    .addFlashAttribute("loginUserDTO", loginUserDTO)
                    .addFlashAttribute("validCredentials", false);
            return "redirect:/login";
        }
        else return "redirect:/home";

    }

    @GetMapping("logout")
    public String logout(){
        currentUser.clear();
        return "index";
    }

}
