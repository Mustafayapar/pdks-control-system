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

import com.onbiron.onbironpdks.entities.CodeConstant;
import com.onbiron.onbironpdks.entities.UserRole;
import com.onbiron.onbironpdks.enums.ParentIdType;
import com.onbiron.onbironpdks.services.CodeConstantService;

@RestController
@RequestMapping("/codeconstants")
@CrossOrigin("*")
public class CodeConstantController {

	 private static final Logger logger = LoggerFactory.getLogger(CodeConstantController.class);

	    @Autowired
	    private CodeConstantService codeConstantService;

	    public CodeConstantController(CodeConstantService codeConstantService) {
			super();
			this.codeConstantService = codeConstantService;
		}

		// Tüm CodeConstant kayıtlarını getirir
	    @GetMapping("/")
	    public ResponseEntity<List<CodeConstant>> getAllCodeConstant() {
	        try {
	            List<CodeConstant> codeConstants = codeConstantService.getAllCodeConstantS();
	            if (codeConstants.isEmpty()) {
	                logger.info("No CodeConstants found");
	                return ResponseEntity.notFound().build();
	            } else {
	                logger.info("Returning all CodeConstants");
	                return ResponseEntity.ok(codeConstants);
	            }
	        } catch (Exception e) {
	            logger.error("Error retrieving all CodeConstants: {}", e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	 // CodeConstant rollerini getiren API
	    @GetMapping("/findall")
	    public ResponseEntity<Page<CodeConstant>> getAllCodeConstantPages(@RequestParam int page, @RequestParam int rowsPerPage) {
	       
	    	Page<CodeConstant> codeConstantPage = codeConstantService.getAllCodeConstantPageS(page, rowsPerPage);
	        if (codeConstantPage.isEmpty()) {
	            logger.info("No user roles found");
	            return ResponseEntity.notFound().build();
	        } else {
	            logger.info("Returning all user roles");
	            return ResponseEntity.ok(codeConstantPage);
	        }
	    }

	    // Yeni bir CodeConstant oluşturur
	    @PostMapping("/create")
	    public ResponseEntity<CodeConstant> createCodeConstant(@RequestBody CodeConstant codeConstant) {
	        try {
	            CodeConstant createdCodeConstant = codeConstantService.createCodeConstantS(codeConstant);
	            logger.info("Created new CodeConstant with ID {}", createdCodeConstant.getId());
	            return ResponseEntity.status(HttpStatus.CREATED).body(createdCodeConstant);
	        } catch (Exception e) {
	            logger.error("Error creating CodeConstant: {}", e.getMessage());
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	        }
	    }

	    // ID'ye göre bir CodeConstant getirir
	    @GetMapping("/find/{id}")
	    public ResponseEntity<CodeConstant> getByIdCodeConstant(@PathVariable Long id) {
	        try {
	            CodeConstant codeConstant = codeConstantService.getByIdCodeConstantS(id);
	            if (codeConstant == null) {
	                logger.info("CodeConstant not found for ID {}", id);
	                return ResponseEntity.notFound().build();
	            } else {
	                logger.info("Returning CodeConstant for ID {}", id);
	                return ResponseEntity.ok(codeConstant);
	            }
	        } catch (Exception e) {
	            logger.error("Error retrieving CodeConstant for ID {}: {}", id, e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	    // ID'ye göre CodeConstant günceller
	    @PutMapping("/update/{id}")
	    public ResponseEntity<CodeConstant> updateByIdCodeConstant(@PathVariable Long id, @RequestBody CodeConstant codeConstant) {
	        try {
	            CodeConstant updatedCodeConstant = codeConstantService.updateByIdCodeConstantS(id, codeConstant);
	            if (updatedCodeConstant == null) {
	                logger.info("CodeConstant not found for ID {}", id);
	                return ResponseEntity.notFound().build();
	            } else {
	                logger.info("Updated CodeConstant with ID {}", id);
	                return ResponseEntity.ok(updatedCodeConstant);
	            }
	        } catch (Exception e) {
	            logger.error("Error updating CodeConstant for ID {}: {}", id, e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	    // Bir CodeConstant siler
	    @PutMapping("/delete/{id}")
	    public ResponseEntity<CodeConstant> deleteCodeConstant(@PathVariable Long id) {
	        try {
	            CodeConstant codeConstant = codeConstantService.getByIdCodeConstantS(id);
	            if (codeConstant == null) {
	                logger.info("CodeConstant not found for ID {}", id);
	                return ResponseEntity.notFound().build();
	            } else {
	                codeConstantService.deleteCodeConstantS(codeConstant);
	                logger.info("Deleted CodeConstant with ID {}", id);
	                return ResponseEntity.ok(codeConstant);
	            }
	        } catch (Exception e) {
	            logger.error("Error deleting CodeConstant for ID {}: {}", id, e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	    // Parent ID'ye göre CodeConstant getirir
	    @GetMapping("/findbyparent/{parentId}")
	    public ResponseEntity<List<CodeConstant>>  getByParentIdCodeConstant(@PathVariable long parentId) {
	        try {
	        	List<CodeConstant> codeConstant = codeConstantService.getByParentIdCodeConstant(parentId);
	            if (codeConstant == null) {
	                logger.info("CodeConstant not found for Parent ID {}", parentId);
	                return ResponseEntity.notFound().build();
	            } else {
	                logger.info("Returning CodeConstant for Parent ID {}", parentId);
	                return ResponseEntity.ok(codeConstant);
	            }
	        } catch (Exception e) {
	            logger.error("Error retrieving CodeConstant for Parent ID {}: {}", parentId, e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	    @GetMapping("/findbyparents")
	    public ResponseEntity<List<CodeConstant>>  getByParentIdsCodeConstant(@RequestParam  List<Long> parentIds) {
	        try {
	        	List<CodeConstant> codeConstant = codeConstantService.getByParentIdsCodeConstant(parentIds);
	            if (codeConstant == null) {
	                logger.info("CodeConstant not found for Parent ID {}", parentIds);
	                return ResponseEntity.notFound().build();
	            } else {
	                logger.info("Returning CodeConstant for Parent ID {}", parentIds);
	                return ResponseEntity.ok(codeConstant);
	            }
	        } catch (Exception e) {
	            logger.error("Error retrieving CodeConstant for Parent ID {}: {}", parentIds, e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	    
	    @GetMapping("/search/{term}")
	    public List<CodeConstant> search(@PathVariable("term") String term) {
	        return codeConstantService.search(term);
	    }
    
    
    
}
