### 숫자 자리수 합 계산기 API 

- **GET** `/sum-digits?number=1234`
- 사용자로부터 `number` 입력값을 받아
- 매 자리의 숫자들의 합을 계산하고, `ResponseEntity`를 통해 HTTP 상태코드를 가진 응답을 함

#### number 정상 입력 시 `200 OK`

```json
{
  "message": "각 자리수 합: 10"
}
```

#### number 누락시 `400 Bad Request`

```json
{
  "error": "number 파라미터는 필수입니다."
}
```

#### 음수 입력 시 `400 Bad Request`

```json
{
  "error": "음수는 지원하지 않습니다. 양의 정수를 입력해주세요."
}
```

#### 정수 아닌 값 입력 시 `422 Unprocessable Entity`

```json
{
  "error": "정수만 입력 가능합니다. 예: /sum-digits?number=1234"
}
```
