package org.vijayian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vijayian.service.HelloService;

/**
 * hello
 *
 * @author ViJay
 * @date 2021-01-30
 */
@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping
    public String hello() {
        return helloService.hello();
    }
}
