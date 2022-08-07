package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exception.FilmeSemEstoqueException;
import br.ce.wcaquino.exception.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.*;

import static br.ce.wcaquino.matchers.MatcherProprios.caiNumaSegunda;
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
	public void deveAlugarFilme() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		//cenario
		Usuario usuario1 = new Usuario("Pedro Marques");

		Filme filme1 = new  Filme("Filme 1",5,5.0);
		Filme filme2 = new  Filme("Filme 2",10,5.0);
		Filme filme3 = new  Filme("Filme 3",10,5.0);

		listFilme = Arrays.asList(filme1,filme2,filme3);
		//ação
		Locacao locacao = service.alugarFilme(usuario1, listFilme);

		//verificação

		/* OBS: para cada teste deve conter apenas 1 asertiva, e cada método pode ser testado sozinho
		 para ser identificado a falha no teste com mais facilidade, abaixo estão sendo tetados 3 de uma vez, facilicar
		a leitura caso der erro utiliza checkThat, listará todos os erros gerados.*/

//		assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(4.2)));
//		assertThat(isMesmaData(locacao.getDataLocacao(), new Date()),CoreMatchers.is(true));
//		assertThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), CoreMatchers.not(true));

		error.checkThat(locacao.getValor(), is(equalTo(13.75)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), not(false));

	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		//cenario
		Usuario usuario = new Usuario("Pedro Marques");
		Filme filme = new Filme("Filme 2",0,5.0);
		listFilme.add(filme);
		//ação
		Locacao locacao = service.alugarFilme(usuario,listFilme);
	}

	@Test
	public void naoDeveAlugarFilmeSemUsuario() {
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
	public void naoDeveAlugarFilmeSemFilme() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		Usuario usuario = new Usuario("Maria Jose");

		//ação
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		service.alugarFilme(usuario,null);
	}

	@Test
	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = new Usuario("usuario1");

		List<Filme> filmes = Arrays.asList(
				new Filme("Filme1", 2,4.0),
				new Filme("Filme2", 2,4.0),
				new Filme("Filme3", 2,4.0)
		);

		Locacao resultado = service.alugarFilme(usuario,filmes);

		assertThat(resultado.getValor(),is(11.0));
	}

	@Test
	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = new Usuario("usuario1");

		List<Filme> filmes = Arrays.asList(
				new Filme("Filme1", 2,4.0),
				new Filme("Filme2", 2,4.0),
				new Filme("Filme3", 2,4.0),
				new Filme("Filme4", 2,4.0)
		);

		Locacao resultado = service.alugarFilme(usuario,filmes);

		assertThat(resultado.getValor(),is(13.0));
	}

	@Test
	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = new Usuario("usuario1");

		List<Filme> filmes = Arrays.asList(
				new Filme("Filme1", 2,4.0),
				new Filme("Filme2", 2,4.0),
				new Filme("Filme3", 2,4.0),
				new Filme("Filme4", 2,4.0),
				new Filme("Filme5", 2,4.0)
		);
		Locacao resultado = service.alugarFilme(usuario,filmes);

		assertThat(resultado.getValor(),is(14.0));
	}

	@Test
	public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = new Usuario("usuario1");

		List<Filme> filmes = Arrays.asList(
				new Filme("Filme1", 2,4.0),
				new Filme("Filme2", 2,4.0),
				new Filme("Filme3", 2,4.0),
				new Filme("Filme4", 2,4.0),
				new Filme("Filme5", 2,4.0),
				new Filme("Filme6", 2,4.0)
		);

		Locacao resultado = service.alugarFilme(usuario,filmes);

		assertThat(resultado.getValor(),is(14.0));
	}


	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		Usuario usuario = new Usuario("usuario1");

		List<Filme> filmes = Arrays.asList(new Filme("Filme1", 2,4.0));

		Locacao resultado = service.alugarFilme(usuario,filmes);

		assertThat(resultado.getDataRetorno(), caiNumaSegunda());


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