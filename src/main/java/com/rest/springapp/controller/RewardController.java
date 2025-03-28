package com.rest.springapp.controller;

import com.rest.springapp.model.Reward;
import com.rest.springapp.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    // Create Reward
    @PostMapping
    public ResponseEntity<Reward> createReward(@RequestBody Reward reward) {
        Reward savedReward = rewardService.saveReward(reward);
        return new ResponseEntity<>(savedReward, HttpStatus.CREATED);
    }

    // Update Reward
    @PutMapping("/{id}")
    public ResponseEntity<Reward> updateReward(@PathVariable Long id, @RequestBody Reward rewardDetails) {
        Optional<Reward> existingReward = rewardService.getRewardById(id);
        if (existingReward.isPresent()) {
            Reward reward = existingReward.get();
            reward.setName(rewardDetails.getName());
            reward.setPoints(rewardDetails.getPoints());
            Reward updatedReward = rewardService.saveReward(reward);
            return ResponseEntity.ok(updatedReward);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Reward by ID
    @GetMapping("/{id}")
    public ResponseEntity<Reward> getRewardById(@PathVariable Long id) {
        Optional<Reward> reward = rewardService.getRewardById(id);
        return reward.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all Rewards with Pagination and Sorting
    @GetMapping
    public ResponseEntity<Page<Reward>> getAllRewards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<Reward> rewards = rewardService.getAllRewards(pageable);
        return ResponseEntity.ok(rewards);
    }

    // Find Rewards by Customer Name
    @GetMapping("/customer/{name}")
    public ResponseEntity<List<Reward>> getRewardsByCustomerName(@PathVariable String name) {
        List<Reward> rewards = rewardService.getRewardsByCustomerName(name);
        return ResponseEntity.ok(rewards);
    }

    // Find Rewards by Points Threshold
    @GetMapping("/points/{points}")
    public ResponseEntity<List<Reward>> getRewardsByPoints(@PathVariable int points) {
        List<Reward> rewards = rewardService.getRewardsByPoints(points);
        return ResponseEntity.ok(rewards);
    }

    // Delete Reward by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReward(@PathVariable Long id) {
        rewardService.deleteReward(id);
        return ResponseEntity.noContent().build();
    }
}