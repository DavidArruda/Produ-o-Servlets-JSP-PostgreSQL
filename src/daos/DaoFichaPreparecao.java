/**
 * 
 */
package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanFichaPreparacao;
import connection.SingleConnection;

/**
 * @author david
 *
 */
public class DaoFichaPreparecao {
	
	Connection connection;
	
	public DaoFichaPreparecao() {
		connection = SingleConnection.getConnection();
	}
	
	
	public void salvar(BeanFichaPreparacao fichaPreparacao) {
		
		String sql = "insert into ficha_preparacao (f_base64, f_content_type, operacao_id) values(?, ?, ?)";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, fichaPreparacao.getfBase64());
			statement.setString(2, fichaPreparacao.getfContentType());
			statement.setLong(3, fichaPreparacao.getOperacao_id().getId());
			statement.execute();
			
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
	
	public List<BeanFichaPreparacao> listar() throws Exception {
		List<BeanFichaPreparacao> lista = new ArrayList<BeanFichaPreparacao>();

		String sql = "select * from ficha_preparacao";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			BeanFichaPreparacao fichaPreparacao = new BeanFichaPreparacao();
			fichaPreparacao.setId(resultSet.getLong("id"));
			fichaPreparacao.setfBase64(resultSet.getString("f_base64"));
			fichaPreparacao.setfContentType(resultSet.getString("f_content-type"));

			lista.add(fichaPreparacao);

		}

		return lista;
	}

	public BeanFichaPreparacao consultar(String id) throws Exception { //consulta para atualização

		String sql = ("select * from ficha_de_preparacao where id ='" + id + "'");

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			BeanFichaPreparacao fichaPreparacao = new BeanFichaPreparacao();
			fichaPreparacao.setId(resultSet.getLong("id"));
			fichaPreparacao.setfBase64(resultSet.getString("f_base64"));
			fichaPreparacao.setfContentType(resultSet.getString("f_content-type"));

			return fichaPreparacao;
		}
		return null;
	}
	
	public BeanFichaPreparacao consultarPorOperacao(String operacaoId) throws Exception {

		String sql = ("select f_base64, f_content_type from ficha_preparacao where operacao_id ='" + operacaoId + "'");

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			BeanFichaPreparacao ficha = new BeanFichaPreparacao();
			ficha.setfBase64(resultSet.getString("f_base64"));
			ficha.setfContentType(resultSet.getString("f_content_type"));

			return ficha;
		}
		return null;
	}

	public void atualizar(BeanFichaPreparacao fichaPreparacao) {
		String sql = "update ficha_de_preparacao set f_base64 = ?, f_content_type = ? where id = " + fichaPreparacao.getId();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, fichaPreparacao.getfBase64());
			statement.setString(2, fichaPreparacao.getfContentType());

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

	public void delete(String operacaoId) {
		try {
			String sql = "delete from ficha_preparacao where operacao_id = '" + operacaoId + "'";

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
