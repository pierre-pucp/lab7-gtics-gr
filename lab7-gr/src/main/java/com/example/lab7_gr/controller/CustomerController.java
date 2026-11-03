package com.example.lab7_gr.controller;

import com.example.lab7_gr.entity.Customer;
import com.example.lab7_gr.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("customer", c);
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

        if (c.getId()==null && customerRepository.existsByDocument(c.getDocument())) {
            bindingResult.rejectValue("document", "duplicate.document", "Este documento ya esta asociado a un cliente");
        }
        if (c.getDocumentType() != null && c.getDocument() != null) {
            if (c.getDocumentType() == Customer.DocumentType.DNI && c.getDocument().length() != 8) {
                bindingResult.rejectValue("document", "size.document", "El DNI debe tener exactamente 8 dígitos");
            } else if (c.getDocumentType() == Customer.DocumentType.RUC && c.getDocument().length() != 11) {
                bindingResult.rejectValue("document", "size.document", "El RUC debe tener exactamente 11 dígitos");
            }
        }
        if (!bindingResult.hasErrors()) {
            if (c.getId() == null || c.getId() == 0) {
                attr.addFlashAttribute("msg", "Producto creado exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Producto actualizado exitosamente");
            }
            customerRepository.save(c);
            return "redirect:/clientes";
        } else {
            model.addAttribute("customer", c);
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
