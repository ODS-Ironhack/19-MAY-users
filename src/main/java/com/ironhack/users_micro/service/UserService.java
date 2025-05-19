package com.ironhack.users_micro.service;

import com.ironhack.users_micro.dto.AccountDTO;
import com.ironhack.users_micro.dto.UserResponseDTO;
import com.ironhack.users_micro.exception.UserNotFoundException;
import com.ironhack.users_micro.model.User;
import com.ironhack.users_micro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getUserById(long id){
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            AccountDTO account = restTemplate.getForObject("http://accounts-micro/api/account/" + optionalUser.get().getAccountID(), AccountDTO.class);

            System.out.println("LA CUENTA ES: " + account);

            UserResponseDTO response = new UserResponseDTO(optionalUser.get(), account);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            throw new UserNotFoundException("The user was not found");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User patchAccountId(Long userId, Long accountId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User foundUser;
        if (optionalUser.isPresent()) {
            foundUser = optionalUser.get();

            foundUser.setAccountID(accountId);
            userRepository.save(foundUser);
            return  foundUser;

        }else{
            throw new UserNotFoundException("Couldn't find the user");
        }

    }
}
