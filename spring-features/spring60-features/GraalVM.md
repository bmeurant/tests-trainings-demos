# GraalVM Native Image support

- install GraalVM

  ```bash
  sdk install java 17.0.9-graalce
  sdk use java 17.0.9-graalce
  ```

- verify installation:

  ```bash
  gu install native-image
  ```

  **Expected Output:**

  ```text
  Downloading: Component catalog from www.graalvm.org
  Processing Component: Native Image
  Component Native Image (org.graalvm.native-image) is already installed.
  ```

- generate full classpath

  ```bash
  mvn dependency:build-classpath -Dmdep.outputFile=classpath.txt

  # Get the dependencies classpath
  SPRING60_DEP_CLASSPATH=$(cat classpath.txt)
  
  # Add your project's compiled classes directory to the beginning of the classpath
  SPRING60_FULL_CLASSPATH="target/classes:SPRING60_DEP_CLASSPATH"
  ```

  ```bash
  # Verify the full classpath (optional)
  echo $SPRING60_FULL_CLASSPATH
  ```

- run tracing agent to manage reflexion configuration for native images

  ```bash
  java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image/spring60-features -cp "$SPRING60_FULL_CLASSPATH" io.bmeurant.spring60.features.MainApplication 
  ```

  **Expected Output:**

  Agent should have created and filled files in `src/main/resources/META-INF/native-image/spring60-features` directory,
  such as:

  ```text
    src/main/resources/META-INF/native-image/spring60-features/reflect-config.json
  ```

- launch native compilation

  ```bash
  mvn clean package -Pnative
  ```

  **Expected Output:**

  ```text
  ------------------------------------------------------------------------------------------------------------------------
  Produced artifacts:
  /tests-trainings-demos/spring-features/spring60-features/target/spring60-features (executable)
  ========================================================================================================================
  ```

    - run the native executable

  ```bash
  ./target/spring60-features
  ```

  **Expected Output:**

  ```text
  ...
  Jun 12, 2025 11:47:00 AM io.bmeurant.spring60.features.MainApplication main
  INFO: Spring Framework 6.0 Features Demo Application Finished.
  ```
