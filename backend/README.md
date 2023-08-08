# 컨벤션

tags: 설계
상태: In progress

# Database 네이밍

- 이름 : _name
- 일시 : created_at
- 일자 : created_date
- 삭제일시 : deleted_at
- 확인일시 : confirmed_at
- 업데이트 일시 : updated_at
- 여부확인 : is_
    - 확인여부 : is_confirmed
    - 삭제여부 : is_deleted
    - 승인여부 : is_accepted
    - __장 : is_leader
- ___수 : _count
- 내용(소개, 등) : content
- 일시 : 시간까지 : _time
- 일자 : 일까지만 : _date
- ~장 : is_leader
- 게시판 개념에서
    - 제목 : title
    - 내용 : content

# Backend

- 패키지

**소문자로 작성하고 단어의 구분을 위해 ‘_’ 및 대문자를 혼용하지 않는다**

```java
// 좋은 예
package com.navercorp.apiGateway
package com.navercorp-api_gateway

// 나쁜 예
package com.navercorp.apigateway

```

- 클래스

**Camel Case를 사용 (합쳐지는 단어의 첫 글자를 대문자로 표기)**

```java
// 좋은 예
public class Reservation
public class AccessToken

// 나쁜 예
public class reservation
public class Accesstoken
```

- 메소드

**메소드 명은 기본적으로 동사로 시작하며 다른 타입으로 변환하는 메소드는 전치사로 시작**

```java
동사사용 : renderHtml()
변환메소드의 전치사 : toString()
```

- 상수

**대문자로 작성하며 합성하는 ‘_’를 사용하여 단어를 구분**

```java
public final int UNLIMITED = -1;
public final String POSTAL_CODE_EXPRESSION = "POST";
```

- 변수

**임시 변수 외에는 1글자 변수 명 사용 X, 이해하기 쉬운 변수 명으로 작성**

```java
// 좋은 예
HtmlParser parser = new HtmlParser();

// 나쁜 예
HtmlParser p = new HtmlParser();
```

- class import

**클래스 import시에는 와일드카드(*) 없이 필요한 클래스 명을 명시적으로 작성** 

```java
// 좋은 예
import java.util.List;

// 나쁜 예
import java.util.*;  // 성능에 영향을 줄 수 있다.
```

- 변수 선언

**변수 선언문은 한 문장에서 하나의 변수만을 다루며 주석 사용을 위해 한 줄에 하나씩 선언하는 것을 권장**

```java
// 좋은 예
int level; // indetation level
int size; // size of table

// 나쁜 예
int level, size;
```

- 들여쓰기
    - 탭을 사용하여 들여쓴다 (스페이스 사용 x)
    - 1개의 탭 사이즈는 4개의 스페이스와 같도록 에디터 설정
    - 클래스, 메소드, 제어문 등의 block이 생길 때마다 1단계 들여쓰기

- 기본적으로 Camel케이스 사용

```java
- 변수명 : `firstName`, `userAge`, `productPrice`
- 함수명: `calculateTotalAmount`, `validateUserInput`, `generateRandomNumber`
- 클래스명: `UserService`, `ProductController`, `CustomerModel`
```