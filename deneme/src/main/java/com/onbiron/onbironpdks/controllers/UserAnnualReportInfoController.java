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
import com.onbiron.onbironpdks.entities.UserAnnualReportInfo;
import com.onbiron.onbironpdks.services.UserAnnualReportInfoService;

@RestController
@RequestMapping("/annualreport")
@CrossOrigin("*")
public class UserAnnualReportInfoController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(UserAnnualReportInfoController.class);

    @Autowired
    private UserAnnualReportInfoService userAnnualReportInfoService;

    public UserAnnualReportInfoController(UserAnnualReportInfoService userAnnualReportInfoService) {
		this.userAnnualReportInfoService = userAnnualReportInfoService;
	}

	// Tüm kullanıcı yıllık rapor bilgilerini getiren API Pagenable olmadığı için kullanılmıyor
    
//    @GetMapping("/findall")
//    public ResponseEntity<List<UserAnnualReportInfo>> getAllUserAnnualReport() {
//        List<UserAnnualReportInfo> reports = userAnnualReportInfoService.getAllUserAnnualReportS();
//        if (reports.isEmpty()) {
//            logger.info("No annual reports found");
//            return ResponseEntity.notFound().build();
//        } else {
//            logger.info("Returning all annual reports");
//            return ResponseEntity.ok(reports);
//        }
//    }
    
    @GetMapping("/findall")
    public ResponseEntity<Page<UserAnnualReportInfo>> getAllUserAnnualReportPagenable(@RequestParam int page, @RequestParam int rowsPerPage) {
        Page<UserAnnualReportInfo> reports = userAnnualReportInfoService.getAllUserAnnualReportPagenableS(page, rowsPerPage);
        if (reports.isEmpty()) {
            logger.info("No annual reports found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Returning all annual reports");
            return ResponseEntity.ok(reports);
        }
    }

    // Belirli bir ID'ye göre kullanıcı yıllık rapor bilgilerini getiren API
    @GetMapping("/find/{id}")
    public ResponseEntity<UserAnnualReportInfo> getUserAnnReportInfoById(@PathVariable Long id) {
        UserAnnualReportInfo report = (UserAnnualReportInfo) userAnnualReportInfoService.getUserAnnReportInfoByIdS(id);
        if (report == null) {
            logger.warn("No annual report found for ID {}", id);
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Returning annual report for ID {}", id);
            return ResponseEntity.ok(report);
        }
    }

    // Yeni bir kullanıcı yıllık rapor bilgisi oluşturan API
    @PostMapping("/create")
    public ResponseEntity<UserAnnualReportInfo> createUserAnnReportInfo(@RequestBody UserAnnualReportInfo newUserAnnReportInfo) {
        try {
            UserAnnualReportInfo createdReport = userAnnualReportInfoService.createUserAnnReportInfoS(newUserAnnReportInfo);
            logger.info("Created new annual report with ID {}", createdReport.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
        } catch (Exception e) {
            logger.error("Error creating annual report: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Belirli bir kullanıcı yıllık rapor bilgisini güncelleyen API
    @PutMapping("/update/{id}")
    public ResponseEntity<UserAnnualReportInfo> updateUserAnnReportInfo(@PathVariable Long id, @RequestBody UserAnnualReportInfo userAnnReportInfo) {
        try {
            UserAnnualReportInfo updatedReport = userAnnualReportInfoService.updateByUserAnnReportInforIdS(id, userAnnReportInfo);
            if (updatedReport == null) {
                logger.warn("Annual report not found for ID {}", id);
                return ResponseEntity.notFound().build();
            } else {
                logger.info("Updated annual report with ID {}", id);
                return ResponseEntity.ok(updatedReport);
            }
        } catch (Exception e) {
            logger.error("Error updating annual report: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Belirli bir kullanıcı yıllık rapor bilgisini silen API
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserAnnualReportInfo> deleteUserAnnReportInfo(@PathVariable Long id) {
        try {
            UserAnnualReportInfo deletedReport = userAnnualReportInfoService.deleteUserAnnReportInfoS(id);
            if (deletedReport == null) {
                logger.warn("Annual report not found for ID {}", id);
                return ResponseEntity.notFound().build();
            } else {
                logger.info("Deleted annual report with ID {}", id);
                return ResponseEntity.ok(deletedReport);
            }
        } catch (Exception e) {
            logger.error("Error deleting annual report: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/search/{term}")
    public List<UserAnnualReportInfo> search(@PathVariable("term") String term) {
        return userAnnualReportInfoService.search(term);
    }
}
