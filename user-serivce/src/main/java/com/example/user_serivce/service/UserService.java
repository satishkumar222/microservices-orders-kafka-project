package com.example.user_serivce.service;

import org.springframework.stereotype.Service;

import com.example.user_serivce.entity.User;
import com.example.user_serivce.repository.UserRepository;

@Service
public class UserService {

	 private UserRepository userRepo;

	public UserService(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}
	 
	 public User createUser(User user) {		 
		return userRepo.save(user); 
	 }
	 
	 public User getUserById(Long id) {
		return userRepo.findById(id).orElse(null);
		 
	 }
	 
	 public User findByEmail(String email) {
		return userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));
		 
	 }
	 
	
	 
	
}
