package com.vf.tickettothemoon.administrators.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vf.tickettothemoon.administrators.domain.model.Prof;
import com.vf.tickettothemoon.administrators.domain.service.ProfService;


/**
 * ListeProfController
 */
@Controller
@RequestMapping("/prof")
public class ProfController {
    ProfService profService;

    public ProfController(ProfService profService) {
        this.profService = profService;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("listeProf", profService.findAll());
        return "prof/liste";
    }

    @GetMapping("/{id}")
    public String montre(Model model, @PathVariable Long id) {
        // Traitement très sommaire...
        // normalement, ici, on devrait envoyer sur une page d'erreur 
        // si le prof n'est pas trouvé.
        // à la place, on aura une exception.
        Prof p= profService.find(id).get();
        model.addAttribute(p);
        return "prof/montre";
    }
    

    
}