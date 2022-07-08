## 프로젝트 설명
* `fluxtest`: Filter에서 ContentType을 설정해서 Reactive, SSE 체험
* `reative-test`: reactive-streams 라이브러리 사용 
  * Publisher, Subscriber, Subscription 구현체 생성하여 테스트
* `fluxdemo`: Spring Webflux, H2 DB 사용하여 조회 테스트, SSE 포함
* `spring-webflux-demo`: Spring Webflux CRUD 예제 (2가지 방식), SSE 포함

## spring-webflux-demo
#### Spring Webflux CRUD 예제 (2가지 방식)
   * Annotation-based reactive components
   * Functional routing and handling

####  Thymeleaf를 사용하기 위해 configration 추가
   * frontend에서 이벤트 스트림을 구독하기 위해 EventSource 사용 


#### 테스트 방법
* https://localhost:8080/api/index 접속 시, DB에서 조회한 데이터와 SSE로 받아오는 데이터가 화면에 주기적으로 출력된다.
* [POST] https://localhost:8080/api/users 를 통해 사용자를 등록하면 스트림으로 데이터를 받아 화면에 등록한 사용자가 출력된다.



