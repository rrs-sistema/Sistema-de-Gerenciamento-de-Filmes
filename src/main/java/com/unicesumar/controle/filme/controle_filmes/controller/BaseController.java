package com.unicesumar.controle.filme.controle_filmes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

}