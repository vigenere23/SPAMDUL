![Build](https://github.com/vigenere23/SPAMDUL/workflows/Build/badge.svg)
![spamdul-logo](https://user-images.githubusercontent.com/32545895/105125463-70e61880-5aaa-11eb-853d-cfb5188ba23f.png)

# SPAMDUL - Rich DDD parking system

This software offers a representation of a parking software. It was done under a course from the Software Engineering program of Laval University.

## Features

- Buy parking permits and access rights
- Validate access and parking permissions and give infractions
- Manage finances and budgets
- Log parking usage
- Other small features

## Developpement

The main goal of this project was to develop a Clean architecture with a rich domain (aka DDD). Many design patterns were used in order to reach such a rich level of DDD, hence the complexity of the classes and their interactions.

For a more advanced DDD approach, see the refactor done in [`parking2`](./src/main/java/ca/ulaval/glo4003/spamdul/parking2). :warning: **This refactor is incomplete**, but was made in a way that does not prevent the main system from working correctly. The tests are still missing and some integrations are not yet done (like the permit delivery or the access logging). The overall exercice what really oriented toward a newer and richer architecture, since the features and their workings have already been proven. 

## Running

**Application**

```
mvn clean install exec:java
```

**Unit tests**

```
mvn test
```

**Integration tests**

```
mvn test -P integration-tests
```

**API tests**

> These tests use [Newman](https://support.postman.com/hc/en-us/articles/115003710329-What-is-Newman-), the Postman API.

```
newman run SPAMD-UL.postman_collection.json
```
