package com.onbiron.onbironpdks.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.*;

import com.onbiron.onbironpdks.entities.CodeConstant;
import com.onbiron.onbironpdks.entities.UserAnnualReportInfo;
import com.onbiron.onbironpdks.entities.Users;
import com.onbiron.onbironpdks.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	 
	@Autowired
	private UserService userService;	
	
	public UserController( UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping()
    public ResponseEntity<List<Users>> getAllCodeConstant() {
        try {
            List<Users> users = userService.getAllUsersS();
            if (users.isEmpty()) {
                logger.info("No CodeConstants found");
                return ResponseEntity.notFound().build();
            } else {
                logger.info("Returning all CodeConstants");
                return ResponseEntity.ok(users);
            }
        } catch (Exception e) {
            logger.error("Error retrieving all CodeConstants: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
    @GetMapping("/findall")
    public ResponseEntity<Page<Users>> findAllUsers(@RequestParam int page, @RequestParam int rowsPerPage) {
    	
    	
    	
        Page<Users> usersPage = userService.getAllUserPageS(page, rowsPerPage);
        if (usersPage.isEmpty()) {
            logger.info("No users found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Returning all users");
            return ResponseEntity.ok(usersPage);
        }
    }

    // Kullanıcı adı ile kullanıcıyı getiren API
    @GetMapping("/findusername/{username}")
    public ResponseEntity<Users> getUserByUsername(@PathVariable String username) {
        Users user = userService.getByUsernameS(username);
        if (user == null) {
            logger.warn("User not found for username {}", username);
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Returning user for username {}", username);
            return ResponseEntity.ok(user);
        }
    }

    // ID ile kullanıcıyı getiren API
    @GetMapping("/find/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Users user = userService.getUserByIdS(id);
        if (user == null) {
            logger.warn("User not found for ID {}", id);
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Returning user for ID {}", id);
            return ResponseEntity.ok(user);
        }
    }

    // Yeni kullanıcı oluşturan API
    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestBody Users newUser) {
        try {
        	  Users users = userService.createUserS(newUser);
        	    if(users == null){
        	        return ResponseEntity.notFound().build();
        	    }else
        	    return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Kullanıcı bilgilerini güncelleyen API
    @PutMapping("/update/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        try {
            Users user = userService.updateByUserIdS(id, updatedUser);
            if (user == null) {
                logger.warn("User not found for ID {}", id);
                return ResponseEntity.notFound().build();
            } else {
                logger.info("Updated user with ID {}", id);
                return ResponseEntity.ok(user);
            }
        } catch (Exception e) {
            logger.error("Error updating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/delete/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable("id") Long id) {
    	try {
            // Kullanıcıyı "soft delete" yapar
            Users deletedUser = userService.deleteById(id);
            return ResponseEntity.ok(deletedUser);
        } catch (RuntimeException e) {
            // Kullanıcı bulunamadığında 404 Not Found döner
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // Diğer hatalar için 500 Internal Server Error döner
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/search/{term}")
    public List<Users> search(@PathVariable("term") String term) {
        return userService.search(term);
    }
	
	
	 
}
