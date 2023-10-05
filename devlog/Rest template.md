
### RestTemplate 이미지 다운로드
Image is a byte array, so you need to use byte[].class object as a second argument for RestTemplate.getForObject:
```
String url = "http://img.championat.com/news/big/l/c/ujejn-runi_1439911080563855663.jpg";
byte[] imageBytes = restTemplate.getForObject(url, byte[].class);
Files.write(Paths.get("image.jpg"), imageBytes);
```
To make it work, you will need to configure a ByteArrayHttpMessageConverter in your application config:
```
@Bean
public RestTemplate restTemplate(List<HttpMessageConverter<?>> messageConverters) {
return new RestTemplate(messageConverters);
}

@Bean
public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
return new ByteArrayHttpMessageConverter();
}
```
참고: https://stackoverflow.com/questions/32080207/how-to-download-image-using-rest-template