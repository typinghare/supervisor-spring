# Development

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

## v2

[Supervisor 2](https://github.com/typinghare/supervisor-spring/tree/v2/src/main/java/me/jameschan/supervisor)