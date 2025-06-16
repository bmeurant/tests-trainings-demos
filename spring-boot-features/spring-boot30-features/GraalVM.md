* Install GraalVM

  ```bash
  sdk install java 17.0.9-graalce
  sdk use java 17.0.9-graalce
  ```
  
  **Expected output:**

  ```text
  Using java version 17.0.9-graalce in this shell.
  ```

* Install the Native Image plugin & verify installation

  ```bash
  gu install native-image
  native-image --version
  ```
  
  **Expected output:**
  
  ```text
  native-image 17.0.9 2023-10-17
  GraalVM Runtime Environment GraalVM CE 17.0.9+9.1 (build 17.0.9+9-jvmci-23.0-b22)
  Substrate VM GraalVM CE 17.0.9+9.1 (build 17.0.9+9, serial gc)
  ```
  
* Compile native image

  ```bash
  mvn clean package -Pnative
  ```

  **Expected output:**

  ```text
  Finished generating 'spring-boot30-features' in 8m 24s.
  ```
  
* Run the native image

  ```bash
  ./target/spring-boot30-features
  ```
  
* Test the application

  ```bash
  curl --http2 -k https://localhost:8443/api/v1/hello
  ```

  **Expected output:**

  ```text
  Hello from Spring Boot 3.0.13!
  ```

  ```bash
  curl --http2 -k https://localhost:8443/api/v1/products
  ```

  **Expected output:**

  ```text
  [{"id":1,"name":"Laptop","price":1200.0},{"id":2,"name":"Mouse","price":25.0},{"id":3,"name":"Keyboard","price":75.0}]
  ```