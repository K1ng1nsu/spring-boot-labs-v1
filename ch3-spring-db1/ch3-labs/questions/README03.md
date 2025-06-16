## Todo list API - Subtask 기능 확장 실습 과제

* MyBatis 기반의 기존 Todo list API를 확장하여 **하위 할 일(Subtask)** 기능을 구현하세요.
* `parentId` 필드를 활용해 **계층형 할 일 구조**를 저장하고 조회할 수 있도록 합니다.

---

### 1. 하위 할 일 추가 (Create Subtask)

* **POST** `/todos/{parentId}/subtasks`
* 요청 바디:

```json
{
  "title": "JPA 강의 노트 정리하기",
  "completed": false
}
```

* 응답: `201 Created`

```json
{
  "id": 3,
  "title": "JPA 강의 노트 정리하기",
  "completed": false,
  "parentId": 1
}
```

---

### 2. 특정 할 일의 하위 할 일 목록 조회 (Read Subtasks)

* **GET** `/todos/{parentId}/subtasks`
* 응답: `200 OK`

```json
[
  { "id": 3, "title": "JPA 강의 노트 정리하기", "completed": false, "parentId": 1 },
  { "id": 4, "title": "섹션 1 다시 듣기", "completed": true, "parentId": 1 }
]
```

---

### 3. 하위 할 일 수정 (Update Subtask)

* **PUT** `/todos/{id}`
* 요청 바디:

```json
{
  "title": "JPA 강의 노트 완성하기",
  "completed": true
}
```

* 응답: `200 OK`

```json
{
  "id": 3,
  "title": "JPA 강의 노트 완성하기",
  "completed": true,
  "parentId": 1
}
```

---

### 4. 하위 할 일 삭제 (Delete Subtask)

* **DELETE** `/todos/{id}`
* 응답: `204 No Content`

---

## 힌트

* DB 스키마에 `parent_id` 컬럼을 추가해야 합니다.
* 최상위 할 일은 `parent_id = NULL`로 저장됩니다.
* MyBatis 매퍼에서 `parent_id`로 하위 할 일 조건을 걸어 조회합니다.
