package ma.bankati.controllers;

import ma.bankati.entities.Admin;
import ma.bankati.entities.Client;
import ma.bankati.entities.Compte;
import ma.bankati.entities.Utilisateur;
import ma.bankati.entities.enums.Devise;
import ma.bankati.entities.enums.Role;
import ma.bankati.repositories.ClientRepository;
import ma.bankati.repositories.CompteRepository;
import ma.bankati.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/users")
public class UserAdminController {

    @Autowired private UtilisateurRepository utilisateurRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private CompteRepository compteRepository;

    @GetMapping
    public String allUsers(Model model) {
        model.addAttribute("users", utilisateurRepository.findAll());
        model.addAttribute("roles", Role.values());
        model.addAttribute("editUser", new Admin()); // ✅ Correction ici
        return "admin/users";
    }


    @PostMapping("/save")
    public String saveUser(@RequestParam(required = false) Long id,
                           @RequestParam String nom,
                           @RequestParam String prenom,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam Role role) {

        boolean nouveau = (id == null);
        Utilisateur utilisateur;

        if (nouveau) {
            if (role == Role.CLIENT) {
                utilisateur = new Client();
            } else {
                utilisateur = new Admin(); // ✅ classe à créer
            }
            utilisateur.setCreatedAt(LocalDate.now());
        } else {
            utilisateur = utilisateurRepository.findById(id).orElseThrow();
        }

        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setUsername(username);
        utilisateur.setPassword(password);
        utilisateur.setRole(role);

        utilisateurRepository.save(utilisateur);

        // Créer compte si nouveau client
        if (role == Role.CLIENT && utilisateur instanceof Client client && nouveau) {
            Compte compte = new Compte();
            compte.setClient(client);
            compte.setSolde(1000.0);
            compte.setDevise(Devise.DH);
            compte.setDateCreation(LocalDate.now());
            compteRepository.save(compte);
        }

        return "redirect:/admin/users";
    }



    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        utilisateurRepository.deleteById(id);
        clientRepository.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam Long id, Model model) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElse(null);
        if (utilisateur == null) {
            return "/admin/users";
        }
        model.addAttribute("editUser", utilisateur);
        model.addAttribute("users", utilisateurRepository.findAll());
        model.addAttribute("roles", Role.values());
        return "admin/users"; // même page, avec le formulaire rempli
    }

}
