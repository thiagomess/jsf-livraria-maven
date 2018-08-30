package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.tx.Log;

public class AutorDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject //Injeta o EntityManager nessa classe 
	private EntityManager em;
	private DAO<Autor> dao;
	
	@PostConstruct //. Com isso o CDI chamará esse método assim que inicializar o AutorDao, inicializando também o DAO genérico:
	public void init() {
		this.dao = new DAO<Autor>(this.em, Autor.class);
		System.out.println("Criando AutorDao ");
	}

	@Log
	public void adiciona(Autor t) {
		dao.adiciona(t);
	}

	@Log
	public void remove(Autor t) {
		dao.remove(t);
	}

	@Log
	public void atualiza(Autor t) {
		dao.atualiza(t);
	}

	@Log
	public List<Autor> listaTodos() {
		return dao.listaTodos();
	}

	@Log
	public Autor buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public List<Livro> verificaRemocaoAutor(Integer autorId) {
		return dao.verificaRemocaoAutor(autorId);
	}


	
}
