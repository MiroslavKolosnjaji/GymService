package com.example.gymservice.web.router;

import com.example.gymservice.web.handler.GymHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Miroslav Kološnjaji
 */
@Configuration
@RequiredArgsConstructor
public class GymRouterConfig {

    public static final String GYM_PATH = "/api/gym";
    public static final String GYM_PATH_ID = GYM_PATH + "/{gymId}";

    private final GymHandler gymHandler;

    @Bean
    public RouterFunction<ServerResponse> gymRouter(){
        return route()
                .POST(GYM_PATH, gymHandler::createGym)
                .PUT(GYM_PATH_ID, gymHandler::updateGym)
                .GET(GYM_PATH_ID, gymHandler::getGymById)
                .GET(GYM_PATH, gymHandler::getAllGyms)
                .DELETE(GYM_PATH_ID, gymHandler::deleteGym)
                .build();
    }
}
