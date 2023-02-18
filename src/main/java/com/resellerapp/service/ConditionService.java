package com.resellerapp.service;

import com.resellerapp.model.entity.Condition;
import com.resellerapp.model.enums.ConditionName;
import com.resellerapp.repository.ConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
public class ConditionService {

    private final ConditionRepository conditionRepository;

    @Autowired
    public ConditionService(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    @PostConstruct
    public void initMoods() {
        if (this.conditionRepository.count() != 0) {
            return;
        }

        Arrays.stream(ConditionName.values())
                .forEach( e -> {
                    Condition condition = new Condition();
                    condition.setConditionName(e);
                    condition.setDescription(e.getValue());

                    this.conditionRepository.save(condition);
                });

    }
}
