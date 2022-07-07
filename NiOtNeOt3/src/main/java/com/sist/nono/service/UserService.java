package com.sist.nono.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.nono.model.Board;
import com.sist.nono.model.User;
import com.sist.nono.repository.BoardRepository;
import com.sist.nono.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User findById(int cu_no) {
		return userRepository.findById(cu_no).get();
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
}
