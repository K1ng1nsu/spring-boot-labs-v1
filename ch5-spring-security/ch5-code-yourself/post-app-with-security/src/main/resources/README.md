## 게시판 API – Spring Security와 JWT를 이용한 인증/인가 기능 추가 실습

이 실습 목표는 기존 게시판 애플리케이션에 Spring Security와 JWT(JSON Web Token)를 적용하여 안전한 인증 및 인가 시스템을 구축하는 것입니다.
사용자(User) 엔티티 모델을 새로 도입하고, 각 API 엔드포인트에 적절한 접근 제어 규칙을 설정합니다.

### 1. `User` 모델 및 연관 관계 설정

기존 게시글(`Post`), 댓글(`Comment`) 기능에 사용자 개념을 도입합니다.

#### 1.1 `User` 엔티티 구현

새로운 `User` 엔티티를 생성하고, 게시글 및 댓글과의 연관 관계를 설정하세요.

#### 1.2 `Post`, `Comment` 엔티티 수정

`Post`와 `Comment` 엔티티의 기존 `author` 필드(String)를 `User` 엔티티와의 연관 관계로 변경합니다.

*   **`Post` 엔티티 수정:**
    - `author` 필드를 삭제하고, `user` 필드를 추가하여 `User`와 다대일(N:1) 관계를 맺습니다. (`@ManyToOne`)
*   **`Comment` 엔티티 수정:**
    - `author` 필드를 삭제하고, `user` 필드를 추가하여 `User`와 다대일(N:1) 관계를 맺습니다. (`@ManyToOne`)

---

### 2. 사용자 인증 API 구현

회원가입과 로그인을 처리하는 API를 구현합니다.

#### 2.1 회원가입

*   `POST /auth/signup`
*   **요청 본문 예시:**
    ```json
    {
      "username": "user123",
      "password": "password123!",
      "role": "USER"
    }
    ```

#### 2.2 로그인

*   `POST /auth/login`
*   **요청 본문 예시:**
    ```json
    {
      "username": "user123",
      "password": "password123!"
    }

---

### 3. 기존 API에 인증/인가 적용

인증된 사용자를 기반으로 기존 게시글/댓글 API를 수정합니다.

#### 3.1 게시글 API 수정

*   **게시글 작성 (`POST /posts`):**
    - **인증 필수:** `Authorization` 헤더에 유효한 JWT가 필요합니다.
*   **게시글 수정 (`PUT /posts/{postId}`):**
    - **인증/인가 필수:** 오직 게시글 작성자 본인 또는 `ADMIN` 권한을 가진 사용자만 삭제할 수 있습니다.
*   **게시글 삭제 (`DELETE /posts/{postId}`):**
    - **인증/인가 필수:** 오직 게시글 작성자 본인 또는 `ADMIN` 권한을 가진 사용자만 삭제할 수 있습니다.

#### 3.2 댓글 API 수정

*   **댓글 작성 (`POST /posts/{postId}/comments`):**
    - **인증 필수:** `Authorization` 헤더에 유효한 JWT가 필요합니다.
*   **댓글 수정 (`PUT /comments/{commentId}`):**
    - **인증/인가 필수:** 오직 댓글 작성자 본인 또는 `ADMIN` 권한을 가진 사용자만 수정할 수 있습니다.
*   **댓글 삭제 (`DELETE /comments/{commentId}`):**
    - **인증/인가 필수:** 오직 댓글 작성자 본인 또는 `ADMIN` 권한을 가진 사용자만 삭제할 수 있습니다.

---