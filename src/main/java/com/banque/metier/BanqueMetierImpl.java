package com.banque.metier;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banque.dao.CompteRepository;
import com.banque.dao.OperationRepository;
import com.banque.entities.Compte;
import com.banque.entities.CompteCourant;
import com.banque.entities.Operation;
import com.banque.entities.Retrait;
import com.banque.entities.Versement;


@Service
@Transactional
public class BanqueMetierImpl implements IBanqueMetier {
	    
	@Autowired
	private CompteRepository cptrepo;
	@Autowired   
	private OperationRepository oprepo;
	@Override
	public Compte consulterCompte(String codeCpte) {
		Compte cp=cptrepo.findById(codeCpte).orElse(null);
		if(cp==null) throw new RuntimeException("Compte introuvable");
		return cp;
	}

	@Override
	public void verser(String codeCpte, double montant) {
		Compte cp=consulterCompte(codeCpte);
		Versement v=new Versement(new Date(), montant, cp);
		oprepo.save(v);
		cp.setSolde(cp.getSolde()+montant);
		cptrepo.save(cp);
	}

	@Override
	public void retirer(String codeCpte, double montant) {
		Compte cp=consulterCompte(codeCpte);
		double facilitescaisse = 0;
		if(cp instanceof CompteCourant)
			facilitescaisse=((CompteCourant) cp).getDecouvert();
		if(cp.getSolde()+facilitescaisse<montant)
			throw new RuntimeException("Votre solde est insuffisant");
		Retrait v=new Retrait(new Date(), montant, cp);
		oprepo.save(v);
		cp.setSolde(cp.getSolde()-montant);
		cptrepo.save(cp);
		
	}

	@Override
	public void virement(String codeCpte1, String codeCpte2, double montant) {
		if(codeCpte1.equals(codeCpte2)) {
			throw new RuntimeException("Impossible: virement sur le meme compte");
		}
		retirer(codeCpte1, montant);
		verser(codeCpte2, montant);
		
	}

	@Override
	public Page<Operation> listOperation(String codeCpte, int page, int size) {
		return oprepo.ListeOperation(codeCpte,PageRequest.of(page,size));
	}

}
