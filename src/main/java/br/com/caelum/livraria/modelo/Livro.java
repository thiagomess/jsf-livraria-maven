package br.com.caelum.livraria.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Livro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	private String titulo;
	private String isbn;
	private double preco;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataLancamento = Calendar.getInstance();
	@ManyToMany
	private Set<Autor> autores = new HashSet<Autor>(); //Não permite ser adicionado autor repetido, utiliza o metodo HashCode e Equals da classe model

	public Set<Autor> getAutores() {
		return autores;
	}

	public void adicionaAutor(Autor autor) {
		this.autores.add(autor);
	}

	public Livro() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public Calendar getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	
	//metodo que esta sendo chamado na LivroBean para remover Autor
	public void removeAutor(Autor autor) {
		this.autores.remove(autor);
		
	}

}