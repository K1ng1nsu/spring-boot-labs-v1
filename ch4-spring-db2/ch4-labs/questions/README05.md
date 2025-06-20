## 독서 리뷰 게시판 API – 대댓글 기능 추가 실습

### 1. 대댓글 모델 구현

기존 댓글(Comment) 모델을 확장하여 대댓글 기능을 지원하도록 다음 필드를 추가하세요:

| 추가 필드      | 타입   | 설명                           |
|------------|------|------------------------------|
| `parentId` | Long | 부모 댓글 ID (대댓글인 경우, null이면 일반 댓글) |

### 수정된 Comment 모델 전체 구조:

| 필드          | 타입            | 설명                    |
|-------------|---------------|-----------------------|
| `id`        | Long          | 댓글 ID (PK)           |
| `content`   | String        | 댓글 내용                 |
| `author`    | String        | 댓글 작성자                |
| `reviewId`  | Long          | 연결된 리뷰 ID (FK)       |
| `parentId`  | Long          | 부모 댓글 ID (대댓글용, null 가능) |
| `createdAt` | LocalDateTime | 작성 시간                 |
| `updatedAt` | LocalDateTime | 수정 시간                 |

---

## 2. 대댓글 CRUD API 구현

### 2.1 대댓글 생성

* **POST** `/reviews/{reviewId}/comments`
* **요청 본문** (대댓글):

```json
{
  "content": "저도 같은 생각이에요! 특히 마지막 장면이 인상적이었습니다.",
  "author": "bookworm",
  "parentId": 1
}
```

* **응답 예시** (201 Created):

```json
{
  "id": 4,
  "content": "저도 같은 생각이에요! 특히 마지막 장면이 인상적이었습니다.",
  "author": "bookworm",
  "reviewId": 12,
  "parentId": 1,
  "createdAt": "2023-07-16T16:20:00"
}
```

### 2.2 대댓글 수정

* **PUT** `/comments/{commentId}`
* **요청 본문**:

```json
{
  "content": "수정된 대댓글입니다. 더 자세한 감상을 추가해요."
}
```

* **응답 예시** (200 OK):

```json
{
  "id": 4,
  "content": "수정된 대댓글입니다. 더 자세한 감상을 추가해요.",
  "author": "bookworm",
  "reviewId": 12,
  "parentId": 1,
  "createdAt": "2023-07-16T16:20:00",
  "updatedAt": "2023-07-16T17:30:00"
}
```

### 2.3 대댓글 삭제

* **DELETE** `/comments/{commentId}`
* **응답**: 204 No Content
* **참고**: 부모 댓글 삭제 시 하위 대댓글 처리 정책을 정의하세요 (cascade 삭제 또는 orphan 처리)

---

## 3. 댓글 및 대댓글 통합 조회 API

### 특정 리뷰의 모든 댓글 조회 (대댓글 포함)

* **GET** `/reviews/{reviewId}/comments`
* **Query Parameters**:

| 파라미터           | 설명                           |
|----------------|------------------------------|
| `sort`         | 정렬 기준 (`createdAt,desc`)    |
| `page`, `size` | 페이징 처리 (부모 댓글 기준)        |

* **응답 예시** :

```json
{
  "content": [
    {
      "id": 3,
      "content": "이 책 저도 좋아해요!",
      "author": "reader123",
      "reviewId": 12,
      "parentId": null,
      "createdAt": "2023-07-16T10:15:00",
      "replies": [
        {
          "id": 5,
          "content": "어떤 부분이 가장 좋았나요?",
          "author": "curious_reader",
          "reviewId": 12,
          "parentId": 3,
          "createdAt": "2023-07-16T11:30:00"
        }
      ]
    },
    {
      "id": 1,
      "content": "정말 좋은 리뷰네요! 저도 이 책 읽어봐야겠어요.",
      "author": "bookfan",
      "reviewId": 12,
      "parentId": null,
      "createdAt": "2023-07-15T14:30:00",
      "replies": [
        {
          "id": 4,
          "content": "저도 같은 생각이에요! 특히 마지막 장면이 인상적이었습니다.",
          "author": "bookworm",
          "reviewId": 12,
          "parentId": 1,
          "createdAt": "2023-07-16T16:20:00"
        }
      ]
    }
  ],
  "totalElements": 2,
  "totalPages": 1,
  "size": 10,
  "page": 0
}
```
