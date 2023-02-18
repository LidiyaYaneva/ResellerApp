package com.resellerapp.service;

import com.resellerapp.model.dtos.AddOfferDTO;
import com.resellerapp.model.dtos.DisplayOfferDTO;
import com.resellerapp.model.entity.Condition;
import com.resellerapp.model.entity.Offer;
import com.resellerapp.model.entity.User;
import com.resellerapp.model.enums.ConditionName;
import com.resellerapp.repository.ConditionRepository;
import com.resellerapp.repository.OfferRepository;
import com.resellerapp.repository.UserRepository;
import com.resellerapp.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    private final ConditionRepository conditionRepository;

    private final UserRepository userRepository;

    private final CurrentUser currentUser;

    private final ModelMapper modelMapper;

    public OfferService(OfferRepository offerRepository, ConditionRepository conditionRepository, UserRepository userRepository, CurrentUser currentUser, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.conditionRepository = conditionRepository;
        this.userRepository = userRepository;
        this.currentUser = currentUser;
        this.modelMapper = modelMapper;
    }


    public void addOffer(AddOfferDTO addOfferDTO) {

        if (currentUser.isLoggedIn() ){

            Optional<Condition> optionalCondition = this.conditionRepository
                    .findByConditionName(addOfferDTO.getConditionName());
            Offer offer = this.modelMapper.map(addOfferDTO, Offer.class);
            optionalCondition.ifPresent(offer::setCondition);

            Long userId = currentUser.getId();
            Optional<User> optUser = this.userRepository.findById(userId);
            optUser.ifPresent(offer::setCreator);
            offer.setBuyer(null);

            this.offerRepository.save(offer);

        }

    }

    public List<DisplayOfferDTO> myOffers () {
        Long currentUserId = this.currentUser.getId();
        List<Offer> offers = this.offerRepository.findAllByCreatorId(currentUserId);

        DisplayOfferDTO[] displayMyOffersDTOS = this.modelMapper.map(offers, DisplayOfferDTO[].class);

        return Arrays.stream(displayMyOffersDTOS).collect(Collectors.toList());

    }

    public List<DisplayOfferDTO> boughtItems () {
        Long currentUserId = this.currentUser.getId();
        List<Offer> offers = this.offerRepository.findAllByBuyerId(currentUserId);

        DisplayOfferDTO[] displayBoughtItemsDTOS = this.modelMapper.map(offers, DisplayOfferDTO[].class);


        return Arrays.stream(displayBoughtItemsDTOS).collect(Collectors.toList());

    }

    public List<DisplayOfferDTO> otherOffers () {
        Long currentUserId = this.currentUser.getId();
        List<Offer> offers = this.offerRepository.findAllByCreatorIdNot(currentUserId);

        DisplayOfferDTO[] displayOtherOffersDTOS = this.modelMapper.map(offers, DisplayOfferDTO[].class);

        return Arrays.stream(displayOtherOffersDTOS).collect(Collectors.toList());

    }

    public void removeOfferById(Long id) {
        this.offerRepository.deleteById(id);
    }

    public void buyOfferWithId(Long offerId) {

        Optional<User> user = this.userRepository.findById(currentUser.getId());

        if (user.isPresent()) {

            Optional<Offer> optionalOffer = this.offerRepository.findById(offerId);

            optionalOffer.ifPresent(offer -> offer.setBuyer(user.get()));

            List<Offer> boughtItems = this.offerRepository.findAllByBuyerId(currentUser.getId());
            optionalOffer.ifPresent(boughtItems::add);

            Long sellerId = optionalOffer.get().getCreator().getId();
            Optional<User> seller = this.userRepository.findById(sellerId);
            List<Offer> createdOffersBySeller = this.offerRepository.findAllByCreator(seller.get());
            optionalOffer.ifPresent(createdOffersBySeller::remove);

            this.offerRepository.save(optionalOffer.get());

        }
    }
}
