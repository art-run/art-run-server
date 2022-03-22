### 요약

---

Art Run은 GPS 아트를 위한 러닝앱입니다.

🗓️ **작업기간** : 2022.01 ~ 2022.05

👨‍💻 **투입인원** : 5명(서버1 + 앱4)

📒 **주요업무** 

Spring 기반 앱 서버 개발 (100%)

🌱 **스킬 및 사용툴**

`Spring Boot` `Spring Data JPA` `MySQL` `Junit5` `Spring Security` `Kubernetes` `Redis` `WebSocket` `Kafka` 



### 🏃 개발 내용

---

- Spring Security를 활용하여 JWT 로그인/회원가입 구축
- [Github Actions를 이용한 GKE Kubernetes CI/CD 파이프라인 구축](https://github.com/art-run/art-run-server/actions)
- [QueryDSL, Spring Data JPA를 활용하여 No Offset 페이징 기능 개발](https://blog.zwan.me/no-offset-)
- [쿠버네티스 분산 환경을 고려하여 Socket 통신을 활용한 맵매칭 기능 설계 및 구현](https://blog.zwan.me/art-run--12)
- GlobalExceptionHandler를 통해 통합 예외 처리 시스템 구축
- Mockito를 이용하여 mock test 구현
- Swagger UI를 활용하여 [API Document](http://artrun.kro.kr/swagger-ui/index.html#/)를 작성하고 앱 개발자들과 소통하였음


### 🗺 아키텍처 다이어그램

---
<img width="548" alt="스크린샷 2022-03-01 오후 11 43 09" src="https://user-images.githubusercontent.com/20726714/159438484-36c97269-d90f-4689-92d7-32dd44a3e51b.png">

Dev/Ops

1. GitHub에 code를 push합니다.
2. GitHubActions에 의하여 CI/CD가 동작합니다.
    - 코드를 테스트, 빌드합니다.
    - docker image를 빌드합니다.
    - docker image를 GCP 내의 쿠버네티스 클러스터에 배포합니다.

User

1. Android 앱을 통해 서버에 접근합니다. 
    - 이어폰 등의 모바일 디바이스로 음성 네비게이션을 들을 수 있습니다.
2. Cloud Load Balancing 서버를 거쳐서 부하분산이 이루어집니다.
3. GKE Kubernetes cluster에 의해 관리되는 코어 서버로 요청이 전달됩니다.
4. 코어 서버는 Cloud SQL 데이터베이스에 접근하여 정보를 가져옵니다.
5. 중간에 Kafka Instance를 통해서 분산 큐잉 처리가 가능합니다.
