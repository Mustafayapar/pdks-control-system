package com.onbiron.onbironpdks.entities;


import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_role", schema = "public")
@CrossOrigin("/*")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Users userId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private CodeConstant roleId;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime=LocalDateTime.now();;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted=false;

    // Getters and Setters
    
    public UserRole() {
	
    }

    
    public UserRole(Long id, Users userId, CodeConstant roleId, LocalDateTime creationTime, Boolean isDeleted) {
		super();
		this.id = id;
		this.userId = userId;
		this.roleId = roleId;
		this.creationTime = creationTime;
		this.isDeleted = isDeleted;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public CodeConstant getCodeConstant() {
        return roleId;
    }

    public void setCodeConstant(CodeConstant roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}

