package com.resellerapp.controller;

import com.resellerapp.model.dtos.AddOfferDTO;
import com.resellerapp.service.OfferService;
import com.resellerapp.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    private final CurrentUser currentUser;

    @Autowired
    public OfferController(OfferService offerService, CurrentUser currentUser) {
        this.offerService = offerService;
        this.currentUser = currentUser;
    }


    @ModelAttribute("addOfferDTO")
    public AddOfferDTO init (Model model) {
        AddOfferDTO addOfferDTO = new AddOfferDTO();
        model.addAttribute("addOfferDTO", addOfferDTO);
        return addOfferDTO;
    }

    @GetMapping("/add")
    public String displayAddOffer () {

        if (currentUser.isLoggedIn())
            return "offer-add";
        else return "redirect:/login";
    }

    @PostMapping("/add")
    public String addOffer (@Valid AddOfferDTO addOfferDTO, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addOfferDTO", addOfferDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addOfferDTO", bindingResult);
            return "redirect:/offers/add";
        }

        if(currentUser.isLoggedIn()){
            this.offerService.addOffer(addOfferDTO);
        }

        return "redirect:/home";

    }

    @GetMapping("/remove/{id}")
    public String removeOffer(@PathVariable Long id) {
        this.offerService.removeOfferById(id);

        return "redirect:/home";
    }

    @GetMapping("buy-offer/{id}")
    public String buyOffer(@PathVariable Long id){
        offerService.buyOfferWithId(id);
        return "redirect:/home";

    }


}
