## 독서 리뷰 게시판 API - CRUD 실습

* Spring Data JPA를 활용해 **도서 리뷰 게시판 API**를 만들어 보세요.
* 사용자는 다양한 책에 대한 리뷰를 남기고, 수정/조회/삭제할 수 있습니다.

---

### 엔티티: `Review`

| 필드명          | 설명           |
| ------------ | ------------ |
| `id`         | 리뷰 식별자 (PK)  |
| `title`      | 리뷰 제목        |
| `content`    | 리뷰 본문        |
| `author`     | 리뷰 작성자       |
| `bookTitle`  | 리뷰 대상 책 제목   |
| `bookAuthor` | 책 저자         |
| `rating`     | 평점 (1\~5 정수) |

---

### 1. 리뷰 작성 (Create)

* **POST** `/reviews`
* 요청 바디 예시 (해리포터 시리즈):

```json
{
  "title": "호그와트에 빠지다",
  "content": "해리포터 1권은 판타지 입문자에게 최고의 책이에요!",
  "author": "booklover99",
  "bookTitle": "해리포터와 마법사의 돌",
  "bookAuthor": "J.K. 롤링",
  "rating": 5
}
```

* 응답: `201 Created`

---

### 2. 전체 리뷰 목록 조회 (Read)

* **GET** `/reviews`
* 응답 예시:

```json
[
  {
    "id": 1,
    "title": "호그와트에 빠지다",
    "content": "해리포터 1권은 판타지 입문자에게 최고의 책이에요!",
    "author": "booklover99",
    "bookTitle": "해리포터와 마법사의 돌",
    "bookAuthor": "J.K. 롤링",
    "rating": 5
  },
  {
    "id": 2,
    "title": "더 어두워진 이야기",
    "content": "비밀의 방은 좀 더 긴장감 넘쳐요.",
    "author": "magicfan",
    "bookTitle": "해리포터와 비밀의 방",
    "bookAuthor": "J.K. 롤링",
    "rating": 4
  }
]
```

---

### 3. 리뷰 수정 (Update)

* **PUT** `/reviews/{id}`
* 요청 바디:

```json
{
  "title": "마법사의 돌 재독 후기",
  "content": "다시 읽어도 감동적인 이야기!",
  "author": "booklover99",
  "bookTitle": "해리포터와 마법사의 돌",
  "bookAuthor": "J.K. 롤링",
  "rating": 5
}
```

* 응답: `200 OK`

```json
{
  "id": 1,
  "title": "마법사의 돌 재독 후기",
  "content": "다시 읽어도 감동적인 이야기!",
  "author": "booklover99",
  "bookTitle": "해리포터와 마법사의 돌",
  "bookAuthor": "J.K. 롤링",
  "rating": 5
}
```
---

### 4. 리뷰 삭제 (Delete)

* **DELETE** `/reviews/{id}`
* 응답: `204 No Content`