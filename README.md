# :pushpin: Social Media Project
### 멋쟁이사자처럼 백엔드 스쿨 2기 개인 프로젝트

> 회원가입, 게시판 CRUD, 댓글 및 좋아요 등의 기능을 RESTful API 방식으로 구현한 Social Media 웹사이트

### 포스트맨 API Documentation
> https://documenter.getpostman.com/view/25565932/2s935vnLd7

### Swagger
> http://ec2-52-78-201-217.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/

---

## 목차
- 들어가며
  - 프로젝트 소개
  - 개발 환경
- 구조 및 설계
  - 아키텍쳐
  - ERD 설계
- 핵심 기능
- 트러블 슈팅
- 고려한 점
- 마치며
  - 프로젝트 보완 사항
  - 후기

---

## 들어가며
### 1. 프로젝트 소개

멋쟁이 사자처럼 백엔드 스쿨 과정으로 배운 기술을 직접 활용해보기 위해 SNS 프로젝트를 진행하게 되었다.
JPA, Security 등 새로 배운 기술을 처음 적용해 본 개인 프로젝트로 개인적으로 애정이 가고 또 지속적으로 고도화하고 있는 프로젝트이다.

### 2. 개발 환경

- 자바 : JAVA 11
- 개발 툴 : SpringBoot 2.7.5
- 빌드 : Gradle 6.8
- 서버 : AWS EC2
- 배포 : Docker
- 데이터베이스 : MySql 8.0
- 필수 라이브러리 : SpringBoot Web, MySQL, Spring Data JPA, Lombok, Spring Security, JWT 0.9.1
- 에디터 : Intellij

---
## 구조 및 설계

### Architecture

![img](/uploads/7f029b4aef1a23cf8a314b2d262eb6a0/img.png)

### ERD 설계

![ERD](/uploads/f1c807e890eca46bb841f990c5014ccb/ERD.png)

---

## 핵심 기능

### 1️⃣ 회원 인증·인가

- 회원가입 시, Spring Security의 BCryptPasswordEncoder를 사용하여 비밀번호를 암호화
- 로그인 하지 않으면 SNS 기능 중 피드를 보는 기능만 가능
- 로그인 한 회원은 글쓰기, 수정, 댓글, 좋아요, 알림 기능 사용 가능

### 2️⃣ 글쓰기

- 회원가입 후 로그인(Token 받기)해야 글을 작성할 수 있다.
- 글 길이는 총 300자 이하로만 작성 가능
- 최신 작성글 순으로 한 페이지 당 글 20개로 페이징 처리
- 로그인 하지 않아도 글 목록을 조회 가능
- 포스트 수정과 삭제는 글 작성자만 가능

### 3️⃣ 피드

- 로그인 한 회원은 자신이 작성한 글 목록을 볼 수 있다.

### 4️⃣ 댓글

- 로그인 한 회원만 댓글 작성 가능
- 댓글은 총 100자 이하로만 작성 가능

### 5️⃣ 좋아요

- 로그인 한 회원만 좋아요 기능 사용 가능
- 좋아요 취소 또한 가능

### 6️⃣ 알림

- 내가 작성한 포스트에 반응(댓글, 좋아요)이 달리면 알람 전송

---
## 트러블 슈팅

* @Transactional과 Lazy Loading
  * 프로젝트 진행 시, Lazy Loading(지연 로딩)으로 인한 LazyInitializationException을 가장 많이 맞닥뜨렸다. 준영속 상태의 엔티티에 지연 로딩을 시도해서 발생하는 에러였다.
  * 처음에는 Eager Loading(즉시 로딩)으로 에러를 해결하였다. 하지만 즉시 로딩 시, 엔티티 간의 관계가 복잡해질 수록 조인으로 인해 성능이 저하될 우려가 있어 이상적인 해결법이 아니라고 판단했다.
  * 따라서 @Transactional을 사용하여 영속 상태에서 미리 연관 관계를 로드하여 에러를 해결할 수 있었다.

---
## 고려한 점

* 서비스 계층에서 @Transactional(readOnly = true) 사용
  * 읽기 전용으로 트랜잭션을 열어 자원의 낭비를 줄이고 쿼리 성능을 최적화했다.

* JPA Auditing 적용
  * 글 작성, 댓글 작성 등에서 공통적으로 들어가는 생성 일자, 변경 일자를 추적하기 위해 해당 기능을 사용했다.

---
## 마치며

### 1. 프로젝트 보완 사항

* OAuth 2.0 구글 로그인 기능 추가
* 조회수 카운트 기능 추가
* 이미지 업로드 기능 추가
  * 소셜 미디어 특성상 많은 이미지가 저장될 것으로 예상된다. 따라서 무한대에 가까운 객체를 저장할 수 있는 S3를 사용해보려고 한다.
  또한 높은 내구성과 가용성을 저렴한 가격에 이용할 수 있다는 점에서 해당 프로젝트에 적용하기 적합한 기술이라고 판단된다.

### 2. 후기

스프링 부트와 JPA로 만든 간단한 게시판 API 였지만 직접 배포까지 해 본 첫 프로젝트라 감회가 남다르다. 
수동으로 빌드하여 EC2에 배포하는 과정을 거쳐 깃랩을 이용한 CI/CD 환경 구축으로 자동 배포 되는 과정이 특히 기억에 남는다. 
단순 구현에서 그치지 않고 시스템 구조와 서버 성능까지 고려하여 개발하는 경험을 할 수 있어 더욱 뿌듯했다. 더불어 여러 에러를 해결하며 꾸준한 공부의 중요성도 다시 한 번 깨닫는 계기가 됐다.