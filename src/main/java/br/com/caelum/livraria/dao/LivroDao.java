package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;
import br.com.caelum.livraria.tx.Log;

public class LivroDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject //Injeta o EntityManager nessa classe 
	private EntityManager em;
	private DAO<Livro> dao;
	
	@PostConstruct //. Com isso o CDI chamará esse método assim que inicializar o LivroDao, inicializando também o DAO genérico:
	void init() {
		this.dao = new DAO<Livro>(this.em, Livro.class);
	}
	
	@Log
	public void adiciona(Livro t) {
		dao.adiciona(t);
	}
	
	@Log
	public void remove(Livro t) {
		dao.remove(t);
	}
	
	@Log
	public void atualiza(Livro t) {
		dao.atualiza(t);
	}
	
	@Log
	public List<Livro> listaTodos() {
		return dao.listaTodos();
	}
	
	@Log
	public Livro buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public List<Venda> verificaRemocaoLivro(Integer livroId) {
		return this.dao.verifcaRemocaoLivro(livroId);
	}
}
