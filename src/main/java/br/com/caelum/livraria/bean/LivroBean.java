package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;
import br.com.caelum.livraria.tx.Transacional;

/*@ManagedBean //Era usado para gerenciar pelo o JSF
@ViewScoped*/
@Named //usado para o CDI gerenciar o projeto
@ViewScoped// Tag do pacote para o CDI javax.faces.view.ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject //Injeta o dao nessa classe ao inves de instanciarmos manualmente (new livroDao();)
	private LivroDao livroDao;
	@Inject //Injeta o dao nessa classe ao inves de instanciarmos manualmente (new AutorDao();)
	private AutorDao autorDao;
	
	private Livro livro = new Livro();
	private Integer autorId;
	private Integer livroId;
	private List<Livro> livros;
	@Inject
	FacesContext context; // FacesContext agora é criado pela JsfUtil

	private String mensagem;

	private Severity tipoErro;
	
	@Transacional // significa que este metodo esta vinculado a classe do pacote tx
	public void carregarLivroPeloId() {
		this.livro = this.livroDao.buscaPorId(livroId);
	}
	
	@Transacional // significa que este metodo esta vinculado a classe do pacote tx
	public List<Autor> getAutores() {
		return  this.autorDao.listaTodos();
	}
	
	@Transacional // significa que este metodo esta vinculado a classe do pacote tx
	public List<Livro> getLivros() {
		if (this.livros == null) {
			this.livros = livroDao.listaTodos();
		}

		return livros;
	}

	public Set<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}
	
	@Transacional // significa que este metodo esta vinculado a classe do pacote tx
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			// throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			context.addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));// pegando a exception e jogando como
																				// mensagem na tela
			return;
		}
		// IF para incluir se for um livro novo ou atualizar os dados de um livro
		if (this.livro.getId() == null) {
			livroDao.adiciona(this.livro);
			this.livros = livroDao.listaTodos();

		} else {
			livroDao.atualiza(livro);
		}

		this.livro = new Livro();
	}

	// carregando livro nos textbox para alteração
	public void carregar(Livro livro) {
		System.out.println("Carregando livro: '" + livro.getTitulo() + "' para alteracao");
		this.livro = this.livroDao.buscaPorId(livro.getId());

	}

	// metodo para remover o livro
	@Transacional // significa que este metodo esta vinculado a classe do pacote tx
	public void remover(Livro livro) {

		List<Venda> existeVenda = this.livroDao.verificaRemocaoLivro(livro.getId());

		if (existeVenda.size() == 0) {
			livroDao.remove(livro);
			mensagem = "Livro removido com sucesso";
			tipoErro = FacesMessage.SEVERITY_INFO;
			this.livros = livroDao.listaTodos();
		} else {
			mensagem = "Não é possível excluir Livro com uma venda vinculada";
			tipoErro = FacesMessage.SEVERITY_ERROR;
		}
		context.addMessage(null, new FacesMessage(tipoErro, mensagem, ""));
	}

	@Transacional // significa que este metodo esta vinculado a classe do pacote tx
	public void gravarAutor() {
		Autor autor = this.autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);

		
	}

	// Removendo autor do livro e usando o metodo na classe Livro.
	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}

	// Tratamento de validação manual do xhtml
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN Deveria começar com 1"));
		}
	}
	
	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) { // java.util.Locale

		//tirando espaços do filtro
		String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();

		System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " + valorColuna);

		// o filtro é nulo ou vazio?
		if (textoDigitado == null || textoDigitado.equals("")) {
			return true;
		}

		// elemento da tabela é nulo?
		if (valorColuna == null) {
			return false;
		}

		try {
			// fazendo o parsing do filtro para converter para Double
			Double precoDigitado = Double.valueOf(textoDigitado);
			Double precoColuna = (Double) valorColuna;

			// comparando os valores, compareTo devolve um valor negativo se o value é menor do que o filtro
			return precoColuna.compareTo(precoDigitado) < 0;

		} catch (NumberFormatException e) {

			// usuario nao digitou um numero
			return false;
		}
	}

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}


	/*
	 * O Redirecionamento para a pagina poderia ser feito tambem atraves da classe
	 * java, util quando se tem biredirecionamento de paginas com IF Usa se na
	 * action no xhtml "#{livroBean.formAutor}" Explicacao>
	 * https://cursos.alura.com.br/course/jsf/task/1978
	 * 
	 * public String formAutor() {
	 * 
	 * return "autor?faces-redirect=true";
	 * 
	 * }
	 */

}
