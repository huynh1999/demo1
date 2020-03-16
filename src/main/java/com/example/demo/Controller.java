package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletResponse;

@RestController
public class Controller {
    @GetMapping("/")
    String test(HttpServletResponse response)
    {
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Headers","origin, x-requested-with, content-type");
        response.setHeader("Access-Control-Allow-Methods","PUT, GET, POST, DELETE, OPTIONS");
        return "{\n" +
                " \"messages\": [\n" +
                "{\"text\": \"123456\""
                +
                " }\n]" +
                "}";
    }
}
