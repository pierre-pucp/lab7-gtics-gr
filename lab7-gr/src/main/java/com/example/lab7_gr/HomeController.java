package com.example.lab7_gr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/inicio")
    public String paginaInicio() {
        return "inicio";
    }

}
