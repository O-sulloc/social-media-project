# :pushpin: Social Media Project
### 멋쟁이사자처럼 백엔드 스쿨 2기 개인 프로젝트

> 회원가입, 게시판 CRUD, 댓글 및 좋아요 등의 기능을 RESTful API로 구현한 Social Media 웹사이트입니다.

### URL
> http://ec2-43-201-23-110.ap-northeast-2.compute.amazonaws.com:8080

### Postman API Documentation
> https://documenter.getpostman.com/view/25565932/2s935vnLd7

### Swagger
> http://ec2-43-201-23-110.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/

---

### 개발환경

- 에디터 : Intellij Ultimate
- 개발 툴 : SpringBoot 2.7.5
- 자바 : JAVA 11
- 빌드 : Gradle 6.8
- 서버 : AWS EC2
- 배포 : Docker
- 데이터베이스 : MySql 8.0
- 필수 라이브러리 : SpringBoot Web, MySQL, Spring Data JPA, Lombok, Spring Security, JWT 0.9.1

---

### Architecture

![img](/uploads/7f029b4aef1a23cf8a314b2d262eb6a0/img.png)

---

### ERD 설계

![ERD](/uploads/f1c807e890eca46bb841f990c5014ccb/ERD.png)

---

### 핵심 기능

#### 1️⃣ 회원 인증·인가

- 회원가입 시, Spring Security의 BCryptPasswordEncoder를 사용하여 비밀번호를 암호화했습니다.
- 로그인을 하지 않으면 SNS 기능 중 피드를 보는 기능만 가능합니다.
- 로그인한 회원은 글쓰기, 수정, 댓글, 좋아요, 알림 기능이 가능합니다.

#### 2️⃣ 글쓰기

- 포스트를 쓰려면 회원가입 후 로그인(Token받기)을 해야 합니다.
- 포스트의 길이는 총 300자 이상을 넘을 수 없습니다.
- 포스트의 한 페이지는 20개씩 보이고 총 몇 개의 페이지인지 표시가 됩니다.
- 로그인 하지 않아도 글 목록을 조회 할 수 있습니다.
- 수정 기능은 글을 쓴 회원만이 권한을 가집니다.
- 포스트의 삭제 기능은 글을 쓴 회원만이 권한을 가집니다.

![스크린샷_2022-12-27_오후_5.58.05](/uploads/660c3acc1b62dabe5788ff8d3a49f1d1/스크린샷_2022-12-27_오후_5.58.05.png)


#### 3️⃣ 피드

- 로그인 한 회원은 자신이 작성한 글 목록을 볼 수 있습니다.

#### 4️⃣ 댓글

- 댓글은 회원만이 권한을 가집니다.
- 글의 길이는 총 100자 이상을 넘을 수 없습니다.
- 회원은 다수의 댓글을 달 수 있습니다.

#### 5️⃣ 좋아요

- 좋아요는 회원만 권한을 가집니다.
- 좋아요 기능은 취소가 가능합니다.

#### 6️⃣ 알림

- 알림은 회원이 자신이 쓴 글에 대해 다른회원의 댓글을 올리거나 좋아요시 받는 기능입니다.
- 알림 목록에서 자신이 쓴 글에 달린 댓글과 좋아요를 확인할 수 있습니다.
