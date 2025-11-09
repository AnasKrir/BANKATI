package ma.bankati.controllers;

import jakarta.servlet.http.HttpSession;
import ma.bankati.entities.Compte;
import ma.bankati.entities.Credit;
import ma.bankati.entities.Utilisateur;
import ma.bankati.entities.enums.Role;
import ma.bankati.entities.enums.StatutCredit;
import ma.bankati.repositories.ClientRepository;
import ma.bankati.repositories.CompteRepository;
import ma.bankati.repositories.CreditRepository;
import ma.bankati.services.money.IMoneyService;
import ma.bankati.services.money.MoneyServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired private ClientRepository clientRepository;
    @Autowired private CompteRepository compteRepository;
    @Autowired private CreditRepository creditRepository;

    @GetMapping({"/", "/home"})
    public String home(@RequestParam(required = false, defaultValue = "DH") String devise,
                       HttpSession session, Model model) {

        var user = (Utilisateur) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (user.getRole() == Role.ADMIN) {
            var clients = clientRepository.findAll();
            var credits = creditRepository.findAll();

            int totalClients = clients.size();
            int totalDemandes = credits.size();
            int creditsApprouves = (int) credits.stream().filter(c -> c.getStatut() == StatutCredit.APPROUVEE).count();
            int creditsRejetes = (int) credits.stream().filter(c -> c.getStatut() == StatutCredit.REJETEE).count();
            double montantTotal = credits.stream()
                    .filter(c -> c.getStatut() == StatutCredit.APPROUVEE)
                    .mapToDouble(c -> c.getCapital() != null ? c.getCapital() : 0.0)
                    .sum();

            model.addAttribute("totalClients", totalClients);
            model.addAttribute("totalDemandes", totalDemandes);
            model.addAttribute("creditsApprouves", creditsApprouves);
            model.addAttribute("creditsRejetes", creditsRejetes);
            model.addAttribute("montantTotal", montantTotal);
            model.addAttribute("allCredits", credits);

            return "admin/home";
        } else {
            var compte = compteRepository.findByClient_Id(user.getId()).orElse(null);
            double solde = compte != null ? compte.getSolde() : 0.0;

            IMoneyService moneyService = MoneyServiceFactory.getService(devise);
            double soldeConverti = moneyService.convertir(solde);

            List<Credit> credits = creditRepository.findByClient_Id(user.getId());

            model.addAttribute("solde", solde);
            model.addAttribute("soldeConverti", soldeConverti);
            model.addAttribute("devise", devise);
            model.addAttribute("credits", credits);

            return "client/home";
        }
    }
}
