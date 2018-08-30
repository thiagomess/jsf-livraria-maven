package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Transacional // cria a ligação da classe com os medicos que irao utiliza-la
@Interceptor // essa anotação diz que @Transacional está associada a um interceptador
public class GerenciadorDeTransacao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	private EntityTransaction transaction;
	
	@AroundInvoke //Serve para o CDI entender que deve executar o metodo e retornar para aqui
	public Object gerenciaTransacao(InvocationContext context) throws Exception { //Através do InvocationContext, podemos pedir para chamar o método

		Object resultado = null;

		try {
			//pega a transação	
			transaction = em.getTransaction();
			// abre transacao
			transaction.begin();
			// "prossegue" com o método interceptado. 
			resultado = context.proceed();
			// commita a transacao
			transaction.commit();

		} catch (Exception e) {
			//verifica se a transação esta nula ou ativa
			if(transaction != null && transaction.isActive()){
				transaction.rollback();
				
			}
			throw e;
		}

		return resultado;
	}

}
