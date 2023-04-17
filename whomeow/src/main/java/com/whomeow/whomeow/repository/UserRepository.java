package com.whomeow.whomeow.repository;

import com.whomeow.whomeow.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
