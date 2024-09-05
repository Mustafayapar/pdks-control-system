package com.onbiron.onbironpdks.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onbiron.onbironpdks.entities.EmployeeEntryExit;
import com.onbiron.onbironpdks.entities.Users;

public interface IUserRepository extends JpaRepository<Users, Long>{
	
	public Optional<Users> findByUsername(String username);
	

	public Page<Users> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);
	
	public List<Users> findAllByIsDeleted(Boolean isDeleted);


	    @Query("SELECT u FROM Users u WHERE " +
	    "LOWER(CAST(u.id AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	    "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	    "LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	    "LOWER(u.password) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	    "LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	    "LOWER(u.registrationNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	    "LOWER(u.surname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	    "LOWER(u.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	    "LOWER(u.username) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	public List<Users> searchByTerm( @Param("searchTerm") String searchTerm);
}
