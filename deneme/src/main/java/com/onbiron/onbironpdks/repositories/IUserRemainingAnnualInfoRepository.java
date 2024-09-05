package com.onbiron.onbironpdks.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onbiron.onbironpdks.entities.UserRemainingAnnualInfo;
import com.onbiron.onbironpdks.entities.Users;
@Repository
public interface IUserRemainingAnnualInfoRepository extends JpaRepository<UserRemainingAnnualInfo, Long>{
	
   public UserRemainingAnnualInfo findByUserId(Users userId);
   
	public List<UserRemainingAnnualInfo> findAllByIsDeleted(Boolean isDeleted);
	
	public Page<UserRemainingAnnualInfo> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);

	 @Query("SELECT u FROM UserRemainingAnnualInfo u WHERE " +
			    "LOWER(CAST(u.id AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(CAST(u.creationTime AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
 			    "LOWER(CAST(u.remainingAnnualLeave  AS string )) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " + 
			    "LOWER(CAST(u.remainingExcusedLeave  AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(CAST(u.userId.surname AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "+
			    "LOWER(CAST(u.userId.name AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ")
			
	 public List<UserRemainingAnnualInfo> searchByTerm(@Param("searchTerm") String searchTerm);
}
