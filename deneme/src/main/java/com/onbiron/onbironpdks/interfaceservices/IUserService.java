package com.onbiron.onbironpdks.interfaceservices;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.onbiron.onbironpdks.entities.UserRole;
import com.onbiron.onbironpdks.entities.Users;

public interface IUserService {

	public Page<Users> getAllUserPageS(int page, int rowsPerPage );
	
	public List<Users> getAllUsersS();
	
	public Users getByUsernameS(String username);
	
    public Users getUserByIdS(Long id);
	
	public Users createUserS(Users newUser);
	
	public Users updateByUserIdS(Long userId, Users user);
	
	public Users deleteById(Long userId);
	
	public List<Users> search(String searchTerm);
	

    
    
}

