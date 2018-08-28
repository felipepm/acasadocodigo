package br.com.alura.acasadocodigo.daos;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.acasadocodigo.builders.ProdutoBuilder;
import br.com.alura.acasadocodigo.conf.DataSourceConfigurationTest;
import br.com.alura.acasadocodigo.conf.JPAConfiguration;
import br.com.alura.acasadocodigo.models.Produto;
import br.com.alura.acasadocodigo.models.TipoPreco;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {JPAConfiguration.class, ProdutoDAO.class, DataSourceConfigurationTest.class})
@ActiveProfiles("test")
public class ProdutoDAOTest {
	
	@Autowired
	private ProdutoDAO produtoDAO;

	@Test
	@Transactional
	public void deveSomarTodosPrecosPorTipoLivro() {
		List<Produto> livrosImpresso = ProdutoBuilder.newProduto(TipoPreco.IMPRESSO, BigDecimal.TEN).more(3).buildAll();
		
		List<Produto> livrosEbook = ProdutoBuilder.newProduto(TipoPreco.EBOOK, BigDecimal.TEN).more(3).buildAll();
		
		livrosImpresso.stream().forEach(produtoDAO::gravar);
		livrosEbook.stream().forEach(produtoDAO::gravar);
		
		BigDecimal valorEbook = produtoDAO.somaPrecosPorTipoPreco(TipoPreco.EBOOK);
		Assert.assertEquals(new BigDecimal(40).setScale(2), valorEbook);
	}
}
