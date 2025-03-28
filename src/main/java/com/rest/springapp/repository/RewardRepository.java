
package com.rest.springapp.repository;

import com.rest.springapp.model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    // Find rewards by customer name
    @Query("SELECT r FROM Reward r WHERE r.name = ?1")
    List<Reward> findRewardsByCustomerName(String name);

    // Find rewards by points threshold
    @Query("SELECT r FROM Reward r WHERE r.points >= ?1")
    List<Reward> findRewardsByPoints(int pointsThreshold);
}
