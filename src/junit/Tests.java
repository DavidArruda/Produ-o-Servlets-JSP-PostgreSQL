package junit;

import java.sql.Connection;

import org.junit.Test;

import connection.SingleConnection;
import daos.DaoApontamento;
import daos.DaoOperacao;
import daos.DaoOrdemServico;
import daos.DaoStatusOrdemServico;

public class Tests {

	Connection connection = SingleConnection.getConnection();

	private DaoStatusOrdemServico daoStatusOs = new DaoStatusOrdemServico();
	
	@SuppressWarnings("unused")
	private DaoOperacao daoOperacao = new DaoOperacao();
	
	@SuppressWarnings("unused")
	private DaoOrdemServico daoOrdemServico = new DaoOrdemServico();
	
	@SuppressWarnings("unused")
	private DaoApontamento daoApontamento = new DaoApontamento();

	@Test
	public void atualizar() {
			daoStatusOs.atualizar(3L, 1L, 5L);

	}

}
