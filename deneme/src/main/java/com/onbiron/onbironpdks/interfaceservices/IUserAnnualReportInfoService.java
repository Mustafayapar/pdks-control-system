package com.onbiron.onbironpdks.interfaceservices;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

import com.onbiron.onbironpdks.entities.UserAnnualReportInfo;



public interface IUserAnnualReportInfoService {
	
   
	public List<UserAnnualReportInfo> getAllUserAnnualReportS();
	
	
    public List<UserAnnualReportInfo> getUserAnnReportInfoByIdS(Long id);
	
	public UserAnnualReportInfo createUserAnnReportInfoS(@RequestBody  Map<String, Object> payload);
	
	public UserAnnualReportInfo updateByUserAnnReportInforIdS(Long userId, UserAnnualReportInfo userAnnReportInfo);
			
	public UserAnnualReportInfo deleteUserAnnReportInfoS(Long userAnnReportInfo);
	
	public Page<UserAnnualReportInfo> getAllUserAnnualReportPagenableS(int page, int rowsPerPage);
	
	public List<UserAnnualReportInfo> search(String searchTerm);
	
	public long calculateBusinessDays(UserAnnualReportInfo newUserAnnReportInfo);
	
	public List<UserAnnualReportInfo> getIsLeaveUserAnnualReportS();
	
	public long countUserAnnualReportInfoIsLeaveS();
}
