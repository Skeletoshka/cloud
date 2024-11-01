package biz.bna.lab1.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

@RestController
public class CounterController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/counter", method = RequestMethod.GET)
    public String counter(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("{\"success\":%d}", counter.getAndIncrement());
    }
}
