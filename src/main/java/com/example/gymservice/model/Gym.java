package com.example.gymservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author Miroslav Kološnjaji
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gym {

    @Id
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private Long cityId;
}
