package com.onbiron.onbironpdks.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onbiron.onbironpdks.entities.EmployeeEntryExit;
import com.onbiron.onbironpdks.entities.Users;

public interface IEmployeeEntryExitRepository extends JpaRepository<EmployeeEntryExit, Long>{

	public List<EmployeeEntryExit> findAllByUserId(Users userId);
	public List<EmployeeEntryExit> findAllByIsDeleted(Boolean isDeleted);
	
	public Page<EmployeeEntryExit> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);

	 @Query("SELECT u FROM EmployeeEntryExit u WHERE " +
			    "LOWER(CAST(u.id AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(CAST(u.creationTime AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(CAST(u.entryExitDatetime AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(CAST(u.entryExitTypeId.name AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " + 
			    "LOWER(CAST(u.userId.name AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(CAST(u.userId.surname AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(CAST(u.userId.email AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(CAST(u.userId.phoneNumber AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ")
			List<EmployeeEntryExit> searchByTerm(@Param("searchTerm") String searchTerm);
}
