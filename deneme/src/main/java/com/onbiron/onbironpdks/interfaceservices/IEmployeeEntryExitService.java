package com.onbiron.onbironpdks.interfaceservices;

import java.util.List;

import org.springframework.data.domain.Page;

import com.onbiron.onbironpdks.entities.EmployeeEntryExit;
import com.onbiron.onbironpdks.entities.Users;

public interface IEmployeeEntryExitService {
	
	
	
    public   List<EmployeeEntryExit> getEmployeeEntryExitByUserIdS(Long id);
	
	public EmployeeEntryExit createEmployeeEntryExitS(EmployeeEntryExit newEmployeeEntryExit);
	
	public List<EmployeeEntryExit> getEmployeeEntryExitAllS(); 
		
	public EmployeeEntryExit deleteEmployeeEntryExitS(Long employeeEntryExitId);
	
	public Page<EmployeeEntryExit> getAllEnrtyExitPageS(int page, int rowsPerPage );
	
	public List<EmployeeEntryExit> search(String searchTerm);
	
	public EmployeeEntryExit updateByEntryExitIdS(Long id, EmployeeEntryExit neEmployeeEntryExit);

}
