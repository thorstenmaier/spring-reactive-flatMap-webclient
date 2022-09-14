package com.trivadis.springreactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class LocalDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalDataApplication.class, args);

        Flux<String> params = Flux.just("Maier", "Müller", "Schmitt");


        System.out.println("----- flatMap() ------");
        params
                .flatMap(name -> getPersonsForName(name))
                .subscribe(s -> System.out.println(s + " " + Thread.currentThread().getName()));


        System.out.println("----- flatMapSequential() ------");
        params
                .flatMapSequential(name -> getPersonsForName(name))
                .subscribe(s -> System.out.println(s + " " + Thread.currentThread().getName()));


        System.out.println("----- concatMap() ------");
        params
                .concatMap(name -> getPersonsForName(name))
                .subscribe(s -> System.out.println(s + " " + Thread.currentThread().getName()));
    }

    private static Flux<Person> getPersonsForName(String name) {
        System.out.println("START REQUEST FOR " + name + " " + Thread.currentThread().getName());
        return Flux.just(name, name, name)
                .map(Person::new)
//                .delayElements(Duration.ofMillis(1000))
                ;
    }

}
