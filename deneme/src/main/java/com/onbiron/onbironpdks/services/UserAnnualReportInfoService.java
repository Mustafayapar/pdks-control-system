package com.onbiron.onbironpdks.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.onbiron.onbironpdks.entities.CodeConstant;
import com.onbiron.onbironpdks.entities.EmployeeEntryExit;
import com.onbiron.onbironpdks.entities.UserAnnualReportInfo;
import com.onbiron.onbironpdks.entities.UserRole;
import com.onbiron.onbironpdks.entities.Users;
import com.onbiron.onbironpdks.interfaceservices.IUserAnnualReportInfoService;
import com.onbiron.onbironpdks.repositories.ICodeConstantRepository;
import com.onbiron.onbironpdks.repositories.IUserAnnualReportInfoRepository;
import com.onbiron.onbironpdks.repositories.IUserRepository;
@Service
public class UserAnnualReportInfoService implements IUserAnnualReportInfoService{

	@Autowired
	private final IUserAnnualReportInfoRepository userAnnReportInfoRepository;
	private final IUserRepository userRepository;
	private final ICodeConstantRepository codeConstantRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserAnnualReportInfoService.class);

	
	public UserAnnualReportInfoService(IUserAnnualReportInfoRepository userAnnReportInfoRepository,  IUserRepository userRepository, ICodeConstantRepository codeConstantRepository) {
		this.userRepository = userRepository;
		this.userAnnReportInfoRepository = userAnnReportInfoRepository;
		this.codeConstantRepository=codeConstantRepository;

	}



	@Override
	public List<UserAnnualReportInfo> getAllUserAnnualReportS() {
		 try {
	            // Fetch all UserAnnualReportInfo records
	            List<UserAnnualReportInfo> userAnnualReportInfos = userAnnReportInfoRepository.findAllByIsDeleted(false);

	            if (userAnnualReportInfos.isEmpty()) {
	                // Log a warning if no records are found
	                logger.warn("No UserAnnualReportInfo records found.");
	            }

	            return userAnnualReportInfos;
	        } catch (Exception e) {
	            // Log the exception and handle it appropriately
	            logger.error("Error fetching UserAnnualReportInfo records", e);
	            throw new RuntimeException("Failed to fetch UserAnnualReportInfo records", e);
	        }
	}

	@Override
	public List<UserAnnualReportInfo> getUserAnnReportInfoByIdS(Long userId) {
		  
	 Users user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

            List<UserAnnualReportInfo> userAnnuelReportInfos = userAnnReportInfoRepository.findByUserId(user);
            return userAnnuelReportInfos;
     }
	

	@Override
	public UserAnnualReportInfo createUserAnnReportInfoS(@RequestBody Map<String, Object> payload) {
	    // User ID ve annualReportTypeId verilerini almak için alt nesnelere doğru şekilde erişmek gerekir
	    Map<String, Object> userMap = (Map<String, Object>) payload.get("user");
	    Long userId = userMap != null ? ((Number) userMap.get("id")).longValue() : null;

	    Map<String, Object> annualReportTypeMap = (Map<String, Object>) payload.get("annuelReportTypeId");
	    Long annualReportTypeId = annualReportTypeMap != null ? ((Number) annualReportTypeMap.get("id")).longValue() : null;

	    // Diğer verileri aynı şekilde alalım
	    String startDateStr = payload.get("startDate") != null ? (String) payload.get("startDate") : null;
	    LocalDateTime startDate = startDateStr != null ? parseISODateTime(startDateStr) : null;
	    
	    String stopDateStr = payload.get("stopDate") != null ? (String) payload.get("stopDate") : null;
	    LocalDateTime stopDate = stopDateStr != null ? parseISODateTime(stopDateStr) : null;
	    

	    // Eğer userId ya da annualReportTypeId eksikse hata fırlat
	    if (userId == null || annualReportTypeId == null) {
	        throw new IllegalArgumentException("User ID or annualReportTypeId is missing in the payload");
	    }

	    // Kullanıcı ve report type nesnelerini bulalım
	    Users user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    CodeConstant reportType = codeConstantRepository.findById(annualReportTypeId)
	            .orElseThrow(() -> new RuntimeException("Report type not found"));

	    // Yeni UserAnnualReportInfo nesnesi oluşturalım
	    UserAnnualReportInfo annualReportInfo = new UserAnnualReportInfo();
	    annualReportInfo.setUser(user);
	    annualReportInfo.setStartDate(startDate);
	    annualReportInfo.setStopDate(stopDate);
	    annualReportInfo.setAnnuelReportTypeId(reportType);

	    // Oluşturulan entity'yi kaydedelim
	    UserAnnualReportInfo createdAnnualReportInfo = userAnnReportInfoRepository.save(annualReportInfo);

	    return createdAnnualReportInfo;
	}

private LocalDateTime parseISODateTime(String dateTimeStr) {
    try {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_DATE_TIME);
    
    } 
