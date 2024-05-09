package com.example.gymservice.web.handler;

import com.example.gymservice.dto.GymDTO;
import com.example.gymservice.service.GymService;
import com.example.gymservice.web.router.GymRouterConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;


/**
 * @author Miroslav Kološnjaji
 */
@Component
@RequiredArgsConstructor
public class GymHandler {

    public static final String GYM_ID = "gymId";
    private final GymService gymService;
    private final Validator validator;

    public Mono<ServerResponse> createGym(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(GymDTO.class)
                .doOnSuccess(this::validate)
                .flatMap(gymService::save)
                .flatMap(savedGym -> ServerResponse.created(UriComponentsBuilder.fromPath(GymRouterConfig.GYM_PATH_ID)
                        .buildAndExpand(savedGym.getId()).toUri()).build());
    }

    public Mono<ServerResponse> updateGym(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(GymDTO.class)
                .doOnSuccess(this::validate)
                .flatMap(gymService::update)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(gymDTO -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getGymById(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(gymService.findById(Long.valueOf(serverRequest.pathVariable(GYM_ID)))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))), GymDTO.class);
    }

    public Mono<ServerResponse> getAllGyms(ServerRequest serverRequest) {
        return ServerResponse.ok().body(gymService.getAll(), GymDTO.class);
    }

    public Mono<ServerResponse> deleteGym(ServerRequest serverRequest) {
        return gymService.findById(Long.valueOf(serverRequest.pathVariable(GYM_ID)))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(gymDTO -> gymService.deleteById(gymDTO.getId()))
                .then(ServerResponse.noContent().build());
    }

    private void validate(GymDTO gymDTO){
        Errors errors = new BeanPropertyBindingResult(gymDTO, "gymDTO");
        validator.validate(gymDTO, errors);

        if (errors.hasErrors())
            throw new ServerWebInputException(errors.toString());
    }
}
