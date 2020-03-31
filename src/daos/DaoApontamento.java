package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanApontamento;
import connection.SingleConnection;

public class DaoApontamento {

	private Connection connection;

	public DaoApontamento() {
		connection = SingleConnection.getConnection();
	}

	// APONTAMENTO COM QUANTIDA MENOR QUE A QUANTIDADE DA O.S
	private void salvarApontamento(BeanApontamento ap) {
		// APONTAMENTO COM A QUANTIDADE INFORMADA PELO USUÁRIO E SETANTO A PROXIMA OP
		String sql = "insert into apontamento (inicio, termino, usuario_id, os_id, data_ap, quantidade, operacao_id)"
				+ " values (?, ?, ?, ?, ?, ?, ?)";

		try {
			DaoOperacao daoOperacao = new DaoOperacao();
			Long proximaOperacao = daoOperacao.consultarProximaOp(ap.getOrdemServico().getProdutoId().getId(),
					ap.getOperacao().getId());
			
			PreparedStatement statement;
			statement = connection.prepareStatement(sql);

			statement.setTime(1, (ap.getInicio()));
			statement.setTime(2, (ap.getTermino()));
			statement.setLong(3, ap.getUsuario().getCodUsuario());
			statement.setLong(4, ap.getOrdemServico().getId());
			statement.setDate(5, ap.getDataAp());
			statement.setInt(6, ap.getQuantidade());
			statement.setLong(7, proximaOperacao);
			statement.execute();

			connection.commit();

			DaoStatusOrdemServico daoStatusOrdemServicome = new DaoStatusOrdemServico();
			daoStatusOrdemServicome.atualizar(ap.getOrdemServico().getId(), ap.getOperacao().getId(), proximaOperacao);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void salvarApontamentoQtdRestante(BeanApontamento apontamento) {
		String sql = "insert into apontamento (inicio, termino, usuario_id, os_id, data_ap, quantidade, operacao_id)"
				+ " values (?, ?, ?, ?, ?, ?, ?)";

		// APONTAMENTO COM A QUANTIDADE RESTANTE E COM A MESMA OP INICIAL
		int qtdOs = apontamento.getOrdemServico().getQuantidade();
		int qtdApontada = apontamento.getQuantidade();
		final int qtdRestante = qtdOs - qtdApontada;

		PreparedStatement statement2;
		try {
			statement2 = connection.prepareStatement(sql);

			statement2.setTime(1, (apontamento.getInicio()));
			statement2.setTime(2, (apontamento.getTermino()));
			statement2.setLong(3, apontamento.getUsuario().getCodUsuario());
			statement2.setLong(4, apontamento.getOrdemServico().getId());
			statement2.setDate(5, apontamento.getDataAp());
			statement2.setInt(6, qtdRestante);
			statement2.setLong(7, apontamento.getOperacao().getId());
			statement2.execute();

			connection.commit();

			DaoStatusOrdemServico daoStatusOrdemServicome = new DaoStatusOrdemServico();
			daoStatusOrdemServicome.salvar(apontamento.getOrdemServico().getId(), apontamento.getOperacao().getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void salvar(BeanApontamento apontamento) {

		String sql = "insert into apontamento (inicio, termino, usuario_id, os_id, data_ap, quantidade)"
				+ " values (?, ?, ?, ?, ?, ?)";

		try {
			if (apontamento.getQuantidade() < apontamento.getOrdemServico().getQuantidade()) {
				salvarApontamento(apontamento);
				salvarApontamentoQtdRestante(apontamento);

			} else if (apontamento.getQuantidade() == apontamento.getOrdemServico().getQuantidade()) {
				DaoOperacao daoOperacao = new DaoOperacao();
				Long proximaOperacao = daoOperacao.consultarProximaOp(
				apontamento.getOrdemServico().getProdutoId().getId(), apontamento.getOperacao().getId());
				
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setTime(1, (apontamento.getInicio()));
				statement.setTime(2, (apontamento.getTermino()));
				statement.setLong(3, apontamento.getUsuario().getCodUsuario());
				statement.setLong(4, apontamento.getOrdemServico().getId());
				statement.setDate(5, apontamento.getDataAp());
				statement.setInt(6, apontamento.getQuantidade());
				statement.execute();

				connection.commit();

				DaoStatusOrdemServico daoStatusOrdemServicome = new DaoStatusOrdemServico();
				daoStatusOrdemServicome.atualizar(apontamento.getOrdemServico().getId(),
				apontamento.getOperacao().getId(), proximaOperacao);

			}

		} catch (

		Exception e) {
			e.printStackTrace();

			try {
				connection.rollback();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<BeanApontamento> listar() throws SQLException {
		String sql = "select * from apontamento";

		List<BeanApontamento> apontamentos = new ArrayList<BeanApontamento>();

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			BeanApontamento apontamento = new BeanApontamento();
			apontamento.setId(resultSet.getLong("id"));
			apontamento.setInicio(resultSet.getTime("inicio"));
			apontamento.setTermino(resultSet.getTime("termino"));
			apontamento.getOrdemServico().setId(resultSet.getLong("os_id"));
			apontamento.getUsuario().setCodUsuario(resultSet.getLong("usuario_id"));
			apontamento.setDataAp(resultSet.getDate("data_ap"));
			apontamento.setQuantidade(resultSet.getInt("quantidade"));
			apontamento.getOperacao().setId(resultSet.getLong("operacao_id"));
			apontamentos.add(apontamento);
		}

		return apontamentos;
	}

	public List<BeanApontamento> consultaPorId(String ordemServicoId) throws SQLException {
		String sql = "select ap.id, ap.inicio, ap.termino, ap.os_id, ap.usuario_id, ap.data_ap, ap.quantidade, ap.operacao_id, op.n_operacao from apontamento ap "
				+ "left outer join operacao op on op.id = ap.operacao_id where os_id = '" + ordemServicoId + "'";

		List<BeanApontamento> apontamentos = new ArrayList<BeanApontamento>();

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			BeanApontamento apontamento = new BeanApontamento();
			apontamento.setId(resultSet.getLong("id"));
			apontamento.setInicio(resultSet.getTime("inicio"));
			apontamento.setTermino(resultSet.getTime("termino"));
			apontamento.getOrdemServico().setId(resultSet.getLong("os_id"));
			apontamento.getUsuario().setCodUsuario(resultSet.getLong("usuario_id"));
			apontamento.setDataAp(resultSet.getDate("data_ap"));
			apontamento.setQuantidade(resultSet.getInt("quantidade"));
			apontamento.getOperacao().setId(resultSet.getLong("operacao_id"));
			apontamento.getOperacao().setN_operacao(resultSet.getInt("n_operacao"));

			apontamentos.add(apontamento);
		}

		return apontamentos;
	}

}
