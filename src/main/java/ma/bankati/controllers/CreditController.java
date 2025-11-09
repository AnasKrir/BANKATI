package ma.bankati.controllers;

import jakarta.servlet.http.HttpSession;
import ma.bankati.entities.Credit;
import ma.bankati.entities.Utilisateur;
import ma.bankati.entities.enums.Role;
import ma.bankati.services.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credits")
public class CreditController {

    @Autowired private CreditService creditService;

    @GetMapping
    public String listerCredits(HttpSession session, Model model) {
        var user = (Utilisateur) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (user.getRole() == Role.CLIENT) {
            model.addAttribute("credits", creditService.findByClientId(user.getId()));
            return "client/mes-credits";
        } else {
            model.addAttribute("credits", creditService.findAll());
            return "admin/credits";
        }
    }

    @GetMapping("/demande")
    public String showForm(Model model) {
        model.addAttribute("credit", new Credit());
        return "client/demander-credit";
    }

    @PostMapping("/demande")
    public String demander(@ModelAttribute Credit credit, HttpSession session) {
        var user = (Utilisateur) session.getAttribute("user");
        credit.setClient((ma.bankati.entities.Client) user);
        creditService.demanderCredit(credit);
        return "redirect:/credits";
    }

    @PostMapping("/supprimer")
    public String supprimer(@RequestParam Long id) {
        creditService.supprimerSiEnCours(id);
        return "redirect:/credits";
    }

    @GetMapping(params = {"action", "id"})
    public String traiterActionCredit(@RequestParam String action, @RequestParam Long id) {
        switch (action) {
            case "approuver" -> creditService.approuver(id);
            case "rejeter" -> creditService.rejeter(id);
        }
        return "redirect:/credits";
    }

}
