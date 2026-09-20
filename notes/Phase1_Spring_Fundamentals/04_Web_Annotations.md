# Core Spring Web Annotations

* `@RestController`: A combination of `@Controller` and `@ResponseBody`. It tells Spring that this class is responsible for handling web requests (making it a Bean). It also tells Spring that the methods inside will return raw data (like text or JSON) directly back to the client, rather than an HTML file.
* `@GetMapping("...")`: Maps a specific HTTP GET request path (like `/` or `/projects`) to a specific Java method. When the embedded Tomcat server receives a request for that path, Spring routes it to the method annotated with this.
