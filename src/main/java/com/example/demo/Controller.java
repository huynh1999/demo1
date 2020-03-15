package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class Controller {
    @GetMapping("/")
    String test(HttpServletResponse response)
    {
        response.setHeader("Access-Control-Allow-Origin","https://springmvctq.herokuapp.com");
        response.setHeader("Access-Control-Allow-Headers","origin, x-requested-with, content-type");
        response.setHeader("Access-Control-Allow-Methods","PUT, GET, POST, DELETE, OPTIONS");
        return "Ok";
    }
}
