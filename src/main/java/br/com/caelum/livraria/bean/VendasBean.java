package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.dao.VendaDao;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;
import br.com.caelum.livraria.tx.Transacional;

/*@ManagedBean //Era usado para gerenciar pelo o JSF
@ViewScoped*/
@Named // usado para o CDI gerenciar o projeto
@ViewScoped // Tag do pacote para o CDI javax.faces.view.ViewScoped
public class VendasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Venda venda = new Venda();
	@Inject
	private VendaDao vendaDao;
	@Inject
	private LivroDao livroDao;
	private List<Livro> livros;
	private Integer livroId;
	private BarChartModel vendasModel;
	List<Venda> buscaVendas;

	@PostConstruct
	public void init() {
		createVendasModel();
	}

	private void createVendasModel() {
		vendasModel = initVendasModel();
		vendasModel.setAnimate(true);
		vendasModel.setZoom(true);

//		vendasModel.setTitle("Vendas");
		vendasModel.setLegendPosition("ne");

		Axis xAxis = vendasModel.getAxis(AxisType.X);
		xAxis.setLabel("Título");
		Axis yAxis = vendasModel.getAxis(AxisType.Y);
		yAxis.setLabel("Quantidade");
		yAxis.setMin(0);
		yAxis.setMax(1000);
	}

	private BarChartModel initVendasModel() {

		BarChartModel model = new BarChartModel();

		Set<Integer> buscaAno = buscaAno();
		List<Livro> todosLivros = getLivros();

		for (Integer ano : buscaAno) {

			ChartSeries vendaSeries = new ChartSeries();

			List<Venda> vendas = buscaVendas(ano);
			vendaSeries.setLabel(Integer.toString(ano));

			for (Livro livro : todosLivros) {
				vendaSeries.set(livro.getTitulo(), null);
			}

			for (Venda venda : vendas) {
				vendaSeries.set(venda.getLivro().getTitulo(), venda.getQuantidade());
			}

			model.addSeries(vendaSeries);
		}
		return model;
	}

	@Transacional
	public List<Venda> buscaVendas(Integer ano) {
		this.buscaVendas = this.vendaDao.buscaVendas(ano);
		return this.buscaVendas;
	}

	@Transacional
	public Set<Integer> buscaAno() {
		List<Integer> anos = vendaDao.buscaAno();
		Set<Integer> anosSet = new TreeSet<Integer>(anos);
		return anosSet;
	}

	@Transacional
	public void gravarVenda() {
		System.out.println("Gravando venda ");

		Livro livro = this.livroDao.buscaPorId(livroId);
		this.venda.setLivro(livro);
		this.venda.setId(null);

		vendaDao.adiciona(this.venda);

		createVendasModel();
	}

	@Transacional
	public List<Livro> getLivros() {
		this.livros = livroDao.listaTodos();
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public BarChartModel getVendasModel() {
		return vendasModel;
	}

	public void setVendasModel(BarChartModel vendasModel) {
		this.vendasModel = vendasModel;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}
	public List<Venda> getBuscaVendas() {
		return buscaVendas;
	}

}
