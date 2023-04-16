package com.whomeow.whomeow.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    @Query(value="select * from user u where u.userEmail = ?1", nativeQuery = true)
    User findByUserEmail(String userEmail);
}
