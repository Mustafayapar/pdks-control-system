package com.onbiron.onbironpdks.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_remaining_annuel_info", schema = "public")
@CrossOrigin("/*")
public class UserRemainingAnnualInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Users userId;

   
    @Column(name = "remaining_annual_leave")
    private BigDecimal remainingAnnualLeave;

    @Column(name = "remaining_excused_leave")
    private BigDecimal remainingExcusedLeave;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime =LocalDateTime.now();;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted=false;
    
    public UserRemainingAnnualInfo() {
    	
    }
    public UserRemainingAnnualInfo(Long id, Users userId, BigDecimal remainingAnnualLeave,
			BigDecimal remainingExcusedLeave, LocalDateTime creationTime, Boolean isDeleted) {
		super();
		this.id = id;
		this.userId = userId;
		this.remainingAnnualLeave = remainingAnnualLeave;
		this.remainingExcusedLeave = remainingExcusedLeave;
		this.creationTime = creationTime;
		this.isDeleted = isDeleted;  
	}

	// Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return userId;
    }

    public void setUser(Users userId) {
        this.userId = userId;
    }
    public BigDecimal getRemainingAnnualLeave() {
        return remainingAnnualLeave;
    }

    public void setRemainingAnnualLeave(BigDecimal remainingAnnualLeave) {
        this.remainingAnnualLeave = remainingAnnualLeave;
    }

    public BigDecimal getRemainingExcusedLeave() {
        return remainingExcusedLeave;
    }

    public void setRemainingExcusedLeave(BigDecimal remainingExcusedLeave) {
        this.remainingExcusedLeave = remainingExcusedLeave;
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
