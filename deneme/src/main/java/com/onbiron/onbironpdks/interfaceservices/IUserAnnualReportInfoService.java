package com.onbiron.onbironpdks.interfaceservices;

import java.util.List;

import org.springframework.data.domain.Page;

import com.onbiron.onbironpdks.entities.UserAnnualReportInfo;



public interface IUserAnnualReportInfoService {
	
   
	public List<UserAnnualReportInfo> getAllUserAnnualReportS();
	
	
    public List<UserAnnualReportInfo> getUserAnnReportInfoByIdS(Long id);
	
	public UserAnnualReportInfo createUserAnnReportInfoS(UserAnnualReportInfo newUserAnnReportInfo);
	
	public UserAnnualReportInfo updateByUserAnnReportInforIdS(Long userId, UserAnnualReportInfo userAnnReportInfo);
			
	public UserAnnualReportInfo deleteUserAnnReportInfoS(Long userAnnReportInfo);
	
	public Page<UserAnnualReportInfo> getAllUserAnnualReportPagenableS(int page, int rowsPerPage);
	
	public List<UserAnnualReportInfo> search(String searchTerm);
}