catch (DateTimeParseException e) {
        
  
throw new IllegalArgumentException("Invalid date format: " + dateTimeStr, e);
    }
}


	@Override
	public UserAnnualReportInfo updateByUserAnnReportInforIdS(Long userId, UserAnnualReportInfo userAnnReportInfo) {
		if (userId == null || userAnnReportInfo == null) {
            logger.error("Cannot update UserAnnualReportInfo: userId or userAnnReportInfo is null.");
            throw new IllegalArgumentException("User ID and UserAnnualReportInfo cannot be null.");
        }

        // Retrieve the existing record
        UserAnnualReportInfo existingUserAnnReportInfo = userAnnReportInfoRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("UserAnnualReportInfo with ID " + userId + " not found"));

        // Update the existing record with new values
        existingUserAnnReportInfo.setStartDate(userAnnReportInfo.getStartDate());
        existingUserAnnReportInfo.setStopDate(userAnnReportInfo.getStopDate());
        //existingUserAnnReportInfo.setCreationTime(userAnnReportInfo.getCreationTime());
        existingUserAnnReportInfo.setAnnuelReportTypeId(userAnnReportInfo.getAnnuelReportTypeId());
        existingUserAnnReportInfo.setIsDeleted(userAnnReportInfo.getIsDeleted());

        try {
            UserAnnualReportInfo updatedUserAnnReportInfo = userAnnReportInfoRepository.save(existingUserAnnReportInfo);
            logger.info("UserAnnualReportInfo updated successfully: {}", updatedUserAnnReportInfo);
            return updatedUserAnnReportInfo;
        } catch (Exception e) {
            // Log the exception and throw a RuntimeException
            logger.error("Error updating UserAnnualReportInfo with ID {}: {}", userId, userAnnReportInfo, e);
            throw new RuntimeException("Failed to update UserAnnualReportInfo.", e);
        }
    
	}

	@Override
	public UserAnnualReportInfo deleteUserAnnReportInfoS(Long id) {

	        try {
	        	 // Retrieve the existing record
		        UserAnnualReportInfo existingUserAnnReportInfo = userAnnReportInfoRepository.findById(id)
		            .orElseThrow(() -> new RuntimeException("UserAnnualReportInfo with ID " + id + " not found"));

		        // Perform a logical deletion by setting isDeleted to true
		        existingUserAnnReportInfo.setIsDeleted(true);
	            UserAnnualReportInfo updatedUserAnnReportInfo = userAnnReportInfoRepository.save(existingUserAnnReportInfo);
	            logger.info("UserAnnualReportInfo logically deleted successfully: {}", updatedUserAnnReportInfo);
	            return updatedUserAnnReportInfo;
	        } catch (Exception e) {
	            // Log the exception and throw a RuntimeException
	            logger.error("Error deleting UserAnnualReportInfo with ID {}: {}", id, e);
	            throw new RuntimeException("Failed to delete UserAnnualReportInfo.", e);
	        }
	}



	@Override
	public Page<UserAnnualReportInfo> getAllUserAnnualReportPagenableS(int page, int rowsPerPage) {
		try {
			Pageable pageable = PageRequest.of(page, rowsPerPage);
	        Page<UserAnnualReportInfo> reportInfo =  userAnnReportInfoRepository.findAllByIsDeleted(false, pageable);
	        if (reportInfo.isEmpty()) {
	            logger.warn("No users found in the database.");
	            throw new RuntimeException("No users found.");
	        }
	        return reportInfo;
	    } catch (Exception e) {
	        logger.error("An error occurred while fetching all users: {}", e.getMessage());
	        throw new RuntimeException("An error occurred while fetching users.", e);
	        }
	   
	}



	@Override
	public List<UserAnnualReportInfo> search(String searchTerm) {
		
		String cleanedSearchTerm = searchTerm.trim();
        return userAnnReportInfoRepository.searchByTerm( cleanedSearchTerm);
	}



	@Override
	public long calculateBusinessDays(UserAnnualReportInfo newUserAnnReportInfo) {
		
		LocalDateTime startDate= newUserAnnReportInfo.getStartDate();
		LocalDateTime endDate= newUserAnnReportInfo.getStopDate();
		
		long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        long weekends = (totalDays / 7) * 2; // Her hafta 2 hafta sonu günüdür.
        
        if (startDate.getDayOfWeek().getValue() > endDate.getDayOfWeek().getValue()) {
            weekends -= 2;
        } else {
            if (startDate.getDayOfWeek().getValue() == 6) weekends--;
            if (endDate.getDayOfWeek().getValue() == 7) weekends--;
        }
        return totalDays - weekends;
	}



	@Override
	public List<UserAnnualReportInfo> getIsLeaveUserAnnualReportS() {
		 try {
	            List<UserAnnualReportInfo> reports = userAnnReportInfoRepository.findByIsDeletedAndIsLeave(false,false);
	            return reports != null ? reports : Collections.emptyList();
	        } catch (DataAccessException e) {
	            
	            // Return an empty list or handle as needed
	            return Collections.emptyList();
	        } catch (Exception e) {
 	            // Return an empty list or handle as needed
	            return Collections.emptyList();
	        }
	}



	@Override
	public long countUserAnnualReportInfoIsLeaveS() {
		// TODO Auto-generated method stub
		return  userAnnReportInfoRepository.countByIsDeletedAndIsLeave(false,false);
	}



	
}
