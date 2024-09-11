package com.onbiron.onbironpdks.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
@Table(name = "users")
@CrossOrigin("/*")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique =true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "birthdate", nullable = false)
    private LocalDateTime birthdate;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime=LocalDateTime.now();

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted =false;
    
    @Column(name = "title")
    private String title;

    @Column(name = "Password")
    private String password;

    @Column(name="email")
    private String email;
    
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeeEntryExit> entryExits;


    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAnnualReportInfo> annualReportInfo;

    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL)
    private UserRemainingAnnualInfo userRemainingAnnualInfo;
    
    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL)
    private UserRole userRole;
    
    public Users() {
    	
    }
    
    public Users(Long id, String username, String name, String surname, String registrationNumber, String phoneNumber,
			LocalDateTime birthdate, LocalDateTime creationTime, String title, String password, String email) {

    	this.id = id;
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.registrationNumber = registrationNumber;
		this.phoneNumber = phoneNumber;
		this.birthdate = birthdate;
		this.creationTime = creationTime;
		this.title = title;
		this.password = password;
		this.email = email;
		
	}

	// Getter ve Setter'lar
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getTitle() {
        return title;
    }

   

	public void setTitle(String title) {
        this.title = title;
    }
	
	 public Boolean getIsDeleted() {
			return isDeleted;
		}

	public void setIsDeleted(Boolean isDeleted) {
			this.isDeleted = isDeleted;
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public Users orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.getCodeConstant().getName()));
	}
	
}
