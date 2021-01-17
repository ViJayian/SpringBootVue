package org.vijayian.controller;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 *
 *
 * @author ViJay
 * @date 2021-01-17
 */
@ControllerAdvice
public class MyAdvice {
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        System.out.println(webDataBinder);
    }
}
