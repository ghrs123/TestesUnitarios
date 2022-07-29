package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exception.FilmeSemEstoqueException;
import br.ce.wcaquino.exception.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("AssertBetweenInconvertibleTypes")
public class LocacaoServiceTest {

	private LocacaoService service ;
	private List<Filme> listFilme;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup(){
		service = new LocacaoService();
		listFilme = new ArrayList<>();
	}



	@Test
	public void testeLocacao() throws Exception {
		//cenario
		Usuario usuario1 = new Usuario("Pedro Marques");
		Filme filme = new  Filme("Filme 1",5,5.0);

		listFilme.add(filme);
		//ação
		Locacao locacao = service.alugarFilme(usuario1, listFilme);

		//verificação

		/* OBS: para cada teste deve conter apenas 1 asertiva, e cada método pode ser testado sozinho
		 para ser identificado a falha no teste com mais facilidade, abaixo estão sendo tetados 3 de uma vez, facilicar
		a leitura caso der erro utiliza checkThat, listará todos os erros gerados.*/

//		assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(4.2)));
//		assertThat(isMesmaData(locacao.getDataLocacao(), new Date()),CoreMatchers.is(true));
//		assertThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), CoreMatchers.not(true));

		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), not(false));

	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		//cenario
		Usuario usuario = new Usuario("Pedro Marques");
		Filme filme = new Filme("Filme 2",0,5.0);
		listFilme.add(filme);
		//ação
		Locacao locacao = service.alugarFilme(usuario,listFilme);
	}

	@Test
	public void testeLocacao_usuarioVazio() {
		//cenario
		Filme filme = new Filme("Filme 2",2,5.0);
		listFilme.add(filme);
		//ação
		try {
			Locacao locacao = service.alugarFilme(null,listFilme);
			Assert.fail();

		} catch (Exception e) {
			assertThat(e.getMessage(), is("Usuário vazio"));
		}
	}

	@Test
	public void testeLocacao_filmeVazio() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		Usuario usuario = new Usuario("Maria Jose");

		//ação
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		service.alugarFilme(usuario,null);
	}


//	@Test(expected = Exception.class) //Tratar a excessão, dizer que o teste espera uma exceção, estoque vazio
//	public void testeLocacao_filmeSemEstoque() throws Exception {
//
//		//cenario
//		LocacaoService service = new LocacaoService();
//		Usuario usuario1 = new Usuario("Pedro Marques");
//		Filme filme1 = new Filme("Filme 1",0,5.0);
//
//		//ação
//		Locacao locacao = service.alugarFilme(usuario1,filme1);
//	}


//	@Test
//	public void testeLocacao_filmeSemEstoque_2() {
//
//		//cenario
//		LocacaoService service = new LocacaoService();
//		Usuario usuario1 = new Usuario("Pedro Marques");
//		Filme filme1 = new Filme("Filme 1",0,5.0);
//
//		//ação
//		try {
//			Locacao locacao = service.alugarFilme(usuario1,filme1);
//			Assert.fail("Deveria ter lançado uma exceçãp!");
//		} catch (Exception e) {
//			assertThat(e.getMessage(), is("Filme sem estoque."));
//		}
//	}


//	@Test
//	public void testeLocacao_filmeSemEstoque_3() throws Exception {
//
//		//cenario
//		LocacaoService service = new LocacaoService();
//		Usuario usuario1 = new Usuario("Pedro Marques");
//		Filme filme1 = new Filme("Filme 1",0,5.0);
//
//		exception.expect(Exception.class);
//		exception.expectMessage("Filme sem estoque.");
//		//ação
//		Locacao locacao = service.alugarFilme(usuario1,filme1);
//	}

}