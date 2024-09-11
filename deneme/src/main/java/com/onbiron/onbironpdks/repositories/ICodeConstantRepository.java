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
import com.onbiron.onbironpdks.enums.ParentIdType;

public interface ICodeConstantRepository extends JpaRepository<CodeConstant, Long> {
	@Query("SELECT c FROM CodeConstant c WHERE c.parentId = :parentId AND c.isDeleted = :isDeleted")
    Optional<CodeConstant> getByParentIdAndIsDeleted(@Param("parentId") Long parentId, @Param("isDeleted") Boolean isDeleted);
	 

	public List<CodeConstant> findByParentIdAndIsDeleted(long parentId, Boolean isDeleted);
	
	public List<CodeConstant> findAllByIsDeleted(Boolean isDeleted);
	
	public Page<CodeConstant> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);
	
	 @Query("SELECT u FROM CodeConstant u WHERE " +
			    "LOWER(CAST(u.id AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			   
			    "LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(u.explanation) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			  
			 	"LOWER(CAST(u.creationTime AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
			public List<CodeConstant> searchByTerm( @Param("searchTerm") String searchTerm);
	 
	 @Query("SELECT c FROM CodeConstant c WHERE c.parentId IN :parentIds AND c.isDeleted = :isDeleted")
	    List<CodeConstant> findByParentIdsAndIsDeleted(@Param("parentIds") List<Long> parentIds,boolean isDeleted);
	 
}
