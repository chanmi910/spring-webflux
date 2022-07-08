package com.example.springwebflux.repository;

import com.example.springwebflux.domain.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    @Query("SELECT * FROM users WHERE id = :id")
    Mono<User> findById(Long id);

    @Query("SELECT * FROM users WHERE name = :name")
    Flux<User> findByName(String name);

}
