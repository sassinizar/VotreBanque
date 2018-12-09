package com.banque;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.banque.dao.ClientRepository;
import com.banque.dao.CompteRepository;
import com.banque.dao.OperationRepository;
import com.banque.entities.Client;
import com.banque.entities.Compte;
import com.banque.entities.CompteCourant;
import com.banque.entities.CompteEpargne;
import com.banque.entities.Retrait;
import com.banque.entities.Versement;
import com.banque.metier.IBanqueMetier;

@SpringBootApplication
public class VotreBanqueApplication implements CommandLineRunner{

	@Autowired
	private ClientRepository cltrepo;
	@Autowired
	private CompteRepository cptrepo;
	@Autowired
	private OperationRepository oprepo;
	@Autowired
	private IBanqueMetier bqm;
	public static void main(String[] args) 	 {
		
		SpringApplication.run(VotreBanqueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*Client c1=cltrepo.save(new Client("sassi","sassi@outlook.fr"));
		Client c2=cltrepo.save(new Client("nizar","nizar@gmail.com"));
		
		Compte cp1=cptrepo.save(new CompteCourant("c1", new Date(), 9000, c1, 6000));
		Compte cp2=cptrepo.save(new CompteEpargne("c2", new Date(), 6000, c2, 5.5));
		
		oprepo.save(new Versement(new Date(), 9000, cp1));
		oprepo.save(new Versement(new Date(), 6000, cp1));
		oprepo.save(new Versement(new Date(), 2300, cp1));
		oprepo.save(new Retrait(new Date(), 9000, cp1));
		
		oprepo.save(new Versement(new Date(), 2300, cp2));
		oprepo.save(new Versement(new Date(), 400, cp2));
		oprepo.save(new Versement(new Date(), 2300, cp2));
		oprepo.save(new Retrait(new Date(), 3000, cp2));
	
		bqm.verser("c1", 5555555);*/
	}
}
