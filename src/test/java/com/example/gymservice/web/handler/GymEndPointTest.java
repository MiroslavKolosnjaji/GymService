package com.example.gymservice.web.handler;

import com.example.gymservice.dto.GymDTO;
import com.example.gymservice.model.Gym;
import com.example.gymservice.service.impl.GymServiceImpl;
import com.example.gymservice.web.router.GymRouterConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


/**
 * @author Miroslav Kološnjaji
 */
@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GymEndPointTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    GymServiceImpl gymService;


    @Test
    void testCreateGym() {

        webTestClient.post().uri(GymRouterConfig.GYM_PATH)
                .body(Mono.just(getTestGym(null)), Gym.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("/api/gym/4");
    }

    @Test
    void testCreateGymBadRequest() {

        var gym = getTestGym(null);
        gym.setCityId(null);

        webTestClient.post().uri(GymRouterConfig.GYM_PATH)
                .body(Mono.just(gym), Gym.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(3)
    void testUpdateGym() {

        var gym = getTestGym(1L);
        gym.setCityId(4L);

        webTestClient.put()
                .uri(GymRouterConfig.GYM_PATH_ID, gym.getId())
                .body(Mono.just(gym), Gym.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testUpdateGymBadRequest() {

        var gym = getTestGym(1L);
        gym.setAddress("");

        webTestClient.put()
                .uri(GymRouterConfig.GYM_PATH_ID, gym.getId())
                .body(Mono.just(gym), Gym.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testUpdateGymNotFound() {

        var gym = getTestGym(15L);

        webTestClient.put()
                .uri(GymRouterConfig.GYM_PATH_ID, 99)
                .body(Mono.just(gym), Gym.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(1)
    void testGetGymById() {

        webTestClient.get()
                .uri(GymRouterConfig.GYM_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(GymDTO.class);
    }

    @Test
    void testGetGymByIdNotFound() {

        webTestClient.get()
                .uri(GymRouterConfig.GYM_PATH_ID, 99)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(2)
    void testGetallGyms() {

        webTestClient.get()
                .uri(GymRouterConfig.GYM_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().jsonPath("$.length()").isEqualTo(3);
    }

    @Test
    void testDeleteGym() {

        var gym = getTestGym(1L);

        webTestClient.delete()
                .uri(GymRouterConfig.GYM_PATH_ID, gym.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    private Gym getTestGym(Long id) {
        return Gym.builder()
                .id(id)
                .name("TestGym")
                .address("testAddress")
                .phone("123123123")
                .email("test@example.com")
                .cityId(1L)
                .build();
    }
}