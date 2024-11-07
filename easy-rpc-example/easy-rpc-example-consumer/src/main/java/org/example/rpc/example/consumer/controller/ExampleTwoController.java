package org.example.rpc.example.consumer.controller;

import org.example.rpc.annotation.ServiceReference;
import org.example.rpc.example.provider.api.ExampleTwoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Roc
 * @Date 2024/11/7 14:43
 */
@RestController
@RequestMapping("/exampleTwo")
public class ExampleTwoController {

    @ServiceReference
    private ExampleTwoService exampleTwoService;

    @GetMapping("/testOne/{name}")
    public String testOne(@PathVariable String name) {
        return exampleTwoService.testOne(name);
    }

    @GetMapping("/testTwo/{name}")
    public String testTwo(@PathVariable String name) {
        return exampleTwoService.testTwo(name);
    }
}
