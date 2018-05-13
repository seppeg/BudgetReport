package com.cegeka;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class ExampleController {

    @GetMapping("/example")
    public String project(){
        log.info("on example");
        return "example";
    }
}
