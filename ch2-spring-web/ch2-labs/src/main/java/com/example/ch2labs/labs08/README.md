## 예약 시간 검증 API

* **POST** `/reserve`
* 입력은 HTTP body (JSON) 로 보낼 것


### 요청 바디 예시

```json
{
  "userId": "yun123",
  "startTime": "2025-06-13T14:00",
  "endTime": "2025-06-13T13:30"
}
```

### 정상 응답 예시

```json
{
  "message": "yun123님의 예약이 완료되었습니다."
}
```

### 실패 응답 예시

```json
{
  "errors": {
    "time": "시작 시간은 종료 시간보다 이전이어야 합니다."
  }
}
```

### 검증 조건

- userId: 필수, 영문 소문자와 숫자로 구성, 5~12자 사이
- startTime: 필수
- endTime: 필수
- startTime은 endTime보다 이전이어야 함 (시간 순서 검증)


## 힌트

* DTO에 필드별 유효성 검증 추가
* 커스텀 애너테이션 `@ValidTimeRange` 만들기
  → `ConstraintValidator<ValidTimeRange, ReservationRequest>` 형태로 DTO 전체를 전달받아 `startTime`과 `endTime`을 함께 비교
* 전역 예외 처리로 JSON 응답 구성
* 시간은 자바 내장 LocalDateTime 클래스 사용