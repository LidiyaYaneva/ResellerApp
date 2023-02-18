package com.resellerapp.service;

import com.resellerapp.model.dtos.LoginUserDTO;
import com.resellerapp.model.dtos.UserRegistrationDTO;
import com.resellerapp.model.entity.User;
import com.resellerapp.repository.UserRepository;
import com.resellerapp.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final CurrentUser currentUser;

    private final PasswordEncoder passwordEncoder;

    private  final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, CurrentUser currentUser, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.currentUser = currentUser;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public boolean hasUniqueEmailAndUsername(String email, String username){

        Optional<User> optUserByEmail = this.userRepository.findByEmail(email);
        Optional<User> optUserByUsername = this.userRepository.findByUsername(username);

        return optUserByEmail.isEmpty() && optUserByUsername.isEmpty();

    }

    public void registerUser(UserRegistrationDTO userRegistrationDTO) {

        boolean confirmPasswordMatches = userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword());

        if(hasUniqueEmailAndUsername(userRegistrationDTO.getEmail(), userRegistrationDTO.getUsername())
                && confirmPasswordMatches) {
           User userEntity = this.modelMapper.map(userRegistrationDTO, User.class);
           userEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
           this.userRepository.save(userEntity);
       }
    }

        public boolean login (LoginUserDTO loginUserDTO) {

            Optional<User> optUser = this.userRepository
                    .findByUsername(loginUserDTO.getUsername());

            if (optUser.isPresent()) {
                String encodedPassword = optUser.get().getPassword();
                String rawPassword = loginUserDTO.getPassword();
                boolean success = passwordEncoder.matches(rawPassword, encodedPassword);

                if (success) {
                    login(optUser.get());
                    return true;
                }
            }
            return false;
    }


    public void logout() {
        this.currentUser.clear();
    }

    private void login (User userEntity) {

        this.currentUser.setId(userEntity.getId());
        this.currentUser.setLoggedIn(true);
        this.currentUser.setUsername(userEntity.getUsername());

    }

    public boolean checkCredentials(String username, String password) {
        return false;
    }
}
