package org.vijayian.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hello
 *
 * @author ViJay
 * @date 2021-01-17
 */
@RestController
public class HelloController {

    @GetMapping
    public String hello(){
        return "hello";
    }
}
