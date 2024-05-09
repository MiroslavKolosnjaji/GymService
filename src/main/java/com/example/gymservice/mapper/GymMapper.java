package com.example.gymservice.mapper;

import com.example.gymservice.dto.GymDTO;
import com.example.gymservice.model.Gym;
import org.mapstruct.Mapper;

/**
 * @author Miroslav Kološnjaji
 */
@Mapper
public interface GymMapper {

    GymDTO gymToGymDTO(Gym gym);
    Gym gymDTOtoGym(GymDTO gymDTO);

}
