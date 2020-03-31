package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.BeanDesenho;
import connection.SingleConnection;

public class DaoDesenho {

	private Connection connection;

	public DaoDesenho() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanDesenho desenho) {
		String sql = "insert into desenho (d_base64, d_base64_miniatura, f_content_type, operacao_id) values(?, ?, ?, ?)";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, desenho.getdBase64());
			statement.setString(2, desenho.getdBase64Miniatura());
			statement.setString(3, desenho.getfContentType());
			statement.setLong(4, desenho.getOperacao_id().getId());
			statement.execute();

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public void atualizar(BeanDesenho desenho) {
		String sql = "update desenho set d_base64 = ?, d_base64_miniatura = ?, f_content_type = ?, operacao_id = ? where id = "
				+ desenho.getId();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, desenho.getdBase64());
			statement.setString(2, desenho.getdBase64Miniatura());
			statement.setString(3, desenho.getfContentType());
			statement.executeUpdate();

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public BeanDesenho consultarPorOperacao(String operacaoId) throws Exception { // consulta para atualização

		String sql = ("select d_base64, d_base64_miniatura, f_content_type from desenho where operacao_id ='"
				+ operacaoId + "'");

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			BeanDesenho desenho = new BeanDesenho();
			desenho.setdBase64(resultSet.getString("d_base64"));
			desenho.setdBase64Miniatura(resultSet.getString("d_base64_miniatura"));
			desenho.setfContentType(resultSet.getString("f_content_type"));

			return desenho;
		}
		return null;
	}

	public void delete(String operacaoId) {
		try {
			String sql = "delete from desenho where operacao_id = '" + operacaoId + "'";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();

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

}
