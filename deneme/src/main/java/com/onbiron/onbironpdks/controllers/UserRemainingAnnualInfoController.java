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
import com.onbiron.onbironpdks.entities.UserRemainingAnnualInfo;
import com.onbiron.onbironpdks.services.UserRemainingAnnualInfoService;

@RestController
@RequestMapping("/remaininginfo")
@CrossOrigin("*")
public class UserRemainingAnnualInfoController {
    private static final Logger logger = LoggerFactory.getLogger(UserRemainingAnnualInfoController.class);

    @Autowired
    private UserRemainingAnnualInfoService userRemainingAnnualInfoService; 
    

    public UserRemainingAnnualInfoController(UserRemainingAnnualInfoService userRemainingAnnualInfoService) {
		this.userRemainingAnnualInfoService = userRemainingAnnualInfoService;
	}

	// Belirli bir kullanıcı ID'sine göre kalan yıllık izin bilgilerini getiren API
    @GetMapping("/find/{id}")
    public ResponseEntity<UserRemainingAnnualInfo> getRemainingInfoByUserId(@PathVariable Long userId) {
        UserRemainingAnnualInfo remainingInfos = userRemainingAnnualInfoService.getRemainingInfoByUserIdS(userId);
        if (remainingInfos==null) {
            logger.warn("No remaining info found for User ID {}", userId);
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Returning remaining info for User ID {}", userId);
            return ResponseEntity.ok(remainingInfos);
        }
    }

    // Tüm kalan yıllık izin bilgilerini getiren API
    @GetMapping("/")
    public ResponseEntity<List<UserRemainingAnnualInfo>> getAllRemainingInfo() {
        List<UserRemainingAnnualInfo> remainingInfos = userRemainingAnnualInfoService.getRemainingInfoAllS();
        if (remainingInfos.isEmpty()) {
            logger.info("No remaining info found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Returning all remaining info");
            return ResponseEntity.ok(remainingInfos);
        }
    }
    // UserRemainingAnnualInfo rollerini getiren API
    @GetMapping("/findall")
    public ResponseEntity<Page<UserRemainingAnnualInfo>> getAllUserRemainingAnnualInfoPages(@RequestParam int page, @RequestParam int rowsPerPage) {
       
    	Page<UserRemainingAnnualInfo> remainingAnnualInfoPage = userRemainingAnnualInfoService.getAllRemainingAnnualInfoPageS(page, rowsPerPage);
        if (remainingAnnualInfoPage.isEmpty()) {
            logger.info("No user roles found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Returning all user roles");
            return ResponseEntity.ok(remainingAnnualInfoPage);
        }
    }


    // Yeni bir kalan yıllık izin bilgisi oluşturan API
    @PostMapping("/create")
    public ResponseEntity<UserRemainingAnnualInfo> createRemainingInfo(@RequestBody UserRemainingAnnualInfo userRemainingInfo) {
        try {
            UserRemainingAnnualInfo createdInfo = userRemainingAnnualInfoService.createRemainingInfoS(userRemainingInfo);
            logger.info("Created new remaining info with ID {}", createdInfo.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInfo);
        } catch (Exception e) {
            logger.error("Error creating remaining info: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Belirli bir kalan yıllık izin bilgisini silen API
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserRemainingAnnualInfo> deleteRemainingInfo(@PathVariable Long id) {
        try {
            UserRemainingAnnualInfo deletedInfo = userRemainingAnnualInfoService.deleteRemainingInfoS(id);
            if (deletedInfo == null) {
                logger.warn("Remaining info not found for ID {}", id);
                return ResponseEntity.notFound().build();
            } else {
                logger.info("Deleted remaining info with ID {}", id);
                return ResponseEntity.ok(deletedInfo);
            }
        } catch (Exception e) {
            logger.error("Error deleting remaining info: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Belirli bir kalan yıllık izin bilgisini güncelleyen API
    @PutMapping("/update/{id}")
    public ResponseEntity<UserRemainingAnnualInfo> updateRemainingInfo(@PathVariable Long id, @RequestBody UserRemainingAnnualInfo userRemainingInfo) {
        try {
            UserRemainingAnnualInfo updatedInfo = userRemainingAnnualInfoService.updateRemainingInfoS(id, userRemainingInfo);
            if (updatedInfo == null) {
                logger.warn("Remaining info not found for ID {}", id);
                return ResponseEntity.notFound().build();
            } else {
                logger.info("Updated remaining info with ID {}", id);
                return ResponseEntity.ok(updatedInfo);
            }
        } catch (Exception e) {
            logger.error("Error updating remaining info: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/search/{term}")
    public List<UserRemainingAnnualInfo> search(@PathVariable("term") String term) {
        return userRemainingAnnualInfoService.search(term);
    }

    
	
}
