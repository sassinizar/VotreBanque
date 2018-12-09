package com.banque.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.banque.entities.Compte;
import com.banque.entities.Operation;
import com.banque.metier.IBanqueMetier;

@Controller
public class BanqueController {
	@Autowired
	private IBanqueMetier bqm;
	
	@RequestMapping("/operation")
	public String index() {
		return "comptes";
	}
	
	@RequestMapping(value="/consulterCompte")
	public String consulter(Model model, String codeCpte,
			@RequestParam(name="page",defaultValue="0") int page,
			@RequestParam(name="size", defaultValue="4")int size) {
		model.addAttribute("codeCompte", codeCpte);
		try {
			Compte cp=bqm.consulterCompte(codeCpte);
			Page<Operation> pageOperation=bqm.listOperation(codeCpte,page, size);
			model.addAttribute("listeOperation", pageOperation.getContent());
			int[] pages=new int[pageOperation.getTotalPages()];
			model.addAttribute("pages", pages);
			model.addAttribute("compte", cp);
		}catch (Exception e) {
			model.addAttribute("exception",e);
		}
		return "comptes";
	}
	
	
	@RequestMapping(value="/saveOperation", method=RequestMethod.POST)
	public String saveOperation(Model model, String typeOperation, String codeCpte, double montant, String codeCompte2) {
		
		try {
			if(typeOperation.equals("VERS")) {
				bqm.verser(codeCpte, montant);
			}else if(typeOperation.equals("RETR")) {
				bqm.retirer(codeCpte, montant);
			}else {
				bqm.virement(codeCpte, codeCompte2, montant);
			}
		}catch(Exception e) {
			model.addAttribute("error", e);
			return "redirect:/consulterCompte?codeCpte="+codeCpte+"&error="+e.getMessage();
		}
			return "redirect:/consulterCompte?codeCpte="+codeCpte;
	}

}
