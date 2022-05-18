## 요약

Art Run은 GPS 아트를 위한 러닝앱입니다.

🗓️ **작업기간** : 2022.01 ~ 2022.05

👨‍💻 **투입인원** : 5명(서버1 + 앱4)

📒 **주요업무** 

Spring 기반 앱 서버 개발 (100%)

🌱 **스킬 및 사용툴**

`Spring Boot` `Spring Data JPA` `MySQL` `Junit5` `Spring Security` `Kubernetes` `Redis` `WebSocket` `Kafka` 



## 🏃 개발 내용

- Spring Security를 활용하여 JWT 로그인/회원가입 구축
- [Github Actions를 이용한 GKE Kubernetes CI/CD 파이프라인 구축](https://github.com/art-run/art-run-server/actions)
- [QueryDSL, Spring Data JPA를 활용하여 No Offset 페이징 기능 개발](https://blog.zwan.me/no-offset-)
- [쿠버네티스 분산 환경을 고려하여 Socket 통신을 활용한 맵매칭 기능 설계 및 구현](https://blog.zwan.me/art-run--12)
- GlobalExceptionHandler를 통해 통합 예외 처리 시스템 구축
- Mockito를 이용하여 mock test 구현
- jacoco를 이용하여 라인 커버리지 60% 이상의 코드만 배포할 수 있도록 설정함
- Swagger UI를 활용하여 [API Document](http://artrun.kro.kr/swagger-ui/index.html#/)를 작성하고 앱 개발자들과 소통하였음


## 🗺 아키텍처 다이어그램

![image](https://user-images.githubusercontent.com/20726714/167296278-4339a825-9928-4b8e-8ace-14949e11852d.png)

Dev/Ops

1. GitHub에 code를 push합니다.
2. GitHubActions에 의하여 CI/CD가 동작합니다.
    - 코드를 테스트, jacoco 커버리지 확인, 빌드합니다.
    - docker image를 빌드합니다.
    - docker image를 GCP 내의 쿠버네티스 클러스터에 배포합니다.

User

1. Android 앱을 통해 서버에 접근합니다. 
    - 이어폰 등의 모바일 디바이스로 음성 네비게이션을 들을 수 있습니다.
2. Cloud Load Balancing 서버를 거쳐서 부하분산이 이루어집니다.
3. 요청은 GKE Kubernetes cluster 내의 Art Run 서버로 전달됩니다.
4. Art Run 서버는 Kubernetes cluster 내의 Worker 서버, Redis, Kafka와 통신합니다.
5. Art Run 서버는 데이터베이스로 GCP의 Cloud SQL을 이용합니다.



## 🗺  맵매칭 기능

![image](https://user-images.githubusercontent.com/20726714/165111910-40f3decb-c32f-4339-9fac-ee9accc165ce.png)

맵매칭 시퀀스 다이어그램

1. 달리기 시작 단계에서 최초 1번 socket 연결과 해당 routeId에 대한 redis 구독을 한다.
2. GPS 보정 요청을 socket을 통해 전송한다. socket은 메시지를 받으면 kafka `match.req` 토픽에 전송한다.
    
    워커에서는 `match.req` 토픽에서 메시지를 consume하여 GPS 값을 보정한 뒤, 워커에서 `match.res` 토픽으로 전송한다.
    
3. 서버는 Kafka  `match.res` 토픽을 구독하고 있다가, 메시지가 오면 consume 한 뒤 routeId의 redis 토픽에 메시지를 그대로 전송한다.
    
    redis는 구독하고 있는 서버(1번에서 구독한 서버)에 보정값을 전송하고, 해당 서버는 socket으로 클라이언트에게 결과를 전달한다.
    

![image](https://user-images.githubusercontent.com/20726714/165111936-836b0b8a-7a4d-4758-94fd-d79032da304e.png)

맵매칭 기능 아키텍처 다이어그램

[검정색] Client들은 Load Balancing 되어 Sticky하게 각각의 서버에 붙어있다.

[보라색] 웹소켓을 통해 전송된 요청은 Kafka를 거쳐서 워커에 (하나의 컨슈머 그룹) 도착한다. 

[노란색] 워커에서는 보정 처리를 하여 Kafka로 전송한다. 서버는 (하나의 컨슈머 그룹) Kafka를 통해 보정값을 받는다. 이 때, 보정값을 받는 서버는 알 수 없다. (이를 해결하기 위해 redis가 있다.)

[빨간색] 서버는 redis의 routeId 토픽에 메시지를 그대로 전송한다. 그러면 이를 구독하고 있던 서버에서 읽을 수 있다.

[검정색] 받은 결과를 다시 클라이언트에 돌려준다.
