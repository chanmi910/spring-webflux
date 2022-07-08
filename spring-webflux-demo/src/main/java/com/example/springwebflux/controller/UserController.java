package com.example.springwebflux.controller;

import com.example.springwebflux.domain.User;
import com.example.springwebflux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.stream.Stream;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final Sinks.Many<User> sink;

    // A 요청 -> Flux -> Stream
    // B 요청 -> Flux -> Stream
    // -> Flux.merge -> sink (두 stream을 merge 할 수 있음)
    public UserController(UserService userService) {
        this.userService = userService;
        this.sink = Sinks.many().multicast().onBackpressureBuffer() ;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> create(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping
    public Flux<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable Long userId){
        Mono<User> user = userService.findById(userId);
        return user.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public Mono<ResponseEntity<User>> updateUserById(@PathVariable Long userId, @RequestBody User user){
        return userService.updateUser(userId,user)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{userId}")
    public Mono<ResponseEntity<Void>> deleteUserById(@PathVariable Long userId){
        return userService.deleteUser(userId)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamAllUsers() {
        return userService
                .getAllUsers()
                .flatMap(user -> Flux
                        .zip(Flux.interval(Duration.ofSeconds(2)),
                                Flux.fromStream(Stream.generate(() -> user)) // stream에 2초마다 user 데이터를 넣음
                        )
                        .map(Tuple2::getT2)
                );
    }

    @GetMapping("/sink") // 생략 produces = MediaType.TEXT_EVENT_STREAM_VALUE
    public Flux<ServerSentEvent<User>> findAllSSE() {
        return sink.asFlux().map(c -> ServerSentEvent.builder(c).build()).doOnCancel(() -> {
            sink.asFlux().blockLast();
        });
    }

    @PostMapping("/sink")
    public Mono<User> save(@RequestBody User user) {
        return userService.createUser(user).doOnNext(c -> {
            sink.tryEmitNext(c);
        });
    }
}
