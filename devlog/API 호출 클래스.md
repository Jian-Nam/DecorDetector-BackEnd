# Spring API 호출 클래스
Consuming a RESTful web service

### **Situation**
DecorDetector backend에서는 IKEA웹사이트의 가구정보들을 Endpoint를 통해 받아와 가공하고 저장해야한다.



### **Task**
외부 IKEA서버와 서버대 서버로 통신을 하기 위해 외부 API 호출이 필요하다.

**Spring API호출 방법 Research**

Spring에서는 외부 api를 호출하기 위해 쓰는 다양한 방법들이 있다. 다음과 같은 다섯가지 방법들을 비교해보고 현재 적용할 수 있는 호출 클래스를 결정해야한다.
(참고: https://jie0025.tistory.com/531)
- HttpURLConnection/URLConnection
- RestTemplate
- HttpClient
- WebClient
- RestClient


**HttpURLConnection/URLConnection**

특징
- 자바에서 제공하는 기본적인 API
- 순수 자바로 HTTP 통신 가능
- URL을 통해 외부 API에 연결하고 데이터 전송 가능
- 데이터의 타입과 길이의 제한이 거의 없다.

단점
- 오래된 자바버전에거 사용하는 클래스로 동기적 통신을 기본으로 한다.
- 따라서, 요청을 보내고 응답을 받을 때까지 스레드가 대기상태에 있다.
- URLConnection은 상대적으로 저수준의 API에 해당한다.(추상화가 덜되어있다.)
- 따라서, 기본적인 요청 응답 기능이 제공되지만, 추가적인 기능은 직접구현해야한다.

**HttpClient**

특징
- HTTP프로토콜을 쉽게 사용할 수 있도록 도와주는 Apache HTTP 컴포넌트
- 객체생성이 쉽다.

단점
- URLConnection과 비교했을 때 비교적 코드가 단순해졌지만, 반복적이고 코드가 길다.
- 응답의 컨텐츠 타입에 따라 별도의 로직이 필요하다.

**SpringRestTemplate**

특징
- 스프링에서 제공하는 http 통신 템플릿이다.
- 스프링 3.0에서 추가되었다.
- org.springframework.http.client 패키지에 존재한다.
- HttpClient를 추상화하여 제공한다.
- Http 요청 후 다른 JSON, XML, String과 같은 다양한 타입의 응답을 받을 수 있다.
- Blocking 기반의 동기방식을 사용한다.
- Restful 원칙을 지킨다.
- header, content-type등을 설정한다.

장점
- 사용하기 편하고, 직관적이다.

단점
- 동기적인 Http 요청을 한다.

    연결시마다 로컬포트를 열고 tcp connection을 맺는다. 
    기본적으로 사용하지 않는 Connection pool을 이용해 개선해야한다.
- 유지관리모드에 있고 비동기 스트리밍 시나리오를 제공하는 WebClient쪽으로 대세가 기울고 있다.

**WebClient**

특징
- 스프링 5부터 도입된 웹 클라이언트 라이브러리이다.
- 비동기, 논블로킹 방식으로 외부 API를 호출할 수 있다.

장점
- 비동기/논블로킹 방식을 지원하여, 높은 처리량과 확장성을 지원한다.
- 리액티브 프로그래밍을 할 수 있다. 데이터 스트림을 효과적으로 처리할 수 있다.

단점
- Webflux를 추가해야만 사용할 수 있다.
- 무엇보다 나의 경우 이지 spring-Web모듈이 추가되어있었고, Webflux를 Dependency에 추가했음에도 모듈자체가 임포트 되지 않았다. 처음부터 webflux를 사용한 프로젝트에 적합할 듯 하다.

**RestClient**

특징
- 스프링 6.1에서 추가되었다.
- 서블릿버전의 WebClient이다.
- WebClient와 비교했을 때 Webflux의존성이 없다.
- RestTemplate의 인프라스트럭쳐 위에서 WebClient의 유연한 API를 제공한다.

단점
- 너무 최신 기술이라 현재 스프링 6.0버전을 사용하는 자는 사용할 수 없다.

### **Action**

**사용클래스 결정**

현재 바로 적용할 수 있는 RestTemplate을 사용해 구현하기로 결정하였고, 추후 스프링의 버전 업그레이드를 통해 RestClient또한 사용할 수 있는 기회가 있다면 사용해볼 것.

**Implementation**
- 다음과 같은 코드로 Ikea의 상품정보를 받아올 수 있었다. 
- `UriComponentsBuilder`를 통해 Uri를 생성한다.
- `UriComponentsBuilder`의 `fromUriString()` 과 `buildAndExpand()` 메소드를 통해 파라미터를 집어넣는다.
 
- 응답에 적합한 Entity를 생성할 수 있다면 `ResponseEntity<MyEntity>`와 같이 사용할 수 있다.
- 나의 경우 응답이 너무 복잡하여 일단 `ResponseEntity<String>`타입으로 응답을 받는다.
- `ResponseEntity<~>` 의 `getBody()`메소드를 통해 데이터값을 읽을 수 있다.
```
package hongik.graduationproject.decordetectorbackend.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class IkeaClient {

    public String getProductData(String category, String start, String end){
        URI uri = UriComponentsBuilder
                .fromUriString("https://sik.search.blue.cdtapps.com/kr/ko/product-list-page/more-products?category={category}&start={start}&end={end}&c=lf&v=20220826&sort=RELEVANCE")
                .buildAndExpand(category, start, end)
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        return responseEntity.getBody();
    }
}
```

**데이터 가공하기**
- RestTemplate을 사용해서 한 번에 객체로 받을 수 있지만, 내가 요청한 데이터가 좀 복잡한 JSON 형태라 불가능했다.  
- json-simple 라이브러리로 수행한 다른 사례를 보고 따라해보았다.(https://mopil.tistory.com/39)
### **Result**
