package com.onbiron.onbironpdks.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onbiron.onbironpdks.entities.Users;
import com.onbiron.onbironpdks.repositories.IUserRepository;

@Service
public class UsersDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findByUsername(username);

        // Properly handle the Optional
        Users foundUser = user.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Return the UserDetails implementation
        return new org.springframework.security.core.userdetails.User(
                foundUser.getUsername(), 
                foundUser.getPassword(), 
                foundUser.getAuthorities());
    }
}
