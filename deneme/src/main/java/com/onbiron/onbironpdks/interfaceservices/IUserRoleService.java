package com.onbiron.onbironpdks.interfaceservices;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.onbiron.onbironpdks.entities.EmployeeEntryExit;
import com.onbiron.onbironpdks.entities.UserRole;
import com.onbiron.onbironpdks.enums.ParentIdType;

public interface IUserRoleService {
	
	public Page<UserRole> getAllUserPageS(int page, int rowsPerPage );
	
	 public List<UserRole> search(String searchTerm);
	
	 public List<UserRole> getUsersByRoleIdS(long parentId);
	 
	 public List<UserRole> getUserRoleAllS();
	 
	 public UserRole createUserRoleS(Map<String, Object> payload);
	 
	 public UserRole deleteUserRoleS(Long userRoleId);
	 
	 public UserRole  updateById(Long id,Map<String, Object> payload);
	 
	 public UserRole getByUserId(Long userId);
	 
	
	
}
