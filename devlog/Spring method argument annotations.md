# Spring method argument annotations

**@RequestParam**

- request parameter를 가져오는 것을 표시해준다.
- method argument 사용된다.
- Url과 함께 뒤에 붙는 query parameter(ex. ?name=jian)를 파싱해준다.
- HTTP GET 요청에 대해 매칭되는 request parameter 값이 자동으로 들어간다.

**@RequestPart**
- Request로 전달된 MultipartFile을 바인딩해준다.


**@RequestBody**
- 요청이 온 데이터(JSON이나 XML형식)를 바로 Class나 model로 매핑하기 위한 Annotation이다.
- POST나 PUT, PATCH로 요청을 받을때에, 요청에서 넘어온 body 값들을 자바 타입으로 파싱해준다.
- HTTP POST 요청에 대해 request body에 있는 request message에서 값을 얻어와 매핑한다.
- RequestData를 바로 Model이나 클래스로 매핑한다.
- JSON 이나 XML같은 데이터를 적절한 messageConverter로 읽을 때 사용하거나 POJO 형태의 데이터 전체로 받는 경우에 사용한다.
- @RequestBody를 사용할 객체는 필드를 바인딩할 생성자나 setter 메서드가 필요없다.
- 직렬화를 위해 기본 생성자가 필수로 필요하다.
- 데이터 바인딩을 위한 필드명을 알아내기 위해 getter나 setter 중 1가지는 정의되어 있어야 한다.


**@ModelAttribute**
- method argument에 어노테이션을 생략시에도 적용된다.
- @ModelAttribute를 사용하면 request parameter 데이터를 Java 객체에 맵핑한다.
- 객체의 필드에 접근해 데이터를 바인딩할 수 있는 생성자 혹은 setter method가 필요하다.
- Query String 및 Form 형식이 아닌 데이터는 처리할 수 없다.