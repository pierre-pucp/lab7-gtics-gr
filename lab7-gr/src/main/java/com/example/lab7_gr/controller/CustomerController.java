package com.example.lab7_gr.controller;

import com.example.lab7_gr.entity.Customer;
import com.example.lab7_gr.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/clientes")
    public String listaClientes(Model model) {
        List<Customer> lista = customerRepository.findAll();
        model.addAttribute("listaClientes", lista);
        return "customer/lista";
    }
}
