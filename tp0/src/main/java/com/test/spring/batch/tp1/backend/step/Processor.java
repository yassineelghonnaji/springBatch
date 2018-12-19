package com.test.spring.batch.tp1.backend.step;

import com.test.spring.batch.tp1.backend.model.Personne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<Personne, Personne> {

	private static final Logger log = LoggerFactory.getLogger(Processor.class);


	@Override
	public Personne process(Personne personne) throws Exception {
		final int id= personne.getId();
		final String nom= personne.getNom().toUpperCase();
		final String prenom=personne.getPrenom().toUpperCase();
		final String civilite=personne.getCivillite().toUpperCase();

		final Personne transformationPersonne=new Personne(id,nom,prenom,civilite);

		log.info("converting ("+ personne+" ) into ( "+ transformationPersonne + ")");

		return transformationPersonne;
	}
}