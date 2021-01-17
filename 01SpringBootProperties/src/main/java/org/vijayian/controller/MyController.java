package org.vijayian.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vijayian.component.MyProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * controller
 *
 * @author ViJay
 * @date 2021-01-16
 */
@RestController
public class MyController {
    private final MyProperties myProperties;

    public MyController(MyProperties myProperties) {
        this.myProperties = myProperties;
    }

    @Value("${my.name}")
    private String name;

    @Value("${my.address}")
    private String address;

    @Value("#{'${my.likes2}'.split(',')}")
    private List<String> likes;

    @GetMapping("/")
    public MyProperties prop() {
        return myProperties;
    }

    @GetMapping("/map")
    public Map<String, Object> map() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("address", address);
        map.put("likes", likes);
        return map;
    }
}
