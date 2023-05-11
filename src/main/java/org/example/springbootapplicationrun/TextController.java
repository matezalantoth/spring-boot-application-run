package org.example.springbootapplicationrun;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TextController {


    @RequestMapping("/mate")
    public String hello(){

        return "hi";
    }

    @RequestMapping("/apa")
    public String bonjour() throws IOException {
       return "bonjour";
    }


}