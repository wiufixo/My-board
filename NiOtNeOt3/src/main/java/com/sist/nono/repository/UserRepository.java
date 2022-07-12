package com.sist.nono.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.Board;
import com.sist.nono.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query(value = "select * from user where cu_id=?1", nativeQuery = true)
	Optional<User> findByCu_id(String cu_id);
	
	@Query(value = "select count(*) from user where cu_id=?1 and cu_pwd=?2", nativeQuery = true)
	int findUser(String cu_id, String cu_pwd);
}
