package com.communicators.welltalk.Entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.communicators.welltalk.Service.UserService;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private int id;

    @Column(name = "institutionalEmail")
    private String institutionalEmail;

    @Column(name = "idNumber")
    private String idNumber;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "password")
    private String password;

    @Column(name = "image")
    private String image;

    @Column(name = "dateOfCreation")
    private LocalDateTime dateOfCreation;

    @Column(name = "dateOfModification")
    private LocalDateTime dateOfModification;

    @Column(name = "isVerified")
    private boolean isVerified;

    @Column(name = "college")
    protected String college;

    @Column(name = "program")
    private String program;

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    @Enumerated(value = EnumType.STRING)
    Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name = "isDeleted")
    private boolean isDeleted;

    public UserEntity() {

    }

    public UserEntity(String institutionalEmail, String idNumber, String firstName, String lastName, String gender,
            String password, String image, String role, String college, String program) {
        this.institutionalEmail = institutionalEmail;
        this.idNumber = idNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.password = password;
        this.image = image;
        isDeleted = false;
        this.isVerified = false;
        this.college = college;
        this.program = program;
    }

    public UserEntity(String institutionalEmail, String idNumber, String firstName, String lastName, String gender,
            String password, String role, String college, String program) {
        this.institutionalEmail = institutionalEmail;
        this.idNumber = idNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.password = password;
        isDeleted = false;
        this.isVerified = false;
        this.college = college;
        this.program = program;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setInstitutionalEmail(String institutionalEmail) {
        this.institutionalEmail = institutionalEmail;
    }

    public String getInstitutionalEmail() {
        return institutionalEmail;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @PrePersist
    protected void onCreate() {
        dateOfCreation = LocalDateTime.now();
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    @PreUpdate
    protected void onUpdate() {
        dateOfModification = LocalDateTime.now();
    }

    public LocalDateTime getDateOfModification() {
        return dateOfModification;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return institutionalEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isVerified() {
        return isVerified;
    }

    @Autowired
    private transient UserService userService;

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
        if (isVerified) {
            userService.assignTeacherOrStudentToCounselor(this.id);
        }
    }
    

}
