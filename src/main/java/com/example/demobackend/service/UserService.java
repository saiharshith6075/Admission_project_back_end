package com.example.demobackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demobackend.model.User;
import com.example.demobackend.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo userrep;
	
	public boolean loginUser(User admin) {
    	User user = userrep.findByEmailIdAndPassword(
    			admin.getEmailId(), admin.getPassword());
        return user != null;
    }
}
