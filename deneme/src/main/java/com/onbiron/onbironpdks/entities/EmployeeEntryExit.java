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
import jakarta.persistence.Table;

@Entity
@Table(name = "employee_entry_exit", schema = "public")
@CrossOrigin("/*")
public class EmployeeEntryExit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users userId;

    @Column(name = "entry_exit_datetime", nullable = false)
    private LocalDateTime entryExitDatetime;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime =LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "entry_exit_type_id", referencedColumnName = "id", nullable=false)
    private CodeConstant entryExitTypeId;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted=false;
    
    public  EmployeeEntryExit() {
    	
    }
    
    // Getters and Setters

    public EmployeeEntryExit(Long id, Users userId, LocalDateTime entryExitDatetime, LocalDateTime creationTime,
			CodeConstant entryExitTypeId, Boolean isDeleted) {
		super();
		this.id = id;
		this.userId = userId;
		this.entryExitDatetime = entryExitDatetime;
		this.creationTime = creationTime;
		this.entryExitTypeId = entryExitTypeId;
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


    public LocalDateTime getEntryExitDatetime() {
        return entryExitDatetime;
    }

    public void setEntryExitDatetime(LocalDateTime entryExitDatetime) {
        this.entryExitDatetime = entryExitDatetime;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public CodeConstant getEntryExitType() {
        return entryExitTypeId;
    }

    public void setEntryExitType(CodeConstant entryExitTypeId) {
        this.entryExitTypeId = entryExitTypeId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}

