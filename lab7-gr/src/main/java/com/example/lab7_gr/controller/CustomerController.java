package com.example.lab7_gr.controller;

import com.example.lab7_gr.entity.Customer;
import com.example.lab7_gr.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping({"", "/", "/lista"})
    public String listCustomer(Model model) {
        List<Customer> lista = customerRepository.findAll();
        model.addAttribute("listaClientes", lista);
        return "customer/list";
    }

    @GetMapping("/crear")
    public String createCustomer(@ModelAttribute("customer") Customer c,
                                Model model) {
        return "customer/edit";
    }

    @GetMapping("/editar")
    public String editCustomer(@ModelAttribute("customer") Customer c,
                            @RequestParam("id") Integer id,
                            Model model) {
        Optional<Customer> opt_c = customerRepository.findById(id);
        if (opt_c.isPresent()) {
            c = opt_c.get();
            model.addAttribute("customer", c);
            return "customer/edit";
        }
        return "redirect:/clientes";
    }

    @PostMapping("/guardar")
    public String saveCustomer(@ModelAttribute("customer") @Valid Customer c,
                            BindingResult bindingResult,
                            RedirectAttributes attr,
                            Model model) {
        if (!bindingResult.hasErrors()) {
            if (c.getId() == 0) {
                attr.addFlashAttribute("msg", "Producto creado exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Producto actualizado exitosamente");
            }
            customerRepository.save(c);
            return "redirect:/clientes";
        } else {
            return "customer/edit";
        }
    }

    @PostMapping("/borrar")
    public String borrarClientes(@RequestParam("id") Integer id,
                                RedirectAttributes attr) {
        Optional<Customer> opt_c = customerRepository.findById(id);
        if (opt_c.isPresent()) {
            customerRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Producto borrado exitosamente");
        }
        return "redirect:/clientes";
    }
}
