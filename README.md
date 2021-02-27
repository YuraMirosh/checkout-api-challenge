# Checkout API challenge

## Usage

- Test `./gradlew test`
- Ktlint check `./gradlew ktlintCheck`
- Run `./gradlew bootRun`

## Task description

We would like you to build a simplified e-commerce API with a single endpoint that performs a
checkout action. The single endpoint should take a list of watches and return the total cost.
In terms of programming language, we work with Kotlin and Java 8+, if you feel that you have the
experience to build a solution in any of those languages then please do. Otherwise, we are happy for
you to build a solution using a language and framework that you feel best showcases your ability.

Below is a catalogue of four watches and their associated prices:

| Watch ID| Watch Name  | Unit Price| Discount |
|---------|-------------|-----------|----------|
| 001     | Rolex       | 100       | 3 for 200|
| 002     | Michael Kors| 80        | 2 for 120|
| 003     | Swatch      | 50        |          |
| 004     | Casio       | 30        |          |

There are a few requirements worth noting here:
- The first two products have a possible discount. As an example, if the user attempts to
checkout three or six Rolex watches then they will receive the discount price once or twice,
respectively.
- There is no limit to the number of items or combinations of watches a user can checkout.
- There is no limit to the number of times a discount can be used.
- Similarly, a user can checkout a single item if they wish.

## API

### Checkout endpoint
```
POST http://localhost:8080/checkout
```

### Request
```
# Headers
Accept: application/json
Content-Type: application/json

# Body
["001","002","001","004","003"]
```
Example:
```
curl -X POST 'localhost:8080/checkout' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
-d '["001","002","001","004","003"]'
```

### Response
```
# Headers
Content-Type: application/json

# Body
{ "price": 360 }
```

#### Response codes
|Code|Description             | Content-Type           |
|----|------------------------|------------------------|
|200 | Success                |application/json        |
|404 | Watch not found        |application/problem+json|
|500 | Internal server error  |application/problem+json|

## Technical stack

- Kotlin
- Gradle
- Spring Boot
- R2DBC

## Assumptions

- Unit and discount price values are integers

## What can be improved

- Add validations in business logic. Like: total price should always be positive
- Separate static information (name) and dynamic (price, discount) in the database
- Add constraints on db tables like: 
    * price should be always > 0
    * any combination with discount should be always better than without discount
    * indexes
- Add db migration tool (liquibase/flyway...)
- Add logging
- Add caching
