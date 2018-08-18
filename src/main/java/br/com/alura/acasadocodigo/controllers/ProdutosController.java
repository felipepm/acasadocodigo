package br.com.alura.acasadocodigo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.acasadocodigo.daos.ProdutoDAO;
import br.com.alura.acasadocodigo.models.Produto;

@Controller
public class ProdutosController {
	
	
	@Autowired
	private ProdutoDAO dao;

	@RequestMapping("/produtos/form")
	public String form() {
		return "produtos/form";
	}
	
	@RequestMapping("/produtos")
	public String gravar(Produto produto){
		System.out.println(produto);
		dao.gravar(produto);
		return "/produtos/ok";
	}
}
