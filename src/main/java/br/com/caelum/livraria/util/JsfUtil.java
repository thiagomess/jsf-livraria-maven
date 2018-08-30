package br.com.caelum.livraria.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;


public class JsfUtil {
	
	@Produces // informa para o CDI que o getEntityManager produz um EntityManager
	@RequestScoped //iremos produzir um um novo EntityManager a cada requisição
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
		
	}

}
