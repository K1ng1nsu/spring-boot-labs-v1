## 독서 리뷰 게시판 API – 댓글 기능 추가 및 통합 조회 실습

### 1. 댓글 모델 구현

다음 요구사항에 맞는 댓글(Comment) 모델을 구현하세요:

| 필드          | 타입      | 설명                    |
|-------------|---------|-----------------------|
| `id`        | Long    | 댓글 ID (PK)           |
| `content`   | String  | 댓글 내용                 |
| `author`    | String  | 댓글 작성자                |
| `reviewId`  | Long    | 연결된 리뷰 ID (FK)       |
| `createdAt` | LocalDateTime | 작성 시간             |
| `updatedAt` | LocalDateTime | 수정 시간             |

---

### 2. 댓글 CRUD API 구현

#### 2.1 댓글 생성

* **POST** `/reviews/{reviewId}/comments`
* **요청 본문**:

```json
{
  "content": "정말 좋은 리뷰네요! 저도 이 책 읽어봐야겠어요.",
  "author": "bookfan"
}
```

* **응답 예시** (201 Created):

```json
{
  "id": 1,
  "content": "정말 좋은 리뷰네요! 저도 이 책 읽어봐야겠어요.",
  "author": "bookfan",
  "reviewId": 12,
  "createdAt": "2023-07-15T14:30:00"
}
```

#### 2.2 댓글 수정

* **PUT** `/comments/{commentId}`
* **요청 본문**:

```json
{
  "content": "수정된 댓글입니다."
}
```

* **응답 예시** (200 OK):

```json
{
  "id": 1,
  "content": "수정된 댓글입니다.",
  "author": "bookfan",
  "reviewId": 12,
  "createdAt": "2023-07-15T14:30:00",
  "updatedAt": "2023-07-15T15:45:00"
}
```

#### 2.3 댓글 삭제

* **DELETE** `/comments/{commentId}`
* **응답**: 204 No Content

#### 2.4 특정 리뷰의 모든 댓글 조회

* **GET** `/reviews/{reviewId}/comments`
* **Query Parameters**:

| 파라미터           | 설명                       |
|----------------|--------------------------|
| `sort`         | 정렬 기준 (`createdAt,desc`) |
| `page`, `size` | 페이징 처리                   |

* **응답 예시**:

```json
{
  "content": [
    {
      "id": 3,
      "content": "이 책 저도 좋아해요!",
      "author": "reader123",
      "reviewId": 12,
      "createdAt": "2023-07-16T10:15:00"
    },
    {
      "id": 1,
      "content": "정말 좋은 리뷰네요! 저도 이 책 읽어봐야겠어요.",
      "author": "bookfan",
      "reviewId": 12,
      "createdAt": "2023-07-15T14:30:00"
    }
  ],
  "totalElements": 2,
  "totalPages": 1,
  "size": 10,
  "page": 0
}
```

---

### 3. 리뷰 조회 시 댓글 통합 조회 기능 구현

#### 단일 리뷰 조회 시 댓글 포함

* **GET** `/reviews/{reviewId}?includeComments=true`
* **Query Parameters**:

| 파라미터             | 설명                                   |
|------------------|--------------------------------------|
| `includeComments` | 댓글 포함 여부 (기본값: false)             |
| `commentPage`     | 댓글 페이지 (기본값: 0)                    |
| `commentSize`     | 페이지당 댓글 수 (기본값: 5)                |

* **응답 예시**:

```json
{
  "id": 12,
  "title": "마법 세계의 환상 리뷰",
  "content": "정말 놀라운 세계관이었어요.",
  "author": "magicfan",
  "bookTitle": "마법사의 돌",
  "bookAuthor": "J.K.Rowling",
  "rating": 5,
  "createdAt": "2023-07-10T09:30:00",
  "comments": {
    "content": [
      {
        "id": 3,
        "content": "이 책 저도 좋아해요!",
        "author": "reader123",
        "createdAt": "2023-07-16T10:15:00"
      },
      {
        "id": 1,
        "content": "정말 좋은 리뷰네요! 저도 이 책 읽어봐야겠어요.",
        "author": "bookfan",
        "createdAt": "2023-07-15T14:30:00"
      }
    ],
    "totalElements": 2,
    "totalPages": 1,
    "size": 5,
    "page": 0
  }
}
```
