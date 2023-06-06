package com.whomeow.whomeow.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    @Query(value="SELECT * FROM user U WHERE U.USEREMAIL = ?1", nativeQuery = true)
    User findByUserEmail(String userEmail);

    @Query(value = "SELECT USEREMAIL FROM user U WHERE U.USERNAME = ?1 and U.PHONENUMBER = ?2", nativeQuery = true)
    String findEmailByNameAndPhoneNumber(String userName, String phoneNumber);
}
