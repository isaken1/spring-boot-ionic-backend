package com.isaackennedy.curso;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.isaackennedy.curso.domain.Categoria;
import com.isaackennedy.curso.domain.Cidade;
import com.isaackennedy.curso.domain.Cliente;
import com.isaackennedy.curso.domain.Endereco;
import com.isaackennedy.curso.domain.Estado;
import com.isaackennedy.curso.domain.Pagamento;
import com.isaackennedy.curso.domain.PagamentoComBoleto;
import com.isaackennedy.curso.domain.PagamentoComCartao;
import com.isaackennedy.curso.domain.Pedido;
import com.isaackennedy.curso.domain.Produto;
import com.isaackennedy.curso.domain.enums.EstadoPagamento;
import com.isaackennedy.curso.domain.enums.TipoCliente;
import com.isaackennedy.curso.repositories.CategoriaRepository;
import com.isaackennedy.curso.repositories.CidadeRepository;
import com.isaackennedy.curso.repositories.ClienteRepository;
import com.isaackennedy.curso.repositories.EnderecoRepository;
import com.isaackennedy.curso.repositories.EstadoRepository;
import com.isaackennedy.curso.repositories.PagamentoRepository;
import com.isaackennedy.curso.repositories.PedidoRepository;
import com.isaackennedy.curso.repositories.ProdutoRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository catRepository;
	
	@Autowired
	private ProdutoRepository prodRepository;
	
	@Autowired
	private EstadoRepository estRepository;
	
	@Autowired
	private CidadeRepository citRepository;
	
	@Autowired
	private ClienteRepository cliRepo;
	
	@Autowired
	private EnderecoRepository endRepo;
	
	@Autowired
	private PedidoRepository pedRepo;
	
	@Autowired 
	private PagamentoRepository pgtoRepo;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().add(cat2);
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		catRepository.saveAll(Arrays.asList(cat1, cat2));
		prodRepository.saveAll(Arrays.asList(p1, p2, p3));
		estRepository.saveAll(Arrays.asList(est1, est2));
		citRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "3877012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		cliRepo.save(cli1);
		endRepo.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);
		
		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pgto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedRepo.saveAll(Arrays.asList(ped1, ped2));
		
		pgtoRepo.saveAll(Arrays.asList(pgto1, pgto2));
		
	}
}
