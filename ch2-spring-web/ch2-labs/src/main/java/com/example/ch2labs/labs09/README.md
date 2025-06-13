## 외부 TODO 조회 API

* **GET** `/external-todos/{id}`
* 외부 API로부터 TODO 데이터를 받아 그대로 반환

### 요청 예시

```
GET /external-todos/3
```

### 정상 응답 예시

```json
{
  "userId": 1,
  "id": 3,
  "title": "fugiat veniam minus",
  "completed": false
}
```

### 실패 응답 예시

```json
{
  "error": "해당 ID의 할 일이 존재하지 않습니다."
}
```


### 구현 조건

* 외부 API: `https://jsonplaceholder.typicode.com/todos/{id}`
* `WebClient`를 사용하여 외부 API에 GET 요청을 보낼 것
* 외부 응답 데이터를 `Todo` 객체로 파싱하여 클라이언트에게 그대로 응답
* 존재하지 않는 ID일 경우(404) 적절한 에러 메시지를 JSON으로 반환
