# Spring Reactive Demo Project - flatMap, flatMapSequential, concatMap, WebClient

This project contains samples for the following aspects of reactive programming:

* Difference between `flatMap`, `flatMapSequential` and `concatMap` in Project Reactor
* How to set up a http endpoint that streams data in a reactive way to the http client
* How to call a reactive http endpoint with Spring `WebClient`
* The fact that `flatMap` handles asynchronicity for parallel web calls but only if the inner ObservableSources does not use the main thread.