package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.tx.Transacional;
import br.com.caelum.livraria.util.RedirectView;

/*@ManagedBean //Era usado para gerenciar pelo o JSF
@ViewScoped*/
@Named //usado para o CDI gerenciar o projeto
@ViewScoped// Tag do pacote para o CDI javax.faces.view.ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject //Injeta o dao nessa classe ao inves de instanciarmos manualmente (new AutorDao();)
	private AutorDao dao;
	
	private Autor autor = new Autor();
	private Integer autorId;
	private String mensagem;
	private Severity tipoErro;
	
	@Inject
	private FacesContext context; // FacesContext agora é criado pela JsfUtil
	
	
	@Transacional // significa que este metodo esta vinculado a classe do pacote tx
	public void carregarAutorPeloId() {
		this.autor = this.dao.buscaPorId(autorId);
	}
	@Transacional // significa que este metodo esta vinculado a classe do pacote tx
	public List<Autor> getAutores() {
		return this.dao.listaTodos();

	}

	// Removendo Autor do Banco
	@Transacional // significa que este metodo esta vinculado a classe do pacote tx
	public void removeAutor(Autor autor) {
		//verifica se o autor possui alguem livro vinculado
		List<Livro> existeLivro = this.dao.verificaRemocaoAutor(autor.getId());
		
		if (existeLivro.size() == 0) {
			this.dao.remove(autor);
			mensagem = "Autor removido com sucesso";
			tipoErro = FacesMessage.SEVERITY_INFO;
		} else {
			mensagem = "Não é possível excluir autor com um livro vinculado";
			tipoErro = FacesMessage.SEVERITY_ERROR;
		}
		context.addMessage(null, new FacesMessage(tipoErro, mensagem, ""));
	}

	public void carregaAutor(Autor autor) {
		System.out.println("carregando autor: " + autor.getNome());
		this.autor = autor;
	}
	
	@Transacional // significa que este metodo esta vinculado a classe do pacote tx
	public RedirectView gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if (this.autor == null) {
			this.dao.adiciona(this.autor);
		} else {
			this.dao.atualiza(autor);
		}
		this.autor = new Autor();
		return new RedirectView("livro");
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	/*
	 * Metodo que da um redirect e outro um forward explicação em
	 * https://cursos.alura.com.br/course/jsf/task/1979
	 * 
	 * public ForwardView gravar() { System.out.println("Gravando autor " +
	 * this.autor.getNome());
	 * 
	 * this.dao.adiciona(this.autor); this.autor = new Autor();
	 * return new ForwardView("livro"); }
	 */
}
