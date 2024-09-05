package com.onbiron.onbironpdks.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.onbiron.onbironpdks.entities.Users;
import com.onbiron.onbironpdks.interfaceservices.IUserService;
import com.onbiron.onbironpdks.repositories.IUserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final IUserRepository userRepository;
	

	

	public UserService(IUserRepository userRepository) {
		super();
		this.userRepository = userRepository;
		
	}

	@Override
	public Users getUserByIdS(Long id) { // tekrar bak ************************
		 System.out.println("Checking request source...");

	        logger.info("Received request for user with ID: {}", id);

	        Users users = userRepository.findById(id).orElse(null);

	        if (users != null) {
	            logger.info("Returning user: {}", users);
	            return users;
	        } else {
	            logger.warn("User with ID {} not found", id);
	            return null;
	        }
	
	}

	@Override
	public Page<Users> getAllUserPageS(int page, int rowsPerPage) {
		try {
			Pageable pageable = PageRequest.of(page, rowsPerPage);
	        Page<Users> users =  userRepository.findAllByIsDeleted(false, pageable);
	        if (users.isEmpty()) {
	            logger.warn("No users found in the database.");
	            throw new RuntimeException("No users found.");
	        }
	        return users;
	    } catch (Exception e) {
	        logger.error("An error occurred while fetching all users: {}", e.getMessage());
	        throw new RuntimeException("An error occurred while fetching users.", e);
	        }
	   
	}

	// get user by username
	@Override
	public Users getByUsernameS(String username) {

		 try {
		        Users user = userRepository.findByUsername(username)
		            .orElseThrow(() -> {
		                logger.warn("User with username '{}' not found.", username);
		                return new RuntimeException("User not found.");
		            });
		        logger.info("Returning user with username: {}", username);
		        return user;
		    } catch (Exception e) {
		        logger.error("An error occurred while fetching user with username '{}': {}", username, e.getMessage());
		        throw new RuntimeException("An error occurred while fetching the user.", e);
		    }	}

	@Override
	public Users createUserS(Users newUser) {
		
		try {
	        // Check if a user with the same username already exists
	        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
	            throw new IllegalArgumentException("Username already exists");
	        }
	        
	        // Create the user
	        Users savedUser = userRepository.save(newUser);
	        return savedUser;
	    } catch (IllegalArgumentException e) {
	        logger.error("Error creating user: {}", e.getMessage());
	        throw e;
	        
	    } catch (Exception e) {
	        logger.error("Unexpected error creating user: {}", e.getMessage());
	        throw new RuntimeException("Failed to create user", e);
	    }
	}

	@Override
	public Users updateByUserIdS(Long userId, Users updatedUser) {
		 
		try {
		        // Check if the user exists
		        Users existingUser = userRepository.findById(userId)
		            .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

		        // Update user details
		        existingUser.setUsername(updatedUser.getUsername());
		        existingUser.setName(updatedUser.getName());
		        existingUser.setSurname(updatedUser.getSurname());
		        existingUser.setRegistrationNumber(updatedUser.getRegistrationNumber());
		        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
		        existingUser.setBirthdate(updatedUser.getBirthdate());
		        existingUser.setTitle(updatedUser.getTitle());
		        existingUser.setPassword(updatedUser.getPassword());
		        existingUser.setEmail(updatedUser.getEmail());

		        // Save the updated user
		        Users savedUser = userRepository.save(existingUser);
		        return savedUser;
		    } catch (NoSuchElementException e) {
		        logger.error("Error updating user: {}", e.getMessage());
		        throw e;
		    } catch (Exception e) {
		        logger.error("Unexpected error updating user: {}", e.getMessage());
		        throw new RuntimeException("Failed to update user", e);
		    }
	}

	@Override
	public Users deleteById(Long userId) {
		 try {
		        // Verilen ID'ye göre kullanıcıyı bul
		        Optional<Users> userOptional = userRepository.findById(userId);
		        
		        if (userOptional.isPresent()) {
		            Users user = userOptional.get();
		            
		            user.setIsDeleted(true);

		            userRepository.save(user);
		            
		            return user;
		        } else {
		          
		            throw new RuntimeException("User not found for ID: " + userId);
		        }
		    } catch (Exception e) {
		        // Hata durumunda loglama yap ve hata fırlat
		        logger.error("Error deleting user with ID {}: {}", userId, e.getMessage());
		        throw new RuntimeException("Failed to delete user.", e);
		    }
	}

	

	@Override
	public List<Users> getAllUsersS() {
		try {
	        // isDeleted alanı false olan kullanıcıları getirir
	        List<Users> users = userRepository.findAllByIsDeleted(false);
	        if (users.isEmpty()) {
	            logger.warn("No users found.");
	            throw new RuntimeException("No users found.");
	        }
	        return users;
	    } catch (Exception e) {
	        logger.error("Error retrieving users: {}", e.getMessage());
	        throw new RuntimeException("Failed to retrieve users.", e);
	    }
	}

	@Override
	public List<Users> search(String searchTerm) {
		String cleanedSearchTerm = searchTerm.trim();
        return userRepository.searchByTerm( cleanedSearchTerm);	}

	
	
	

	
}
