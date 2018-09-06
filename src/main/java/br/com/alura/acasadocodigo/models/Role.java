package br.com.alura.acasadocodigo.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3094379414236142529L;
	
	@Id
	private String nome;

	
	public Role() {
		super();
	}

	public Role(String nome) {
		super();
		this.nome = nome;
	}

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getAuthority() {
		return this.nome;
	}

}
