package ma.bankati.controllers;

import jakarta.servlet.http.HttpSession;
import ma.bankati.entities.Utilisateur;
import ma.bankati.entities.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfilController {

    @GetMapping("/profil")
    public String profil(HttpSession session, Model model) {
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);

        if (user.getRole() == Role.ADMIN) {
            return "admin/profil";
        } else {
            return "client/profil";
        }
    }

    @GetMapping("/profil-client")
    public String profilClientRedirect() {
        return "redirect:/profil";
    }

}
