package com.example.gymservice.repository;

import com.example.gymservice.model.Gym;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author Miroslav Kološnjaji
 */
public interface GymRepository extends ReactiveCrudRepository<Gym, Long> {
}
