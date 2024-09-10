package com.onbiron.onbironpdks.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onbiron.onbironpdks.entities.UserAnnualReportInfo;
import com.onbiron.onbironpdks.entities.Users;

public interface IUserAnnualReportInfoRepository extends JpaRepository<UserAnnualReportInfo, Long> {

	//public UserAnnualReportInfo findByUserId(String username);
	public List<UserAnnualReportInfo> findByUserId(Users userId);
	
	public List<UserAnnualReportInfo> findAllByIsDeleted(Boolean isDeleted);
	
	public List<UserAnnualReportInfo> findByIsDeletedAndIsLeave(Boolean isDeleted,Boolean isLeave);
	
	public Page<UserAnnualReportInfo> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);
	
	@Query("SELECT COUNT(u) FROM UserAnnualReportInfo u WHERE u.isLeave = true")
	public long countByIsDeletedAndIsLeave(Boolean isDeleted, Boolean isLeave);

	 @Query("SELECT u FROM UserAnnualReportInfo u WHERE " +
			    "LOWER(CAST(u.id AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(CAST(u.creationTime AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(CAST(u.startDate AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(CAST(u.stopDate AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " + 
			    "LOWER(CAST(u.annualReportTypeId.name AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " + 
			    "LOWER(CAST(u.userId.name AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(CAST(u.userId.surname AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			    "LOWER(CAST(u.userId.email AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) "
			    
			 )
	List<UserAnnualReportInfo> searchByTerm(@Param("searchTerm") String searchTerm);
}
