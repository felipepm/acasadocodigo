package br.com.alura.acasadocodigo.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.acasadocodigo.models.CarrinhoCompras;
import br.com.alura.acasadocodigo.models.DadosPagamento;
import br.com.alura.acasadocodigo.models.Usuario;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {

	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MailSender sender;

	@RequestMapping(value="/finalizar", method=RequestMethod.POST)
	public Callable<ModelAndView> finalizar(@AuthenticationPrincipal Usuario usuario, RedirectAttributes model) {
		return () -> {
			try {
		        String uri = "http://book-payment.herokuapp.com/payment";
		        String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), String.class);
		        model.addFlashAttribute("sucesso", response);
		        System.out.println(response);
		        enviaEmailCompraProduto(usuario);
		        return new ModelAndView("redirect:/produtos");
		    } catch (HttpClientErrorException e) {
		        e.printStackTrace();
		        model.addFlashAttribute("falha", "Valor maior que o permitido");
		        return new ModelAndView("redirect:/produtos");
		    }
		};
	}

	private void enviaEmailCompraProduto(Usuario usuario) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject("Compra finalizada com sucesso");
		email.setTo(usuario.getEmail());
		email.setText("Compra aprovada com sucesso no valor de " + carrinho.getTotal());
		email.setFrom("compras@casadocodigo.com.br");
		
		sender.send(email);
	}
}
