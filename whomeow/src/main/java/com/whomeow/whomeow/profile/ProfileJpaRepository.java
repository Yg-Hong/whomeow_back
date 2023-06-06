package com.whomeow.whomeow.profile;

import com.whomeow.whomeow.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileJpaRepository extends JpaRepository<Dog, Long> {

    Optional<Dog> findByUser(User user);
}
