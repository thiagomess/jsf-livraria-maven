package br.com.caelum.livraria.dao;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("livraria");
	
	@Produces // informa para o CDI que o getEntityManager produz um EntityManager
	@RequestScoped //iremos produzir um um novo EntityManager a cada requisi��o
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void close(@Disposes EntityManager em) { // para chamar um m�todo quando a requisi��o acaba, o CDI possui uma anota��o @Disposes
		em.close();
	}
}
