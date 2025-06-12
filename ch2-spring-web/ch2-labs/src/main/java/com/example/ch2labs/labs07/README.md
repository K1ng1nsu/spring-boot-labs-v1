# TODO 리스트 API - CRUD

### 1. 할 일 작성 (Create)

* **POST** `/todos`
* 요청 바디:

```json
{
  "title": "스프링부트 공부하기",
  "completed": false
}
```

* 응답: `201 Created`

```json
{
  "id": 1,
  "title": "스프링부트 공부하기",
  "completed": false
}
```

---

### 2. 할 일 전체 목록 조회 (Read)

* **GET** `/todos`
* 응답: `200 OK`

```json
[
  { "id": 1, "title": "스프링부트 공부하기", "completed": false },
  { "id": 2, "title": "리액트 수업 듣기", "completed": true }
]
```

---

### 3. 할 일 수정 (Update)

* **PUT** `/todos/{id}`
* 요청 바디:

```json
{
  "title": "Java 프로젝트 완성하기",
  "completed": true
}
```

* 응답: `200 OK`

```json
{
  "id": 1,
  "title": "Java 프로젝트 완성하기",
  "completed": true
}
```

---

### 4. 할 일 삭제 (Delete)

* **DELETE** `/todos/{id}`
* 응답: `204 No Content`

---

### 예외 처리

* title 및 completed 누락 시: `400 Bad Request`
* 없는 ID에 대한 조회/수정/삭제 시: `404 Not Found`

---

### 3티어 구조와 DTO 사용으로 구현해보기

* Controller
* Service
* Repository
* Request DTO (`TodoRequest`)
* Response DTO (`TodoResponse`)
* Model Entity (`Todo`)
