package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exception.FilmeSemEstoqueException;
import br.ce.wcaquino.exception.LocadoraException;

import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

public class LocacaoService {

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException,LocadoraException {


		if(usuario == null){
			throw new LocadoraException("Usuário vazio");
		}

		if(filmes == null || filmes.isEmpty()){
			throw new LocadoraException("Filme vazio");
		}

		if(filmes.stream().map(Filme::getEstoque).anyMatch(estoque -> estoque == 0)){
			throw new FilmeSemEstoqueException();
		}

		Locacao locacao = new Locacao();

		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filmes.stream().map(Filme::getPrecoLocacao).mapToDouble(s -> Double.parseDouble(s.toString())).sum());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);

		//Salvando a locacao...
		//TODO adicionar método para salvar

		return locacao;
	}

}