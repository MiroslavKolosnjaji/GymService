package com.example.gymservice.service.impl;

import com.example.gymservice.dto.GymDTO;
import com.example.gymservice.mapper.GymMapper;
import com.example.gymservice.repository.GymRepository;
import com.example.gymservice.service.GymService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Miroslav Kološnjaji
 */
@Service
@RequiredArgsConstructor
public class GymServiceImpl implements GymService {

    private final GymRepository gymRepository;
    private final GymMapper gymMapper;


    @Override
    public Mono<GymDTO> save(GymDTO gymDTO) {
        return gymRepository.save(gymMapper.gymDTOtoGym(gymDTO))
                .map(gymMapper::gymToGymDTO);
    }

    @Override
    public Mono<GymDTO> update(GymDTO gymDTO) {
        return gymRepository.findById(gymDTO.getId())
                .map(foundGym -> {
                    foundGym.setName(gymDTO.getName());
                    foundGym.setAddress(gymDTO.getAddress());
                    foundGym.setPhone(gymDTO.getPhone());
                    foundGym.setEmail(gymDTO.getEmail());
                    foundGym.setCityId(gymDTO.getCityId());
                    return foundGym;
                }).flatMap(gymRepository::save)
                .map(gymMapper::gymToGymDTO);
    }

    @Override
    public Mono<GymDTO> findById(Long id) {
        return gymRepository.findById(id).map(gymMapper::gymToGymDTO);
    }

    @Override
    public Flux<GymDTO> getAll() {
        return gymRepository.findAll().map(gymMapper::gymToGymDTO);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return gymRepository.deleteById(id);
    }
}
