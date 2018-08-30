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
	@RequestScoped //iremos produzir um um novo EntityManager a cada requisição
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void close(@Disposes EntityManager em) { // para chamar um método quando a requisição acaba, o CDI possui uma anotação @Disposes
		em.close();
	}
}
