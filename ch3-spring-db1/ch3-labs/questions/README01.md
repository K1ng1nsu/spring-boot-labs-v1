## Todo list API - CRUD

- Todo list API 서버를 MyBatis를 활용해 만들어 보세요.
- 이전 labs에서 작업하던 Todo list에서 DB 연동 방법을 변경하면 됩니다. 

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
