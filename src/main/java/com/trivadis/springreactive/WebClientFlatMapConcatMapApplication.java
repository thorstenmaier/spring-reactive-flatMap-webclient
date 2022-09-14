package com.trivadis.springreactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootApplication
public class WebClientFlatMapConcatMapApplication {

    private static WebClient client = WebClient.create("http://localhost:8080");

    public static void main(String[] args) {
        SpringApplication.run(WebClientFlatMapConcatMapApplication.class, args);

        Flux<String> params = Flux.just("Maier", "Müller", "Schmitt");

        Flux.concat(
                Mono.just("----- flatMap() ------"),
                params.flatMap(name -> getPersonsForName(name)),
                Mono.just("----- flatMapSequential() ------"),
                params.flatMapSequential(name -> getPersonsForName(name)),
                Mono.just("----- concatMap() ------"),
                params.concatMap(name -> getPersonsForName(name))
        ).subscribe(s -> System.out.println(s + " " + Thread.currentThread().getName()));
    }

    private static Flux<Person> getPersonsForName(String name) {
        return getPersonsForNameWebClient(name);
    }

    private static Flux<Person> getPersonsForNameLocal(String name) {
        System.out.println("START REQUEST FOR " + name + " " + Thread.currentThread().getName());
        return Flux.just(name, name, name)
                .map(Person::new)
                .delayElements(Duration.ofMillis(1000))
                ;
    }

    private static Flux<Person> getPersonsForNameWebClient(String name) {
        return client
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("name", name).build())
                .retrieve()
                .bodyToFlux(Person.class);
    }

}
