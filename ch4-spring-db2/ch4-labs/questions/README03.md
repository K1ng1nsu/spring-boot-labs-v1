## 독서 리뷰 게시판 API – 고급 검색 기능 실습 (QueryDSL 확장)

### 공통 요청 형식

* **GET** `/reviews`
* **Query Parameters**:

| 파라미터                | 설명                       |
| ------------------- | ------------------------ |
| `author`            | 리뷰 작성자 (정확 일치)           |
| `bookTitle`         | 책 제목 (정확 일치)             |
| `bookTitleContains` | 책 제목 키워드 포함 검색           |
| `bookAuthor`        | 책 저자 (정확 일치)             |
| `titleContains`     | 리뷰 제목 키워드 포함 검색          |
| `contentContains`   | 리뷰 본문 키워드 포함 검색          |
| `rating`            | 정확 평점 (1~5)             |
| `minRating`         | 최소 평점                    |
| `maxRating`         | 최대 평점                    |
| `sort`              | 정렬 기준 (`createdAt,desc`) |
| `page`, `size`      | 페이징 처리                   |

---

### 1. 책 제목 키워드 포함 + 평점 범위 + 최신순 정렬

```
GET /reviews?bookTitleContains=마법&minRating=3&maxRating=5&sort=createdAt,desc&page=0&size=10
```

#### 응답 예시

```json
{
  "content": [
    {
      "id": 12,
      "title": "마법 세계의 환상 리뷰",
      "content": "정말 놀라운 세계관이었어요.",
      "author": "magicfan",
      "bookTitle": "마법사의 돌",
      "bookAuthor": "J.K.Rowling",
      "rating": 5
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 10,
  "page": 0
}
```

---

### 2. 리뷰 제목 키워드 포함 + 평점 내림차순 정렬

```
GET /reviews?titleContains=후기&sort=rating,desc&page=0&size=5
```

#### 응답 예시

```json
{
  "content": [
    {
      "id": 8,
      "title": "환상적인 모험 후기",
      "content": "추천합니다!",
      "author": "booklover",
      "bookTitle": "엘프의 여정",
      "bookAuthor": "Tolkien",
      "rating": 5
    },
    {
      "id": 5,
      "title": "평범한 이야기 후기",
      "content": "그럭저럭 볼만했어요.",
      "author": "reader1",
      "bookTitle": "일상의 기록",
      "bookAuthor": "Kim",
      "rating": 3
    }
  ],
  ...
}
```

---

### 3. 리뷰 작성자 + 책 저자 필터링

```
GET /reviews?author=yun&bookAuthor=J.K.Rowling&page=0&size=10
```

#### 응답 예시

```json
{
  "content": [
    {
      "id": 4,
      "title": "다시 읽어도 재미있는 책",
      "content": "어릴 적 감동이 다시 떠오릅니다.",
      "author": "yun",
      "bookTitle": "해리포터와 비밀의 방",
      "bookAuthor": "J.K.Rowling",
      "rating": 5
    }
  ],
  ...
}
```

---

### 4. 리뷰 본문 키워드 + 평점 정확히 일치 + 작성일 최신순

```
GET /reviews?contentContains=재미있&rating=5&sort=createdAt,desc&page=0&size=10
```

#### 응답 예시

```json
{
  "content": [
    {
      "id": 21,
      "title": "재미있고 흥미로운 이야기",
      "content": "정말 재미있었어요!",
      "author": "joyreader",
      "bookTitle": "판타지의 왕국",
      "bookAuthor": "Min Lee",
      "rating": 5
    }
  ],
  ...
}
```

---

### 5. 책 제목 정확 일치 + 작성자 정확 일치

```
GET /reviews?bookTitle=해리포터와불의잔&author=wizard99&page=0&size=10
```

#### 응답 예시

```json
{
  "content": [
    {
      "id": 7,
      "title": "불의 잔 최고!",
      "content": "역대급 재미!",
      "author": "wizard99",
      "bookTitle": "해리포터와불의잔",
      "bookAuthor": "J.K.Rowling",
      "rating": 5
    }
  ],
  ...
}
```

---

### 6. 리뷰 제목 또는 본문에 키워드 포함 (OR 조건)

```
GET /reviews?titleContains=환상&contentContains=마법&page=0&size=10
```

#### 응답 예시

```json
{
  "content": [
    {
      "id": 9,
      "title": "환상 그 자체",
      "content": "마법과 환상의 조화!",
      "author": "dreamer",
      "bookTitle": "꿈꾸는 자의 서",
      "bookAuthor": "R. Moon",
      "rating": 4
    }
  ],
  ...
}
```

---

### 7. 책 저자 + 평점 오름차순 정렬

```
GET /reviews?bookAuthor=Tolkien&sort=rating,asc&page=0&size=10
```

#### 응답 예시

```json
{
  "content": [
    {
      "id": 3,
      "title": "중간계 입문",
      "content": "조금 어렵지만 재미있음.",
      "author": "novice",
      "bookTitle": "반지의 제왕",
      "bookAuthor": "Tolkien",
      "rating": 3
    },
    {
      "id": 6,
      "title": "엘프의 지혜",
      "content": "정말 멋진 세계!",
      "author": "elflover",
      "bookTitle": "실마릴리온",
      "bookAuthor": "Tolkien",
      "rating": 5
    }
  ],
  ...
}
```

---

### 8. 작성자 + 평점 범위 + 제목 키워드

```
GET /reviews?author=reader123&minRating=2&maxRating=4&titleContains=후기&page=0&size=10
```

#### 응답 예시

```json
{
  "content": [
    {
      "id": 14,
      "title": "적당한 후기",
      "content": "기대보단 아쉬움이...",
      "author": "reader123",
      "bookTitle": "마법과 현실",
      "bookAuthor": "Sun Kim",
      "rating": 3
    }
  ],
  ...
}
```
