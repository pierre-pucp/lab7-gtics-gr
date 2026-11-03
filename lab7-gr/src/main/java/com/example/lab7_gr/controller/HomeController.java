package com.example.lab7_gr.controller;

import com.example.lab7_gr.entity.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"", "/", "/inicio"})
    public String paginaInicio() {
        return "init";
    }

}
