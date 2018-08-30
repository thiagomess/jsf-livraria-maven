package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

/*@ManagedBean //Era usado para gerenciar pelo o JSF
@ViewScoped*/
@Named //usado para o CDI gerenciar o projeto
@ViewScoped// Tag do pacote para o CDI javax.faces.view.ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	
	@Inject
	private UsuarioDao dao;
	
	@Inject
	private FacesContext context; // FacesContext agora é criado pela JsfUtil

	public Usuario getUsuario() {
		return usuario;
	}

	public String efetuaLogin() {
		
		System.out.println("login inválido: " + this.usuario.getEmail());

		boolean existe = dao.existe(this.usuario);
		
		if (existe) {
			System.out.println("login efetuado com sucesso: " + this.usuario.getEmail());
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "carousel?faces-redirect=true";
		}
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));
		return "login";
		
		
	}
	
	public String efetuaLogout() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		
		return "login?faces-redirect=true";
		
		
		
	}

}
