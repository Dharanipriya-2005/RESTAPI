package com.rest.springapp.service;

import com.rest.springapp.model.Reward;
import com.rest.springapp.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;

    // Create or Update Reward
    public Reward saveReward(Reward reward) {
        return rewardRepository.save(reward);
    }

    // Read single Reward by ID
    public Optional<Reward> getRewardById(Long id) {
        return rewardRepository.findById(id);
    }

    // Read all Rewards with pagination and sorting
    public Page<Reward> getAllRewards(Pageable pageable) {
        return rewardRepository.findAll(pageable);
    }

    // Find Rewards by Customer Name
    public List<Reward> getRewardsByCustomerName(String customerName) {
        return rewardRepository.findRewardsByCustomerName(customerName);
    }

    // Find Rewards by Points
    public List<Reward> getRewardsByPoints(int pointsThreshold) {
        return rewardRepository.findRewardsByPoints(pointsThreshold);
    }

    // Delete Reward by ID
    public void deleteReward(Long id) {
        if (rewardRepository.existsById(id)) {
            rewardRepository.deleteById(id);
        }
    }
}