package com.onbiron.onbironpdks.controllers;

import java.util.List;

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

import com.onbiron.onbironpdks.entities.EmployeeEntryExit;
import com.onbiron.onbironpdks.entities.Users;
import com.onbiron.onbironpdks.services.EmployeeEntryExitService;



@RestController
@RequestMapping("/entryexit")
@CrossOrigin("*")
public class EmployeeEntryExitController {

	  private static final Logger logger = LoggerFactory.getLogger(EmployeeEntryExitController.class);

	    @Autowired
	    private EmployeeEntryExitService employeeEntryExitService;

	    public EmployeeEntryExitController(EmployeeEntryExitService employeeEntryExitService) {
			super();
			this.employeeEntryExitService = employeeEntryExitService;
		}

		// Kullanıcı ID'sine göre giriş/çıkış bilgilerini getiren API
	    @GetMapping("/find/{id}")
	    public ResponseEntity<List<EmployeeEntryExit>> getEmployeeEntryExitByUserId(@PathVariable Long id) {
	        try {
	            List<EmployeeEntryExit> entries = employeeEntryExitService.getEmployeeEntryExitByUserIdS(id);
	            if (entries.isEmpty()) {
	                logger.info("No entry/exit records found for User ID {}", id);
	                return ResponseEntity.notFound().build();
	            } else {
	                logger.info("Returning entry/exit records for User ID {}", id);
	                return ResponseEntity.ok(entries);
	            }
	        } catch (Exception e) {
	            logger.error("Error retrieving entry/exit records for User ID {}: {}", id, e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	    // Yeni bir giriş/çıkış bilgisi oluşturan API
	    @PostMapping("/create")
	    public ResponseEntity<EmployeeEntryExit> createEmployeeEntryExit(@RequestBody EmployeeEntryExit newEmployeeEntryExit) {
	        try {
	            EmployeeEntryExit createdEntryExit = employeeEntryExitService.createEmployeeEntryExitS(newEmployeeEntryExit);
	            logger.info("Created new entry/exit record with ID {}", createdEntryExit.getId());
	            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntryExit);
	        } catch (Exception e) {
	            logger.error("Error creating entry/exit record: {}", e.getMessage());
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	        }
	    }

	    // Tüm giriş/çıkış bilgilerini getiren API pagenable olmadığı için yorum satırına alındı
	    
	    @GetMapping("/")
	    public ResponseEntity<List<EmployeeEntryExit>> getAllEmployeeEntryExit() {
	        try {
	            List<EmployeeEntryExit> entries = employeeEntryExitService.getEmployeeEntryExitAllS();
	            if (entries.isEmpty()) {
	                logger.info("No entry/exit records found");
	                return ResponseEntity.notFound().build();	            } else {
 	                logger.info("Returning all entry/exit records");
	                return ResponseEntity.ok(entries);
	            }
	        } catch (Exception e) {
	            logger.error("Error retrieving all entry/exit records: {}", e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	    

	    @GetMapping("/findall")
	    public ResponseEntity<Page<EmployeeEntryExit>> getAllEmployeeEntryExitPagenable(@RequestParam int page, @RequestParam int rowsPerPage) {
	    	
	    	
	    	
	        Page<EmployeeEntryExit> entryExitPage = employeeEntryExitService.getAllEnrtyExitPageS(page, rowsPerPage);
	        if (entryExitPage.isEmpty()) {
	            logger.info("No users found");
	            return ResponseEntity.notFound().build();
	        } else {
	            logger.info("Returning all users");
	            return ResponseEntity.ok(entryExitPage);
	        }
	    }

	    // Belirli bir giriş/çıkış bilgisini silen API
	    @PutMapping("/delete/{id}")
	    public ResponseEntity<EmployeeEntryExit> deleteEmployeeEntryExit(@PathVariable Long id) {
	        try {
	            EmployeeEntryExit deletedEntryExit = employeeEntryExitService.deleteEmployeeEntryExitS(id);
	            if (deletedEntryExit == null) {
	                logger.warn("Entry/exit record not found for ID {}", id);
	                return ResponseEntity.notFound().build();
	            } else {
	                logger.info("Deleted entry/exit record with ID {}", id);
	                return ResponseEntity.ok(deletedEntryExit);
	            }
	        } catch (Exception e) {
	            logger.error("Error deleting entry/exit record: {}", e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	    
	    @PutMapping("/update/{id}")
	    public ResponseEntity<EmployeeEntryExit> updateEntryExit(@PathVariable Long id, @RequestBody EmployeeEntryExit newEntryExit) {
	        try {
	        	EmployeeEntryExit entryExit = employeeEntryExitService.updateByEntryExitIdS(id, newEntryExit);
	            if (entryExit == null) {
	                logger.warn("User not found for ID {}", id);
	                return ResponseEntity.notFound().build();
	            } else {
	                logger.info("Updated user with ID {}", id);
	                return ResponseEntity.ok(entryExit);
	            }
	        } catch (Exception e) {
	            logger.error("Error updating user: {}", e.getMessage());
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	        }
	    }
	    
	    @GetMapping("/search/{term}")
	    public List<EmployeeEntryExit> search(@PathVariable("term") String term) {
	        return employeeEntryExitService.search(term);
	    }
}
