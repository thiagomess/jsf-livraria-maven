package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Venda;
import br.com.caelum.livraria.tx.Log;

public class VendaDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	private DAO<Venda> vendaDao;
	
	@PostConstruct
	void init() {
		this.vendaDao = new DAO<Venda>(this.em, Venda.class);
	}
	
	@Log
	public List<Venda> buscaVendas(Integer ano) {
		return vendaDao.buscaVendas(ano);
	}

	public void adiciona(Venda venda) {
		this.vendaDao.adiciona(venda);
		
	}

	public List<Integer> buscaAno() {
		return this.vendaDao.buscaAno();
	}
	

}
