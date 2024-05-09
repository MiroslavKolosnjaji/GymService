package com.example.gymservice.service;

import com.example.gymservice.dto.GymDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Miroslav Kološnjaji
 */
public interface GymService {

    Mono<GymDTO> save(GymDTO gymDTO);
    Mono<GymDTO> update(GymDTO gymDTO);
    Mono<GymDTO> findById(Long id);
    Flux<GymDTO> getAll();
    Mono<Void> deleteById(Long id);
}
