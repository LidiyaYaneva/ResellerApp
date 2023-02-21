package com.resellerapp.repository;

import com.resellerapp.model.entity.Offer;
import com.resellerapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByCreatorId(Long creatorId);

    List<Offer> findAllByCreator(User creator);

    List<Offer> findAllByCreatorIdNotAndBuyerIsNull(Long creatorId);

    List<Offer> findAllByBuyerId(Long buyerId);
}
