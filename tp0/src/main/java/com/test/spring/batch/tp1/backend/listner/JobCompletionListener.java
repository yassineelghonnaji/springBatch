package com.test.spring.batch.tp1.backend.listner;

import com.test.spring.batch.tp1.backend.model.Personne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	//recevoir des notification des differents evenements dans la cycle de vie de traitement

	@Override
	public void afterJob(JobExecution jobExecution) {

		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			jdbcTemplate.query("SELECT id, nom, prenom, civilite FROM personne",
					(rs, row) -> new Personne(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4))
			).forEach(personne -> log.info("Found <" + personne + "> in the database."));
		}
	}


}