package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.StatusOrdemServico;
import connection.SingleConnection;

public class DaoStatusOrdemServico {

	private Connection connection;

	public DaoStatusOrdemServico() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Long ordem_servico_id, Long operacao_id) {
		String sql = "insert into status_ordem_servico (ordem_servico_id, operacao_id) values(?, ?)";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, ordem_servico_id);
			preparedStatement.setLong(2, operacao_id);
			preparedStatement.execute();

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
	}

	public void atualizar(Long osId, Long OperacaoAtual, Long operacaoAtualizada) {
		String sql = "update status_ordem_servico set ordem_servico_id = ?, operacao_id = ? where ordem_servico_id = '" + osId
				+ "' and operacao_id = '" + OperacaoAtual + "'";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, osId);
			preparedStatement.setLong(2, operacaoAtualizada);
			preparedStatement.executeUpdate();

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();

			try {
				connection.rollback();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<StatusOrdemServico> consultarPorId(Long ordem_servico_id) throws SQLException {
		String sql = "select * from status_ordem_servico where ordem_servico_id = '" + ordem_servico_id + "'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		List<StatusOrdemServico> status = new ArrayList<>();

		while (resultSet.next()) {
			StatusOrdemServico statusOrdemServico = new StatusOrdemServico();
			statusOrdemServico.setOperacao(resultSet.getLong("operacao_id"));
			statusOrdemServico.setOrdemServico(resultSet.getLong("ordem_servico_id"));

			status.add(statusOrdemServico);
		}

		return status;

	}

}
