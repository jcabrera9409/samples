## Introduction
You are developing a microservice consisting of three layers:
- Persistence layer - a `dao` package responsible for retrieving data represented by `Item`, `Review` and `User` entities in the `model` package.
- Service layer - a `service` package responsible for business logic.
- Web layer - a `controller` package serving as a REST endpoint.

## Task definition
Please implement the following methods in respective classes: 
- `ItemRepository.java`
  - `List<Item> findItemsWithAverageRatingLowerThan(Double rating)` - the method should find all items that have a rating 
    lower than the rating passed as an argument. Use the reviews associated with each item to calculate the item rating.
    If there are no reviews for an item, then its rating should be zero. Your implementation should make as few database round trips as possible.

- `ItemService.java`
  - `List<String> getTitlesWithAverageRatingLowerThan(Double rating)` - the method should retrieve 
    data using `findItemsWithAverageRatingLowerThan` method from the `ItemRepository` class and return only the titles of
    retrieved the items.

- `ItemController.java`
  - `List<String> getTitles(Double rating)` - the method should provide a REST endpoint (GET method) under the `/titles`
    path and return data retrieved from `getTitlesWithAverageRatingLowerThan` method in the `ItemService`. Data should 
    be returned as JSON with UTF8 encoding.
