### 가장 길이가 긴 단어 찾기 API

* **POST** `/longest-word`
* 입력은 HTTP body (JSON) 로 보낼 것

#### 요청 바디

```json
{
  "words": ["apple", "banana", "kiwi"]
}
```

```json
{
  "message": "가장 길게는 단어는 'banana'입니다."
}
```

#### 상태 코드:

* 입력 범위가 빈가 같은 경우 `400 Bad Request`
* 필요 필드 누르기 경우 `400 Bad Request`
* 단어 내역이 무한 경우 `422 Unprocessable Entity`