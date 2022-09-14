package com.trivadis.springreactive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class PersonRestController {

    @GetMapping(value = "/", produces = "text/event-stream")
    public Flux<Person> getPerson(@RequestParam String name) {
        System.out.println("START REQUEST FOR NAME: " + name);
        return Flux.just(name, name, name)
                .map(Person::new)
                .delayElements(Duration.ofMillis(1000));
    }

}
