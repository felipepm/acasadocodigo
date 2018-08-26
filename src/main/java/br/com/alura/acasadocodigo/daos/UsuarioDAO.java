package br.com.alura.acasadocodigo.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.alura.acasadocodigo.models.Role;
import br.com.alura.acasadocodigo.models.Usuario;

@Repository
public class UsuarioDAO implements UserDetailsService {

	@PersistenceContext
	private EntityManager manager;
	
	public Usuario loadUserByUsername(String email) {
		insert();
		
		List<Usuario> usuarios = manager.createQuery("select u from Usuario u where u.email = :email", Usuario.class).setParameter("email", email).getResultList();
		
		if (usuarios.isEmpty()) {
			throw new UsernameNotFoundException("Usuário " + email + " não foi encontrado");
		}
		
		return usuarios.get(0);
	}
	
	private void insert() {
		try {
			List<Role> roles = new ArrayList<>();
			roles.add(new Role("ROLE_ADMIN"));
			Usuario usuario = new Usuario();
			usuario.setEmail("admin@casadocodigo.com.br");
			usuario.setSenha("$2a$04$qP517gz1KNVEJUTCkUQCY.JzEoXzHFjLAhPQjrg5iP6Z/UmWjvUhq");
			usuario.setRoles(roles);
			manager.persist(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
