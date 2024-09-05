package com.onbiron.onbironpdks.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onbiron.onbironpdks.entities.CodeConstant;
import com.onbiron.onbironpdks.entities.Users;

public interface ICodeConstantRepository extends JpaRepository<CodeConstant, Long> {
	
	public Optional<CodeConstant> getByParentId(Long parentId);
	 

	public List<CodeConstant> findByParentId(Long parentId);
	
	public List<CodeConstant> findAllByIsDeleted(Boolean isDeleted);
	
	public Page<CodeConstant> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);
	
	 @Query("SELECT u FROM CodeConstant u WHERE " +
			    "LOWER(CAST(u.id AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			   
			    "LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(u.explanation) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			  
			 	"LOWER(CAST(u.creationTime AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
			public List<CodeConstant> searchByTerm( @Param("searchTerm") String searchTerm);
	 
}
