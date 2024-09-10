package com.onbiron.onbironpdks.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.onbiron.onbironpdks.entities.CodeConstant;
import com.onbiron.onbironpdks.entities.UserRole;
import com.onbiron.onbironpdks.entities.Users;
import com.onbiron.onbironpdks.enums.ParentIdType;
import com.onbiron.onbironpdks.interfaceservices.IUserRoleService;
import com.onbiron.onbironpdks.repositories.ICodeConstantRepository;
import com.onbiron.onbironpdks.repositories.IUserRepository;
import com.onbiron.onbironpdks.repositories.IUserRoleRepository;

@Service
public class UserRoleService implements IUserRoleService {
	
	private final IUserRoleRepository userRoleRepository;
	private final IUserRepository userRepository;
	private final ICodeConstantRepository codeConstantRepository;
	private static final Logger logger = LoggerFactory.getLogger(UserRoleService.class);

	public UserRoleService(IUserRoleRepository userRoleRepository, IUserRepository userRepository,
			ICodeConstantRepository codeConstantRepository) {
		super();
		this.userRoleRepository = userRoleRepository;
		this.userRepository = userRepository;
		this.codeConstantRepository = codeConstantRepository;
	}



	@Override
	public List<UserRole> getUsersByRoleIdS(long parentId) {
		CodeConstant codeConstant = codeConstantRepository.getByParentIdAndIsDeleted(parentId,false)
                .orElseThrow(() -> new RuntimeException("Role not found"));

            List<UserRole> userRole = userRoleRepository.findByRoleIdAndIsDeleted(codeConstant,false);
            return userRole;
	
	}


	@Override
	public List<UserRole> getUserRoleAllS() {
		
		  List<UserRole> userRoles = userRoleRepository.findAllByIsDeleted(false);
		    if (userRoles.isEmpty()) {
		        logger.warn("No UserRole entries found");
		        throw new RuntimeException("No UserRole entries found");
		    }
		    return userRoles;
	}


	public UserRole createUserRoleS(Map<String, Object> payload) {
	    // Retrieve userId and roleId from the payload
	    Long userId = payload.get("userId") != null ? ((Number) payload.get("userId")).longValue() : null;
	    Long roleId = payload.get("roleId") != null ? ((Number) payload.get("roleId")).longValue() : null;

	    if (userId == null || roleId == null) {
	        throw new IllegalArgumentException("User ID or Role ID is missing in the payload");
	    }

	    // Find the related entities using their IDs
	    Users user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));
	    
	    CodeConstant role = codeConstantRepository.findById(roleId)
	            .orElseThrow(() -> new RuntimeException("Role not found"));

	    // Create and populate the UserRole entity
	    UserRole userRole = new UserRole();
	    userRole.setUserId(user);
	    userRole.setCodeConstant(role);  // Assume the role field name is "role"
	    userRole.setCreationTime(LocalDateTime.now());
	    userRole.setIsDeleted(false);

	        // Save the entity
	        UserRole createdUserRole = userRoleRepository.save(userRole);
	        return createdUserRole;
	 }

	@Override
    public UserRole updateById(Long id, Map<String, Object> payload){
		 // Retrieve the existing record by ID
		// Retrieve the existing UserRole by ID
	    UserRole existingUserRole = userRoleRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("UserRole not found with id: " + id));

	    // Update the fields of the existingUserRole based on the payload
	    if (payload.containsKey("userId")) {
	        Long userId = ((Number) payload.get("userId")).longValue();
	        Users user = userRepository.findById(userId)
		            .orElseThrow(() -> new RuntimeException("User not found"));
	        existingUserRole.setUserId(user);
	    }

	    if (payload.containsKey("roleId")) {
	        Long roleId = ((Number) payload.get("roleId")).longValue();

		    CodeConstant role = codeConstantRepository.findById(roleId)
		            .orElseThrow(() -> new RuntimeException("Role not found"));
	        existingUserRole.setCodeConstant(role); // Assuming roleId maps to codeConstant
	    }

	    

	    // Add more fields as necessary, depending on what can be updated in your entity

	    // Save the updated UserRole entity
	    return userRoleRepository.save(existingUserRole);
         
    }


	@Override
	public UserRole deleteUserRoleS(Long userRoleId) {
		
		UserRole userRole = userRoleRepository.findById(userRoleId)
	            .orElseThrow(() -> new RuntimeException("UserRole not found with ID: " + userRoleId));
	    
	    if (userRole.getIsDeleted()) {
	        logger.warn("UserRole is already deleted for ID: {}", userRoleId);
	        throw new RuntimeException("UserRole is already deleted");
	    }

	    userRole.setIsDeleted(true);
	    return userRoleRepository.save(userRole);
	}



	


	@Override
	public UserRole getByUserId(Long userId) {
		Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

                UserRole userRoles = userRoleRepository.findByUserIdAndIsDeleted(user,false);
                return userRoles;
	}



	@Override
	public Page<UserRole> getAllUserPageS(int page, int rowsPerPage) {
		
		try {
			Pageable pageable = PageRequest.of(page, rowsPerPage);
	        Page<UserRole> userRole =  userRoleRepository.findAllByIsDeleted(false, pageable);
	        if (userRole.isEmpty()) {
	            logger.warn("No users found in the database.");
	            throw new RuntimeException("No users found.");
	        }
	        return userRole;
	    } catch (Exception e) {
	        logger.error("An error occurred while fetching all users: {}", e.getMessage());
	        throw new RuntimeException("An error occurred while fetching users.", e);
	        }
	   
	}



	@Override
	public List<UserRole> search(String searchTerm) {
		String cleanedSearchTerm = searchTerm.trim();
        return userRoleRepository.searchByTerm( cleanedSearchTerm);	
        
	}



	

	
}
