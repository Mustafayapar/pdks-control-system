package com.onbiron.onbironpdks.entities;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_annuel_report_info", schema = "public")
@CrossOrigin("/*")
public class UserAnnualReportInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Users userId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "stop_date", nullable = false)
    private LocalDateTime stopDate;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime=LocalDateTime.now();

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted=false;
    
    @Column(name = "is_leave", nullable = false)
    private Boolean isLeave=false;
    
    @ManyToOne
    @JoinColumn(name = "annuel_report_type_id", referencedColumnName = "id", nullable=false)
    private CodeConstant annualReportTypeId;
    
  public  UserAnnualReportInfo() {
    	
    }
    
  

    public UserAnnualReportInfo(Long id, Users userId, LocalDateTime startDate, LocalDateTime stopDate,
			LocalDateTime creationTime, Boolean isDeleted, Boolean isLeave, CodeConstant annualReportTypeId) {
		super();
		this.id = id;
		this.userId = userId;
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.creationTime = creationTime;
		this.isDeleted = isDeleted;
		this.isLeave = isLeave;
		this.annualReportTypeId = annualReportTypeId;
	}

	// Getters and Setters

	public Boolean getIsLeave() {
		return isLeave;
	}



	public void setIsLeave(Boolean isLeave) {
		this.isLeave = isLeave;
	}



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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getStopDate() {
        return stopDate;
    }

    public void setStopDate(LocalDateTime stopDate) {
        this.stopDate = stopDate;
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

    public CodeConstant getAnnuelReportTypeId() {
        return annualReportTypeId;
    }

    public void setAnnuelReportTypeId(CodeConstant annualReportTypeId) {
        this.annualReportTypeId = annualReportTypeId;
    }
}

