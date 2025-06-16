* Generate a keystore for SSL/TLS:

  ```bash
  keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore src/main/resources/keystore.p12 -validity 3650 -dname "CN=localhost, OU=IT, O=Spring Boot, L=Paris, ST=IDF, C=FR" -storepass password -keypass password
  ```
  
* Run the application

  ```bash
  ./mvnw spring-boot:run
  ```

* Access the application at `https://localhost:8443/api/v1/hello`, accept the certificate warning in your browser.
* Open devtools (F12), go to `Network` tab, on the `hello` request, the `Protocol` column should show be `h2` for HTTP/2.
