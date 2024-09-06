package com.communicators.welltalk.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.UserEntity;
import com.communicators.welltalk.Repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean existsByEmail(String institutionalEmail) {
        return userRepository.existsByInstitutionalEmail(institutionalEmail);
    }

    public boolean existsByIdNumber(String idNumber) {
        return userRepository.existsByIdNumber(idNumber);
    }

    public List<UserEntity> getAllIsNotDeletedUsers() {
        return userRepository.findByIsDeletedFalse();
    }

    public UserEntity getUserById(int id) {
        return userRepository.findByIdAndIsDeletedFalse(id).get();
    }

    public UserEntity getUserByIdAndIsNotDeleted(int id) {
        return userRepository.findByIdAndIsDeletedFalse(id).get();
    }

    public boolean isInstitutionalEmailPresent(String institutionalEmail) {
        return userRepository.findByInstitutionalEmailAndIsDeletedFalse(institutionalEmail).isPresent();
    }

    @SuppressWarnings("finally")
    public UserEntity updateUser(int id, UserEntity user) {
        UserEntity userToUpdate = new UserEntity();
        try {
            userToUpdate = userRepository.findByIdAndIsDeletedFalse(id).get();

            userToUpdate.setInstitutionalEmail(user.getInstitutionalEmail());
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setGender(user.getGender());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setImage(user.getImage());
            userToUpdate.setVerified(user.isVerified());
        } catch (Exception e) {
            throw new IllegalArgumentException("User " + user.getId() + " does not exist.");
        } finally {
            return userRepository.save(userToUpdate);
        }
    }

    public boolean deleteUser(int id) {
        UserEntity user = userRepository.findById(id).get();
        if (user != null) {
            user.setIsDeleted(true);
            userRepository.save(user);
            return true;
        } else {
            System.out.println("User " + id + " does not exist.");
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByInstitutionalEmailAndIsDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
