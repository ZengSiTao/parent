package com.linktop.cloud.entranceguardcore.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class ProductController {

    @RequestMapping(value = "hi", method = RequestMethod.POST)
    public String listForOrder(@RequestParam("name") String name){

        return "listForOrder调用成功";

    }
}