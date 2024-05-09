package com.example.gymservice.bootstrap;

import com.example.gymservice.model.Gym;
import com.example.gymservice.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Miroslav Kološnjaji
 */
@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final GymRepository gymRepository;


    @Override
    public void run(String... args) throws Exception {
        gymRepository.deleteAll().doOnSuccess(success -> loadGymData()).subscribe();
    }

    private void loadGymData() {
        gymRepository.count().subscribe(count -> {
            Gym gym1 = getGym("AlphaGym", "testAddress", "123123123", "testAplha@example.com", 1L);
            Gym gym2 = getGym("Invictus", "XC", "123111333", "invictus@example.com", 3L);
            Gym gym3 = getGym("Gold Gym", "unknown", "525423213", "goldGym@example.com", 3L);

            gymRepository.save(gym1).subscribe();
            gymRepository.save(gym2).subscribe();
            gymRepository.save(gym3).subscribe();
        });
    }

    private Gym getGym(String name, String address, String phone, String email, Long cityId){
        return Gym.builder()
                .name(name)
                .address(address)
                .phone(phone)
                .email(email)
                .cityId(cityId)
                .build();
    }
}
