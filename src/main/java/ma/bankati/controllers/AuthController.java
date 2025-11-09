package ma.bankati.controllers;

import jakarta.servlet.http.HttpSession;
import ma.bankati.entities.Client;
import ma.bankati.entities.Utilisateur;
import ma.bankati.entities.enums.Role;
import ma.bankati.repositories.ClientRepository;
import ma.bankati.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) model.addAttribute("error", "❌ Identifiants incorrects");
        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam(required = false) boolean adminLogin,
                        HttpSession session, Model model) {
        // Recherche de l’utilisateur correspondant en base (mot de passe en clair)
        var utilisateur = utilisateurRepository.findByUsernameAndPassword(username, password)
                .orElse(null);
        if (utilisateur == null) {
            model.addAttribute("error", "❌ Identifiants incorrects");
            return "error"; // recharge la page de login avec un message d’erreur
        }
        if (adminLogin && utilisateur.getRole() != Role.ADMIN) {
            model.addAttribute("error", "⛔ Vous n'avez pas les droits administrateur");
            return "login";
        }
        // Stockage de l'utilisateur authentifié en session
        if (utilisateur.getRole() == Role.CLIENT) {
            Client client = clientRepository.findById(utilisateur.getId()).orElse(null);
            session.setAttribute("user", client);
        } else {
            session.setAttribute("user", utilisateur);
        }
        return "redirect:/home"; // redirige vers la page d’accueil appropriée
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
