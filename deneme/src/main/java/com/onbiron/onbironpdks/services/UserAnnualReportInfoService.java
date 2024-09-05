package com.onbiron.onbironpdks.services;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.onbiron.onbironpdks.entities.EmployeeEntryExit;
import com.onbiron.onbironpdks.entities.UserAnnualReportInfo;
import com.onbiron.onbironpdks.entities.Users;
import com.onbiron.onbironpdks.interfaceservices.IUserAnnualReportInfoService;
import com.onbiron.onbironpdks.repositories.IUserAnnualReportInfoRepository;
import com.onbiron.onbironpdks.repositories.IUserRepository;
@Service
public class UserAnnualReportInfoService implements IUserAnnualReportInfoService{

	@Autowired
	private final IUserAnnualReportInfoRepository userAnnReportInfoRepository;
	private final IUserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserAnnualReportInfoService.class);

	
	public UserAnnualReportInfoService(IUserAnnualReportInfoRepository userAnnReportInfoRepository,  IUserRepository userRepository) {
		this.userRepository = userRepository;
		this.userAnnReportInfoRepository = userAnnReportInfoRepository;

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
	public UserAnnualReportInfo createUserAnnReportInfoS(UserAnnualReportInfo newUserAnnReportInfo) {
		
		 // Validate input
        if (newUserAnnReportInfo == null) {
            logger.error("Cannot create UserAnnualReportInfo: input is null.");
            throw new IllegalArgumentException("UserAnnualReportInfo cannot be null.");
        }

        // Set default values if necessary
        if (newUserAnnReportInfo.getCreationTime() == null) {
            newUserAnnReportInfo.setCreationTime(LocalDateTime.now());
        }

        // Save the new UserAnnualReportInfo record
        try {
            UserAnnualReportInfo savedUserAnnReportInfo = userAnnReportInfoRepository.save(newUserAnnReportInfo);
            logger.info("UserAnnualReportInfo created successfully: {}", savedUserAnnReportInfo);
            return savedUserAnnReportInfo;
        } catch (Exception e) {
            // Log the exception and throw a RuntimeException
            logger.error("Error creating UserAnnualReportInfo: {}", newUserAnnReportInfo, e);
            throw new RuntimeException("Failed to create UserAnnualReportInfo.", e);
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



	
}
