package com.example.gnosi.usergnosi.service;

import com.example.gnosi.usergnosi.controller.CreateUserDto;
import com.example.gnosi.usergnosi.controller.UpdateUserDto;
import com.example.gnosi.usergnosi.entity.User;
import com.example.gnosi.usergnosi.repository.UserRepository;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {

        // DTO -> ENTITY
        var entity = new User(
                createUserDto.firstName(),
                createUserDto.lastName(),
                createUserDto.email(),
                createUserDto.password(),
                createUserDto.userType()
        );

        var userSave = userRepository.save(entity);

        return userSave.getUserId();

    }

    public Optional<User> getUserById(String userId) {
       return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto) {

       var id = UUID.fromString(userId);
       var userEntity = userRepository.findById(id);

       if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.firstName() != null) {
                user.setFirstName(updateUserDto.firstName());
            }
            if (updateUserDto.lastName() != null) {
                user.setLastName(updateUserDto.lastName());
            }
            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
       }
    }


    public void deleteById(String userId) {

        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }

    }
}
