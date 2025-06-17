* Run Zipkin

  ```bash
  docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin
  ```

* browse the app at `https://localhost:8443/api/v1/hello` and `https://localhost:8443/api/v1/products`
* Open Zipkin UI in your browser at `http://localhost:9411` and explore traces.

  **Expected output:**

  ```bash
  2025-06-17T00:27:49.782+02:00  INFO [spring-boot30-features,68509a65dbea9bec40e0685647e20399,40e0685647e20399] 32708 --- [io-8443-exec-10] i.b.s.b.f.controller.DemoController      : Fetching all products.
  ```
  
* launch Tests