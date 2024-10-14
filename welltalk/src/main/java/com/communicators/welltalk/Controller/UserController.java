package com.communicators.welltalk.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.communicators.welltalk.Entity.AuthenticationResponse;
import com.communicators.welltalk.Entity.PasswordResetTokenEntity;
import com.communicators.welltalk.Entity.UserEntity;
import com.communicators.welltalk.Repository.PasswordResetTokenRepository;
import com.communicators.welltalk.Repository.UserRepository;
import com.communicators.welltalk.Service.AuthenticationService;
import com.communicators.welltalk.Service.EmailService;
import com.communicators.welltalk.Service.PasswordReset;
import com.communicators.welltalk.Service.UserService;
import com.communicators.welltalk.dto.EmailCheckDTO;
import com.communicators.welltalk.dto.IdNumberCheckDTO;
import com.communicators.welltalk.dto.PasswordChangeDTO;
import com.communicators.welltalk.dto.PasswordVerificationDTO;

@CrossOrigin("http://localhost:3000")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordReset passwordReset;

    PasswordResetTokenEntity passwordResetTokenEntity;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    public AuthenticationService authenticationService;

    @Autowired
    public EmailService emailService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserEntity> register(@RequestBody UserEntity request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @GetMapping("/existsByEmail")
    public ResponseEntity<?> getMethodName(@RequestParam String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }

    @GetMapping("/existsByIdNumber")
    public ResponseEntity<?> existsByIdNumber(@RequestParam String idNumber) {
        return ResponseEntity.ok(userService.existsByIdNumber(idNumber));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String email) {
        UserEntity user = userRepository.findByInstitutionalEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        String token = UUID.randomUUID().toString();
        passwordReset.createPasswordResetTokenForUser(user, token);

        String message = "Here is the link to reset your password: http://localhost:3000/" + token + "/changepassword";

        emailService.sendSimpleMessage(
                email,
                "Password Reset Request",
                message);

        return ResponseEntity.ok("Reset password email sent.");
    }

    @PutMapping("/forgotPassword")
    public ResponseEntity<?> changePassword(@RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword) {
        if (!passwordReset.validatePasswordResetToken(token)) {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        PasswordResetTokenEntity resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Couldn't find the password reset token."));

        UserEntity user = resetToken.getUser();
        passwordReset.changeUserPassword(user, newPassword);

        // Invalidate the token after use
        passwordResetTokenRepository.delete(resetToken);

        return ResponseEntity.ok("Password changed successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserEntity request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getAllUnverifiedUsers")
    public ResponseEntity<List<UserEntity>> getAllUnverifiedUsers() {
        List<UserEntity> users = userService.getAllUnverifiedUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getAllVerifiedUsers")
    public ResponseEntity<List<UserEntity>> getAllVerifiedUsers() {
        List<UserEntity> users = userService.getAllVerifiedUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO request) {
        return ResponseEntity.ok(authenticationService.changePassword(request));
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable int id) {
        UserEntity user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable int id, @RequestBody UserEntity user) {
        UserEntity updatedUser = userService.updateUser(id, user);
        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/verifyUserAccount/{id}")
    public ResponseEntity<Void> verifyUserAccount(@PathVariable int id) {
        boolean isVerified = userService.verifyUserAccount(id);
        if (isVerified) {

            // isVerified -> true

            // notification.service (userid)
                // user -> fetch user by user Id
                // assignedCounselor - fetch counselor by user Id

                // if user.role == student && user.isVerified == 1
            
                    // notification 
                        // type - verified_student
                        // sender - assignedCounselor
                        // receiver - assignedCounselor
                        // user - user
            
            
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/validateChangePasswordToken")
    public ResponseEntity<?> validateChangePasswordToken(@RequestParam String token) {
        boolean isValid = passwordReset.validatePasswordResetToken(token);
        if (isValid) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token is invalid");
        }
    }

    @PostMapping("/verifyPassword")
    public ResponseEntity<?> verifyPassword(@RequestBody PasswordVerificationDTO request) {
        try {
            boolean isValid = authenticationService.verifyPassword(request);
            return ResponseEntity.ok(isValid ? "Password is correct." : "Password is incorrect.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestBody EmailCheckDTO request) {
        try {
            boolean emailExists = authenticationService.emailExists(request.getEmail());
            return ResponseEntity.ok(emailExists ? "Email exists." : "Email does not exist.");
        } catch (RuntimeException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/checkIdNumber")
    public ResponseEntity<?> checkIdNumber(@RequestBody IdNumberCheckDTO request) {
        try {
            boolean idExists = authenticationService.idExists(request.getIdNumber());
            return ResponseEntity.ok(idExists ? "ID number exists." : "ID number does not exist.");
        } catch (RuntimeException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

}
