# Development

## Gradle and Dependencies

This project utilizes [Gradle]((https://docs.gradle.org/current/userguide/userguide.html)) as its package manager. For detailed insights into plugins, dependencies, tasks, and related aspects, please refer to the build.gradle file. Ensure that Gradle version 8.7 or higher is installed for compatibility.

This dependency list is as follows:

* Spring
    * [Spring Boot 3](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
    * [Spring Security Crypto](https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/crypto.html)
    * [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
* Database and NoSQL
    * [MySQL Java Connector](https://www.mysql.com/products/connector/)
* API
    * [Spring Boot GraphQL](https://docs.spring.io/spring-graphql/docs/1.2.2/reference/html/)
* Utility
    * [Guava](https://github.com/google/guava/wiki)
    * [Apache Commons Validator](https://commons.apache.org/proper/commons-validator/)

## API and GraphQL

This project employs GraphQL as its API protocol. Developers are encouraged to adhere to GraphQL best practices and the provided specifications. For those unfamiliar with GraphQL, we recommend referring to the (GraphQL documentation)[https://graphql.org/learn/] to begin familiarizing themselves with its concepts and usage.

The GraphQL schema files are located in `src/main/resources/graphql`. Every model should be represented by a corresponding type, although not every field needs to be included. Developers have the flexibility to introduce additional types as necessary. It's important to ensure that entries within root types (Query, Mutation, and Subscription) are appropriately commented for clarity and maintainability. Each root type entry should correspond to a controller method. Note that all comments should be written in schema file instead of above the method.

All controllers are situated within the `controller` package. It's advisable for developers to categorize their mappings based on the respective controller names instead of clustering them all within a single controller. This ensures a more organized and maintainable codebase.

The custom exception resolver, `aop.SupervisorExceptionResolver`, governs the format of errors within the response body. Each error consists of two components: the message, conveying information for client-side interpretation, and the extensions, encompassing an error code and a classification for enhanced error handling and categorization. An example of errors is as follows:

```json
{
  "errors": [
    {
      "message": "Username already in use.",
      "locations": [],
      "extensions": {
        "code": 11000,
        "classification": "NOT_FOUND"
      }
    }
  ]
}
```

## Authentication

For enhanced security, it is imperative that all passwords and passcodes are encrypted using `utility.Encryptor`. This utility offers both encryption and matching methods, ensuring robust protection. Powered by the PBKDF2 algorithm within the Spring Security Crypto library, it guarantees strong cryptographic standards.

The `Encryptor` component is integrated within `SupervisorConfiguration`, ensuring effortless usage:

```java
private final Encryptor encryptor;

final String password = "a password";

// Encrypt the password to generate an authentication string
final String authString = encryptor.encrypt(password);

// Verify if the provided password matches the authentication string
final boolean isPasswordMatched = encryptor.matches(password, authString);
```

## Session and Cookies

### Set Cookies for Client Ends



## v2

[Supervisor 2](https://github.com/typinghare/supervisor-spring/tree/v2/src/main/java/me/jameschan/supervisor)