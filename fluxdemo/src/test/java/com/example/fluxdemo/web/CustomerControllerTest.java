package com.example.fluxdemo.web;

import com.example.fluxdemo.DBinit;
import com.example.fluxdemo.domain.Customer;
import com.example.fluxdemo.domain.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

// 통힙 테스트
//@SpringBootTest // Spring Bean 다 띄워줌
//@AutoConfigureWebTestClient

@WebFluxTest // Controller 관련된 것을 bean으로 만들어줌
public class CustomerControllerTest {

    @MockBean // 가짜 객체
    CustomerRepository repository; // 인터페이스라 @import가 안됨

    @Autowired
    private WebTestClient webClient;

    @Test
    public void 한건찾기_테스트() {
        // given
        Mono<Customer> result = Mono.just(new Customer("chanmi", "park"));

        // stub -> 행동 지시
        when(repository.findById(1L)).thenReturn(result);

        webClient.get().uri("/customer/{id}",1L)
                .exchange()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("chanmi")
                .jsonPath("$.lastName").isEqualTo("park");
    }
}
