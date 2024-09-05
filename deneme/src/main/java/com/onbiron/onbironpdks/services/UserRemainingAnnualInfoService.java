package com.onbiron.onbironpdks.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.onbiron.onbironpdks.entities.UserRemainingAnnualInfo;
import com.onbiron.onbironpdks.entities.UserRole;
import com.onbiron.onbironpdks.entities.Users;
import com.onbiron.onbironpdks.interfaceservices.IUserRemainingAnnualInfoService;
import com.onbiron.onbironpdks.repositories.IUserRemainingAnnualInfoRepository;
import com.onbiron.onbironpdks.repositories.IUserRepository;

@Service
public class UserRemainingAnnualInfoService implements IUserRemainingAnnualInfoService {

	private final IUserRemainingAnnualInfoRepository userRemainingAnnInfoRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserRemainingAnnualInfoService.class);
    private final IUserRepository userRepository;

	
	public UserRemainingAnnualInfoService(IUserRemainingAnnualInfoRepository userRemainingAnnInfoRepository,  IUserRepository userRepository) {
		this.userRemainingAnnInfoRepository = userRemainingAnnInfoRepository;
		this.userRepository = userRepository;
	}

	@Override
	public UserRemainingAnnualInfo getRemainingInfoByUserIdS(Long userId) {
		
		 


		 try {
		        // Retrieve the UserRemainingAnnualInfo records by userID
			 Users user = userRepository.findById(userId)
		                .orElseThrow(() -> new RuntimeException("User not found"));

			  UserRemainingAnnualInfo userRemainingAnnuelInfos = userRemainingAnnInfoRepository.findByUserId(user);

		        // Check if any records were retrieved and log accordingly
		        if (userRemainingAnnuelInfos!=null) {
		            
		            return userRemainingAnnuelInfos;
		        } else {
		            logger.warn("No UserRemainingAnnualInfo records found for User ID {}", userId);
		            return null; // Return an empty list if no records are found
		        }
		    } catch (Exception e) {
		        logger.error("Error retrieving UserRemainingAnnualInfo records for User ID {}: {}", userId, e.getMessage());
		        throw new RuntimeException("Failed to retrieve UserRemainingAnnualInfo records.", e);
		    }
	}

	@Override
	public List<UserRemainingAnnualInfo> getRemainingInfoAllS() {
		  try {
		        // Retrieve all UserRemainingAnnualInfo records
		        List<UserRemainingAnnualInfo> remainingInfos = userRemainingAnnInfoRepository.findAllByIsDeleted(false);

		        // Log the number of records retrieved
		        if (!remainingInfos.isEmpty()) {
		            logger.info("Retrieved {} UserRemainingAnnualInfo records", remainingInfos.size());
		        } else {
		            logger.warn("No UserRemainingAnnualInfo records found");
		        }

		        return remainingInfos;
		    } catch (Exception e) {
		        logger.error("Error retrieving UserRemainingAnnualInfo records: {}", e.getMessage());
		        throw new RuntimeException("Failed to retrieve UserRemainingAnnualInfo records.", e);
		    }
	}

	@Override
	public UserRemainingAnnualInfo createRemainingInfoS(UserRemainingAnnualInfo userRemainingInfo) {
		 try {
		        // Save the UserRemainingAnnualInfo record to the database
		        UserRemainingAnnualInfo savedInfo = userRemainingAnnInfoRepository.save(userRemainingInfo);

		        // Log the creation of the record
		        logger.info("Created UserRemainingAnnualInfo with ID: {}", savedInfo.getId());

		        return savedInfo;
		    } catch (Exception e) {
		        // Log the error
		        logger.error("Error creating UserRemainingAnnualInfo: {}", e.getMessage());
		        throw new RuntimeException("Failed to create UserRemainingAnnualInfo.", e);
		    }
	}

	@Override
	public UserRemainingAnnualInfo deleteRemainingInfoS(Long userRemainingInfoId) {
		 try {
		        // Retrieve the UserRemainingAnnualInfo record by ID
		        UserRemainingAnnualInfo userRemainingAnnualInfo = userRemainingAnnInfoRepository.findById(userRemainingInfoId)
		            .orElseThrow(() -> new RuntimeException("UserRemainingAnnualInfo with ID " + userRemainingInfoId + " not found"));

		        // Set the record as deleted (soft delete)
		        userRemainingAnnualInfo.setIsDeleted(true);

		        // Save the updated record back to the database
		        userRemainingAnnInfoRepository.save(userRemainingAnnualInfo);

		        // Log the soft deletion
		        logger.info("Soft deleted UserRemainingAnnualInfo with ID: {}", userRemainingInfoId);

		        return userRemainingAnnualInfo;
		    } catch (Exception e) {
		        // Log the error
		        logger.error("Error performing soft delete for UserRemainingAnnualInfo with ID {}: {}", userRemainingInfoId, e.getMessage());
		        throw new RuntimeException("Failed to perform soft delete for UserRemainingAnnualInfo.", e);
		    }
		
	}

	@Override
	public UserRemainingAnnualInfo updateRemainingInfoS(Long userRemainingInfoId,
			UserRemainingAnnualInfo updatedInfo) {
		try {
	        // Retrieve the existing record
	        UserRemainingAnnualInfo existingInfo = userRemainingAnnInfoRepository.findById(userRemainingInfoId)
	            .orElseThrow(() -> new RuntimeException("UserRemainingAnnualInfo with ID " + userRemainingInfoId + " not found"));

	        // Check if the record is marked as deleted
	        if (existingInfo.getIsDeleted()) {
	            throw new RuntimeException("Cannot update UserRemainingAnnualInfo with ID " + userRemainingInfoId + " because it is marked as deleted.");
	        }

	        // Update fields with the values from updatedInfo
	        existingInfo.setRemainingAnnualLeave(updatedInfo.getRemainingAnnualLeave());
	        existingInfo.setRemainingExcusedLeave(updatedInfo.getRemainingExcusedLeave());
	       

	        // Save the updated record
	        UserRemainingAnnualInfo updated = userRemainingAnnInfoRepository.save(existingInfo);

	        // Log the update
	        logger.info("Updated UserRemainingAnnualInfo with ID: {}", userRemainingInfoId);

	        return updated;
	    } catch (Exception e) {
	        // Log the error
	        logger.error("Error updating UserRemainingAnnualInfo with ID {}: {}", userRemainingInfoId, e.getMessage());
	        throw new RuntimeException("Failed to update UserRemainingAnnualInfo.", e);
	    }	}
	
	@Override
	public UserRemainingAnnualInfo getUserRemainAnnInfoByUserIdS(Users user) {
		
	
		 try {
		        // Validate the user input
		        if (user == null || user.getId() == null) {
		            logger.error("Invalid User: User object or User ID is null");
		            throw new IllegalArgumentException("User object or User ID cannot be null");
		        }
		        
		        
		        // Retrieve the remaining annual info for the user
		        UserRemainingAnnualInfo userRemainingAnnualInfos = userRemainingAnnInfoRepository.findByUserId(user);
		        
		        // Check if the result is empty
		        if (userRemainingAnnualInfos ==null) {
		            logger.warn("No remaining annual info found for User ID {}", user.getId());
		        } else {
		            logger.info("Found {} remaining annual info records for User ID {}", userRemainingAnnualInfos, user.getId());
		        }
		        
		        return userRemainingAnnualInfos;
		    } catch (IllegalArgumentException e) {
		        logger.error("Error in user input: {}", e.getMessage());
		        throw e;
		    } catch (NoSuchElementException e) {
		        logger.error("Error fetching user or remaining annual info: {}", e.getMessage());
		        throw e;
		    } catch (Exception e) {
		        logger.error("Unexpected error fetching remaining annual info for User ID {}: {}", user.getId(), e.getMessage());
		        throw new RuntimeException("Failed to retrieve remaining annual info", e);
		    }
		 
	}
	
	@Override
	public Page<UserRemainingAnnualInfo> getAllRemainingAnnualInfoPageS(int page, int rowsPerPage) {
		
		try {
			Pageable pageable = PageRequest.of(page, rowsPerPage);
	        Page<UserRemainingAnnualInfo> remainingAnnualInfo =  userRemainingAnnInfoRepository.findAllByIsDeleted(false, pageable);
	        if (remainingAnnualInfo.isEmpty()) {
	            logger.warn("No users found in the database.");
	            throw new RuntimeException("No users found.");
	        }
	        return remainingAnnualInfo;
	    } catch (Exception e) {
	        logger.error("An error occurred while fetching all users: {}", e.getMessage());
	        throw new RuntimeException("An error occurred while fetching users.", e);
	        }
	   
	}



	@Override
	public List<UserRemainingAnnualInfo> search(String searchTerm) {
		String cleanedSearchTerm = searchTerm.trim();
        return userRemainingAnnInfoRepository.searchByTerm( cleanedSearchTerm);	
        
	}

	
}
