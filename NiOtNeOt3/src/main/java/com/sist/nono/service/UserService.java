package com.sist.nono.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nono.model.RoleType;
import com.sist.nono.model.User;
import com.sist.nono.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public User save(User user) {
		String rawPwd = user.getCu_pwd();
		String encPwd = encoder.encode(rawPwd); //해쉬화
		user.setCu_pwd(encPwd);
		user.setRole(RoleType.USER);
		return userRepository.save(user);
	}
}
