package edu.metrostate.ics499.prim.controller;

import java.util.concurrent.atomic.AtomicLong;

import edu.metrostate.ics499.prim.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingJsonController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/greetingjson", method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping(value = "/greetingjson-javaconfig", method = RequestMethod.GET)
    public Greeting greetingWithJavaconfig(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
