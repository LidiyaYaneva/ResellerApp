package com.resellerapp.controller;

import com.resellerapp.model.dtos.DisplayOfferDTO;
import com.resellerapp.service.OfferService;
import com.resellerapp.util.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final CurrentUser currentUser;

    private final OfferService offerService;

    public HomeController(CurrentUser currentUser, OfferService offerService) {
        this.currentUser = currentUser;
        this.offerService = offerService;
    }

    @GetMapping
    public String index() {
        if (currentUser.isLoggedIn()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        if (!currentUser.isLoggedIn()) {
            return "redirect:/";
        }
        List<DisplayOfferDTO> myOffers = this.offerService.myOffers();
        List<DisplayOfferDTO> boughtItems = this.offerService.boughtItems();
        List<DisplayOfferDTO> otherOffers = this.offerService.otherOffers();

        model.addAttribute("myOffers", myOffers);
        model.addAttribute("boughtItems", boughtItems);
        model.addAttribute("otherOffers", otherOffers);
        return "home";
    }

}
