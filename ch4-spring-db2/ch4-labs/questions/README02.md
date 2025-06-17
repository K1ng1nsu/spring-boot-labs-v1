## Todo list API - 검색 기능 확장 실습 과제

* 기존 Spring Data JPA 기반 Todo API에 **검색 기능**을 추가해보세요.
* 특정 키워드를 포함한 할 일 제목(title)을 조회할 수 있어야 합니다.

---

### 1. 제목으로 할 일 검색 (Search by Title)

* **GET** `/todos?keyword=공부`
* 응답: `200 OK`

```json
[
  { "id": 1, "title": "스프링부트 공부하기", "completed": false },
  { "id": 5, "title": "DB 공부 정리하기", "completed": true }
]
```

---

### 2. 검색 조건이 없을 경우 전체 목록 반환

* **GET** `/todos`
* 응답: `200 OK` (전체 할 일 목록과 동일)

```json
[
  { "id": 1, "title": "스프링부트 공부하기", "completed": false },
  { "id": 2, "title": "리액트 수업 듣기", "completed": true },
  { "id": 5, "title": "DB 공부 정리하기", "completed": true }
]
```