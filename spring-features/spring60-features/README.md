| Topic                   | Description                           | Old Practice                        | New Practice                             | Test / Class Example                                                                                              |
|-------------------------|---------------------------------------|-------------------------------------|------------------------------------------|-------------------------------------------------------------------------------------------------------------------|
| ⚙️ Java 17+ required    | Requires JDK 17 minimum               | Java 8/11                           | Java 17+                                 | -                                                                                                                 |
| ✅ Annotation-only mode  | XML is no longer officially supported | `.xml` files                        | `@Configuration`, `@ComponentScan`, etc. | -                                                                                                                 |
| ☠️ Removal of `javax.*` | Everything migrated to `jakarta.*`    | `javax.servlet`, `javax.annotation` | `jakarta.servlet`, `jakarta.annotation`  | [`jakartaValidationService`](./src/main/java/io/bmeurant/spring60/features/jakarta/JakartaValidationService.java) |

* Launch application [`MainApplication`](./src/main/java/io/bmeurant/spring60/features/MainApplication.java) to see the
  features in action.
* Launch tests in [Tests](./src/test/java/io/bmeurant/spring60/features) to validate the features.