package com.loreal.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loreal.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, BigInteger>{
	Optional<User> findByName(String name); //name을 통해 회원을 조회
}
