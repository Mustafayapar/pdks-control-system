package com.onbiron.onbironpdks.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.onbiron.onbironpdks.enums.ParentIdType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "code_constant", schema = "public")
@CrossOrigin("/*")
public class CodeConstant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Use EnumType.STRING to store the enum's name in the database (optional)
     @Column(name = "parent_id")
    private long parentId;

	@Column(name = "name")
    private String name;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime=LocalDateTime.now();

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted=false;
    
    @OneToMany(mappedBy = "roleId", cascade = CascadeType.ALL)
    private List<UserRole> userRole;
    
    @OneToMany(mappedBy = "annualReportTypeId", cascade = CascadeType.ALL)
    private List<UserAnnualReportInfo> userAnnualReportInfo;
    
    @OneToMany(mappedBy = "entryExitTypeId", cascade = CascadeType.ALL)
    private List<EmployeeEntryExit> employeeEntryExit;
    
    public CodeConstant() {
    	
    }
    
    public CodeConstant(Long id, ParentIdType parentIdType, String explanation, LocalDateTime creationTime,
			Boolean isDeleted) {
		this.id = id;
		this.parentId = parentIdType.getParentId();
	    this.name = parentIdType.getName();
		this.explanation = explanation;
		this.creationTime = creationTime;
		this.isDeleted = isDeleted;
	}
  

    public ParentIdType getParentIdType() {
        return ParentIdType.fromParentId(this.parentId);
    }

    public void setParentIdType(ParentIdType parentIdType) {
        this.parentId = parentIdType.getParentId();
        this.name = parentIdType.getName();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

	

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
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
