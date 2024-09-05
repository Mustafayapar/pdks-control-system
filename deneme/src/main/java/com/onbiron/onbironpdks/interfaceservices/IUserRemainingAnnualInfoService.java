package com.onbiron.onbironpdks.interfaceservices;

import java.util.List;

import org.springframework.data.domain.Page;

import com.onbiron.onbironpdks.entities.UserRemainingAnnualInfo;
import com.onbiron.onbironpdks.entities.Users;

public interface IUserRemainingAnnualInfoService {
	

	 public  UserRemainingAnnualInfo getRemainingInfoByUserIdS(Long userID);
	 
	 public List<UserRemainingAnnualInfo> getRemainingInfoAllS();
	 
	 public  UserRemainingAnnualInfo createRemainingInfoS(UserRemainingAnnualInfo userRemainingInfo);
	 
	 public UserRemainingAnnualInfo deleteRemainingInfoS(Long userRemainingInfoId);
	 
	 public UserRemainingAnnualInfo updateRemainingInfoS(Long userRemainingInfoId, UserRemainingAnnualInfo userRemainingInfo);
	 
	 public  UserRemainingAnnualInfo getUserRemainAnnInfoByUserIdS(Users user);

	public List<UserRemainingAnnualInfo> search(String searchTerm);

	public Page<UserRemainingAnnualInfo> getAllRemainingAnnualInfoPageS(int page, int rowsPerPage);
}
