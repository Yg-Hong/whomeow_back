package com.whomeow.whomeow.behavior;

import com.whomeow.whomeow.profile.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BehaviorJpaRepository extends JpaRepository<Behavior, Long> {
    Behavior findByDog(Dog dog);

    @Query(value = "SELECT * FROM behavior WHERE dogKey = ?3 AND createdat >= ?1 AND createdat <= ?2", nativeQuery = true)
    List<Behavior> findByDate(String startDate, String endDate, Long dogKey);
}
