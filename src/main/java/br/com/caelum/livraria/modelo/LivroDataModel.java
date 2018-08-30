package br.com.caelum.livraria.modelo;

import org.primefaces.model.LazyDataModel;

public class LivroDataModel extends LazyDataModel<Livro> {

	/**
	 *  CLASSE NAO USADA
	 */
	private static final long serialVersionUID = 2948232237230849780L;
	
/*	DAO<Livro> dao = new DAO<Livro>(Livro.class);
	
	public LivroDataModel() {
		super.setRowCount(dao.quantidadeDeElementos());
	}*/
	
	
//	public List<Livro> load(int inicio, int quantidade, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
//	    String titulo = (String) filtros.get("titulo");
//
//	    return dao.listaTodosPaginada(inicio, quantidade, "titulo", titulo);
//	}
//	
	

}
