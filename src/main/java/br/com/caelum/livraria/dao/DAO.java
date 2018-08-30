package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

public class DAO<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Class<T> classe;
	
	@Inject //Injeta o EntityManager nessa classe
	private EntityManager em;
	
	public DAO(EntityManager manager, Class<T> classe) {
		this.em = manager;
		this.classe = classe;
		
	}

	public void adiciona(T t) {
		// persiste o objeto
		em.persist(t);
	}

	public void remove(T t) {
		// persiste o objeto
		em.remove(em.merge(t));
	}

	public void atualiza(T t) {
		// persiste o objeto
		em.merge(t);
	}

	public List<T> listaTodos() {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

		return lista;
	}

	public T buscaPorId(Integer id) {
		T instancia = em.find(classe, id);
		return instancia;
	}

	public int contaTodos() {
		long result = (Long) em.createQuery("select count(n) from livro n")
				.getSingleResult();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults, String coluna, String valor) {
	    CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
	    Root<T> root = query.from(classe);

	    if(valor != null)
	        query = query.where(em.getCriteriaBuilder().like(root.<String>get(coluna), valor + "%"));

	    List<T> lista = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

	    return lista;
	}
	
	public int quantidadeDeElementos() {
		long result = (Long) em.createQuery("select count(n) from " + classe.getSimpleName() + " n")
				.getSingleResult();
		return (int) result;
	}

	public List<Venda> buscaVendas(Integer ano) {
		List<Venda> resultado = em.createQuery("select v from Venda v where v.ano = :pAno", Venda.class).setParameter("pAno", ano).getResultList();
		
		return resultado;
	}

	public List<Integer> buscaAno() {
		List<Integer> resultList = em.createQuery("select v.ano from Venda v", Integer.class).getResultList();
		
		return resultList;
	}

	public List<Livro> verificaRemocaoAutor(Integer autorId) {
		List<Livro> resultList = em.createQuery("select l from Livro l join l.autores a where a.id = :pAutorId ", Livro.class).setParameter("pAutorId", autorId).getResultList();

		return resultList;
	}

	public List<Venda> verifcaRemocaoLivro(Integer livroId) {
		List<Venda> resultList = em.createQuery("select v from Venda v join v.livro l where l.id = :pLivroId", Venda.class).setParameter("pLivroId", livroId).getResultList();
		
		return resultList;
	}

}
