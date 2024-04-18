# 피드백

## ERD

- 현재 ERD 상태라면 비회원은 장바구니를 사용하지 못하게 되는 구조입니다.
- 데이터베이스에 테이블이 2개씩 있네요..? Products, products
  - 필드들은 조금씩 다른??
  - 사용하는 테이블을 가지고 ERD를 제출했으면 좋았을 것 같습니다.


## DbConnectionThreadLocal

- SonarLint를 설치하셨다면 주석으로 체크한 부분에서 어떤 경고가 뜨는지 한 번 보시기 바랍니다.
  - SonarLint를 잘 활용하세요.

```java
public static void reset() {
    Connection connection = connectionThreadLocal.get();
    try {
        if (getSqlError()) {
            connection.rollback();
        } else {
            connection.commit();
        }
        if (connection != null) {  // <- HERE
            connection.close();
        }
        connectionThreadLocal.remove();
    } catch (SQLException e) {
        log.error("Error resetting database connection: {}", e.getMessage());
        throw new RuntimeException("DbConnectionThreadLocal reset error!");
    }
}
```


## UserRepositoryImpl 

- `PrepareStatement`와 `ResultSet` 자원을 사용하고 잘 `close()` 해주셨습니다.
- `close()` 호출 전 null 체크까지 한 것은 좋습니다.
- `ResultSet`도 `try-with-resources`를 사용하시면 더 편합니다.


## 로그아웃

- 로그아웃을 현재 HTTP Method `GET`을 사용해서 구현했습니다. `GET` vs `POST` 차이점에 대해 공부해 보시기 바랍니다.


## FrontServlet

- FrontServlet에서 Exception을 캐치해서 처리하는 로직을 보면
  - 로깅
  - ThreadLocal 에서 true 설정
  - 응답 400 설정
  - 응답 500 설정
- 여기서 응답을 400, 500 두 개를 설정한 이유가 있을까요?? HTTP Status 4xx, 5xx 값들이 무엇을 의미하는지 한 번 공부해 보시기 바랍니다.
  - 발생하는 Exception 종류에 따라 처리해줘야할 응답 코드들은 반드시 다를 것 입니다.!

```java
@Override
protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
        // ...
    } catch (Exception e) {
        log.error("error:{}", e);
        DbConnectionThreadLocal.setSqlError(true);
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
    } finally {
        DbConnectionThreadLocal.reset();
    }
}
```

## Filter

- 필터에서는 항상 `doFilter()`를 호출해 주시기 바랍니다.
- 로그인을 하지 않고 [http://localhost:8080/admin/admin_home.do](http://localhost:8080/admin/admin_home.do)에 접근하면 아무런 화면이 뜨지 않습니다. 
- 마이 페이지 필터는 사용하지 않고 있습니다. 왜 이 필터를 사용하지 않고 JavaScript를 사용해서 처리했는지 모르겠습니다...
  - 로그인을 하지 않았다면 마이페이지에 빈 값을 보여주는 화면을 보여주는 것이 아니라 아예 접근 자체가 안되어야 하는게 더 올바를 것 같습니다.

## 회원가입

- 아이디 입력이 안됩니다. input에 `readonly`는 풀어주세요.


## 장바구니 

- 장바구니에 접근 할 링크가 보이지 않습니다...
- 장바구니 수량이 DB에 반영되지 않고 항상 1개로 고정입니다.


## 메인 페이지

- index 페이지에서 카테고리 별 상품들을 보여주는 것을 구현했습니다. 근데 url을 보니 `http://localhost:8080/index.do?index=4&page=1` 이런 식으로 표시가 됩니다. 
- `index=4`는 크리스마스, `index=3`은 식품. 카테고리 번호를 `index`로 표시하기엔 오해의 소지가 많을 것같습니다. 
- `category-id=1` or `category=1` 이런식으로 하면 카테고리 별로 조회할 url이라는게 눈에 더 띕니다.

## 주소

- 동일한 주소가 중복으로 들어갑니다. ERD 설계가 잘못된 것 같습니다.


## 상품

- 요구사항에 상품 이미지가 없으면 `no-image`를 보여주라고 되어있습니다. 이게 데이터베이스에 `no-image` 경로를 넣어둘 수도 있지만 다른 방법도 한 번 생각해 보면 좋을것 같습니다.
- 상품 이미지 저장 경로를 지금 절대 경로로 하고 있습니다. 상대 경로로 할 수 있는 방법을 생각해 보세요.


## 공통

- 값을 확인하시기 위해 로그를 찍으셨는데, 이때 레벨이 `debug`인 것은 좋은 것 같습니다.
- Service, Repository 계층에 대한 테스트 코드가 없습니다.
  - 시간을 내서 한 번씩 꼭 작성해 보세요.
- Session에 넣는 값들을 상수로 관리하면 좋습니다.
  - 페이지 크기 같은 값들도 위와 같습니다.

수고하셨습니다. 👏👏