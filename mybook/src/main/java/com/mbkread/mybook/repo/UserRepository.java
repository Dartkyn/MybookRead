package com.mbkread.mybook.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbkread.mybook.core.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
