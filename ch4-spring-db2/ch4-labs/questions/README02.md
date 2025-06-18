## 독서 리뷰 게시판 API – 검색 기능 확장 실습

- 지금까지 구현한 리뷰 게시판에 검색 조건과 페이징 기능능을 추가해보세요.

### 공통 요청 형식

* **GET** `/reviews`
* **Query Parameters**:

    * `bookTitle`: 도서 제목 키워드
    * `author`: 리뷰 작성자
    * `rating`: 정확한 평점
    * `minRating`, `maxRating`: 평점 범위
    * `page`, `size`: 페이징 처리

---

### 1. 도서 제목 키워드 검색 + 페이징

* `GET /reviews?bookTitle=해리포터&page=0&size=10`
* 예: `"해리포터"`가 제목에 포함된 도서 리뷰 검색

---

### 2. 작성자 + 평점 필터링

* `GET /reviews?author=booklover99&rating=5&page=0&size=10`

---

### 3. 평점 범위 검색

* `GET /reviews?minRating=3&maxRating=5&page=0&size=10`

---

### 4. 통합 검색 API 설계 예시

* **GET** `/reviews`
* 모든 조건은 QueryParam으로 조합:

  ```
  /reviews?author=yun&keyword=마법&minRating=3&page=0&size=10
  ```

#### 📦 `ReviewSearchRequest` DTO 예시

```java
public class ReviewSearchRequest {
    private String author;
    private String bookTitle;
    private String keyword;
    private Integer rating;
    private Integer minRating;
    private Integer maxRating;
    private int page = 0;
    private int size = 10;
}
```
---

### 5. 최종 Controller 예시

```java
@GetMapping("/reviews")
public Page<ReviewResponse> searchReviews(@ModelAttribute ReviewSearchRequest request) {
    return reviewService.search(request);
}
```