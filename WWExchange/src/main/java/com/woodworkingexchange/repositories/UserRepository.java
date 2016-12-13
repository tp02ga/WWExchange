package com.woodworkingexchange.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.woodworkingexchange.domain.Users;

public interface UserRepository extends JpaRepository<Users, Long>
{

  Users findUsersByEmail(String username);

  @Query(value="update users u set u.password = (SELECT SUBSTRING(MD5(RAND()) FROM 1 FOR 8) AS password) where u.id = :id", nativeQuery=true)  
  void resetPassword (Long id);

  @Query("select u from Users u join fetch u.address a where u.email = :email")
  Users findUserWithAddress(@Param(value="email") String email);
}
