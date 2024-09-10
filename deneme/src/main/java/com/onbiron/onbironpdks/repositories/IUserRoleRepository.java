package com.onbiron.onbironpdks.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onbiron.onbironpdks.entities.CodeConstant;
import com.onbiron.onbironpdks.entities.EmployeeEntryExit;
import com.onbiron.onbironpdks.entities.UserRole;
import com.onbiron.onbironpdks.entities.Users;

public interface IUserRoleRepository extends JpaRepository<UserRole, Long> {
    
	public abstract UserRole findByUserIdAndIsDeleted(Users user, Boolean isDeleted);

	//public List<UserRole> findByRoleId(Long roleId);
	
    List<UserRole> findByRoleIdAndIsDeleted(CodeConstant roleId, Boolean isDeleted);
	
	public Page<UserRole> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);
	
	public List<UserRole> findAllByIsDeleted(Boolean isDeleted);

	   @Query("SELECT u FROM UserRole u WHERE " +
			    "LOWER(CAST(u.id AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(u.userId.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(u.userId.surname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			     
			    "LOWER(u.roleId.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(CAST(u.creationTime AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"
			    
			   )
			List<UserRole> searchByTerm( @Param("searchTerm") String searchTerm);
		
	
}
