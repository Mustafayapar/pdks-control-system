package com.onbiron.onbironpdks.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onbiron.onbironpdks.entities.UserRole;
import com.onbiron.onbironpdks.entities.Users;
import com.onbiron.onbironpdks.services.UserRoleService;	

@RestController
@RequestMapping("/userrole")
@CrossOrigin("*")
public class UserRoleController {

	private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);

    @Autowired
	private final UserRoleService userRoleService;
	
	public UserRoleController(UserRoleService userRoleService) {
	
		this.userRoleService=userRoleService;
		
	}


    // Belirli bir rol ID'sine sahip tüm kullanıcı rollerini getiren API
    @GetMapping("/findbyrole/{roleId}")
    public ResponseEntity<List<UserRole>> getUsersByRoleId(@PathVariable Long roleId) {
        List<UserRole> userRoles = userRoleService.getUsersByRoleIdS(roleId);
        if (userRoles.isEmpty()) {
            logger.warn("No user roles found for Role ID {}", roleId);
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Returning user roles for Role ID {}", roleId);
            return ResponseEntity.ok(userRoles);
        }
    }

    // Tüm kullanıcı rollerini getiren API
    @GetMapping("/findall")
    public ResponseEntity<Page<UserRole>> getAllUserRoles(@RequestParam int page, @RequestParam int rowsPerPage) {
       
    	Page<UserRole> userRolesPage = userRoleService.getAllUserPageS(page, rowsPerPage);
        if (userRolesPage.isEmpty()) {
            logger.info("No user roles found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Returning all user roles");
            return ResponseEntity.ok(userRolesPage);
        }
    }

    // Yeni bir kullanıcı rolü oluşturan API

    @PostMapping("/create")
       public ResponseEntity<UserRole> createUserRoleAPI(@RequestBody Map<String, Object> payload) {
    	 try {
             UserRole userRole = userRoleService.createUserRoleS(payload);
             return ResponseEntity.ok(userRole);
         } catch (Exception e) {
             return ResponseEntity.status(500).body(null); // Customize error handling as needed
         }
       }

    // Belirli bir kullanıcı rolünü silen API
    @PutMapping("/delete/{id}")
    public ResponseEntity<UserRole> deleteUserRole(@PathVariable Long id) {
        try {
            UserRole deletedUserRole = userRoleService.deleteUserRoleS(id);
            if (deletedUserRole == null) {
                logger.warn("User role not found for ID {}", id);
                return ResponseEntity.notFound().build();
            } else {
                logger.info("Deleted user role with ID {}", id);
                return ResponseEntity.ok(deletedUserRole);
            }
        } catch (Exception e) {
            logger.error("Error deleting user role: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Belirli bir kullanıcı rolünü güncelleyen API
    @PutMapping("/update/{id}")
    public ResponseEntity<UserRole> updateByIdApi(@PathVariable Long id, @RequestBody UserRole newUserRole){
        UserRole userRole = userRoleService.updateById(id, newUserRole);
    if(userRole == null){
        return ResponseEntity.notFound().build();
    }else
    return ResponseEntity.ok(userRole);
    }
    
    @GetMapping("/search/{term}")
    public List<UserRole> search(@PathVariable("term") String term) {
        return userRoleService.search(term);
    }
	
}
