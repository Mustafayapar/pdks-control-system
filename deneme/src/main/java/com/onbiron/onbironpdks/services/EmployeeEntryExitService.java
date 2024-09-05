package com.onbiron.onbironpdks.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.onbiron.onbironpdks.entities.EmployeeEntryExit;
import com.onbiron.onbironpdks.entities.Users;
import com.onbiron.onbironpdks.interfaceservices.IEmployeeEntryExitService;
import com.onbiron.onbironpdks.repositories.IEmployeeEntryExitRepository;
import com.onbiron.onbironpdks.repositories.IUserRepository;

@Service
public class EmployeeEntryExitService implements IEmployeeEntryExitService{

    private static final Logger logger = LoggerFactory.getLogger(EmployeeEntryExitService.class);

	private final IEmployeeEntryExitRepository employeeEntryExitRepository;
	private final IUserRepository userRepository;
	
	public EmployeeEntryExitService(IEmployeeEntryExitRepository employeeEntryExitRepository, IUserRepository userRepository) {
		this.employeeEntryExitRepository = employeeEntryExitRepository;
		this.userRepository= userRepository;
		
	}

	@Override
	public List<EmployeeEntryExit> getEmployeeEntryExitByUserIdS(Long userId) {
		Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

                List<EmployeeEntryExit> employeeEntryExits = employeeEntryExitRepository.findAllByUserId(user);
                    return employeeEntryExits;
	}

	@Override
	public EmployeeEntryExit createEmployeeEntryExitS(EmployeeEntryExit newEmployeeEntryExit) {
		 if (newEmployeeEntryExit == null) {
	            logger.error("Cannot create EmployeeEntryExit: provided object is null.");
	            throw new IllegalArgumentException("EmployeeEntryExit cannot be null.");
	        }

	        try {
	            // Save to repository
	            EmployeeEntryExit savedEmployeeEntryExit = employeeEntryExitRepository.save(newEmployeeEntryExit);

	            logger.info("EmployeeEntryExit created successfully with ID: {}", savedEmployeeEntryExit.getId());
	            return savedEmployeeEntryExit;
	        } catch (Exception e) {
	            logger.error("Error creating EmployeeEntryExit: {}", e.getMessage());
	            throw new RuntimeException("Failed to create EmployeeEntryExit.", e);
	        }
	}

	@Override
	public List<EmployeeEntryExit> getEmployeeEntryExitAllS() {
		 try {
		        // Fetch all EmployeeEntryExit records
		        List<EmployeeEntryExit> employeeEntryExits = employeeEntryExitRepository.findAllByIsDeleted(false);

		        if (employeeEntryExits.isEmpty()) {
		            logger.warn("No EmployeeEntryExit records found.");
		        } else {
		            logger.info("Retrieved {} EmployeeEntryExit records.", employeeEntryExits.size());
		        }

		        return employeeEntryExits;
		    } catch (Exception e) {
		        logger.error("Error retrieving EmployeeEntryExit records: {}", e.getMessage());
		        throw new RuntimeException("Failed to retrieve EmployeeEntryExit records.", e);
		    }
	}

	@Override
	public EmployeeEntryExit deleteEmployeeEntryExitS(Long employeeEntryExitId) {
		 try {
		        // Check if the EmployeeEntryExit record exists by its ID
		        Optional<EmployeeEntryExit> existingEntry = employeeEntryExitRepository.findById(employeeEntryExitId);

		        if (existingEntry.isPresent()) {
		            // Get the existing entry
		            EmployeeEntryExit entryToDelete = existingEntry.get();

		            // Mark the record as deleted by setting the isDeleted flag to true
		            entryToDelete.setIsDeleted(true);

		            // Save the updated record back to the database
		            EmployeeEntryExit deletedEntry = employeeEntryExitRepository.save(entryToDelete);
		            logger.info("EmployeeEntryExit with ID {} marked as deleted.", deletedEntry.getId());

		            return deletedEntry;
		        } else {
		            logger.warn("EmployeeEntryExit with ID {} not found.", employeeEntryExitId);
		            throw new RuntimeException("EmployeeEntryExit record not found.");
		        }
		    } catch (Exception e) {
		        logger.error("Error deleting EmployeeEntryExit record with ID {}: {}", employeeEntryExitId, e.getMessage());
		        throw new RuntimeException("Failed to delete EmployeeEntryExit record.", e);
		    }
	}

	@Override
	public Page<EmployeeEntryExit> getAllEnrtyExitPageS(int page, int rowsPerPage) {
		
		try {
			Pageable pageable = PageRequest.of(page, rowsPerPage);
	        Page<EmployeeEntryExit> entryExit =  employeeEntryExitRepository.findAllByIsDeleted(false, pageable);
	        if (entryExit.isEmpty()) {
	            logger.warn("No users found in the database.");
	            throw new RuntimeException("No users found.");
	        }
	        return entryExit;
	    } catch (Exception e) {
	        logger.error("An error occurred while fetching all users: {}", e.getMessage());
	        throw new RuntimeException("An error occurred while fetching users.", e);
	        }
	   
	}

	@Override
	public List<EmployeeEntryExit> search(String searchTerm) {
		
		String cleanedSearchTerm = searchTerm.trim();
        return employeeEntryExitRepository.searchByTerm(cleanedSearchTerm);
	}

	@Override
	public EmployeeEntryExit updateByEntryExitIdS(Long id, EmployeeEntryExit neEmployeeEntryExit) {
		EmployeeEntryExit existingEntryExit = employeeEntryExitRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("EmployeeEntryExit not found with id: " + id));

	    // Update the fields of the existing record with new values
	    existingEntryExit.setId(neEmployeeEntryExit.getId());
	    existingEntryExit.setUserId(neEmployeeEntryExit.getUserId());
	    existingEntryExit.setEntryExitDatetime(neEmployeeEntryExit.getEntryExitDatetime());
	    existingEntryExit.setCreationTime(neEmployeeEntryExit.getCreationTime());
	    existingEntryExit.setEntryExitType(neEmployeeEntryExit.getEntryExitType()); // Replace with actual field names

	    // Save the updated record
	    return employeeEntryExitRepository.save(existingEntryExit);
	
	}



	
}
