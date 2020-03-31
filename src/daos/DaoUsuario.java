/**
 * 
 */
package daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanTipoUsuario;
import beans.BeanUsuario;
import connection.SingleConnection;

/**
 * @author david
 *
 */
public class DaoUsuario {

	Connection connection;

	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanUsuario usuario) {

		String sql = "insert into usuario (nome, login, senha, tipo_usuario_id) values(?, ?, ?, ?)";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, usuario.getNome());
			statement.setString(2, usuario.getLogin());
			statement.setString(3, usuario.getSenha());
			statement.setLong(4, usuario.getTipo_usuario().getCodTipo());

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

	public List<BeanTipoUsuario> listaTipoUsuario() throws Exception {
		List<BeanTipoUsuario> retorno = new ArrayList<BeanTipoUsuario>();

		String sql = "select id, descricao from tipo_usuario";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			BeanTipoUsuario categoria = new BeanTipoUsuario();
			categoria.setCodTipo(resultSet.getLong("id"));
			categoria.setDescricao(resultSet.getString("descricao"));

			retorno.add(categoria);
		}

		return retorno;

	}

	public List<BeanUsuario> listar() throws Exception {
		List<BeanUsuario> lista = new ArrayList<BeanUsuario>();

		String sql = "select us.id, us.nome, us.login, us.senha, tp.descricao\n"
				+ "	from usuario us left outer join tipo_usuario tp on us.tipo_usuario_id = tp.id;";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			BeanUsuario usuario = new BeanUsuario();
			usuario.setCodUsuario(resultSet.getLong("id"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.getTipo_usuario().setDescricao(resultSet.getString("descricao"));

			lista.add(usuario);

		}

		return lista;
	}

	public BeanUsuario consultar(String codUsuario) throws Exception { // consulta para atualização

		String sql = ("select * from usuario where codUsuario ='" + codUsuario + "'");

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			BeanUsuario usuario = new BeanUsuario();
			usuario.setCodUsuario(resultSet.getLong("codUsuario"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.getTipo_usuario().setCodTipo(resultSet.getLong("tipo_usuario"));

			return usuario;
		}
		return null;
	}

	public void atualizar(BeanUsuario usuario) {
		String sql = "update usuario set nome = ?, login = ?, senha = ?, tipo_usuario = ? where codUsuario = "
				+ usuario.getCodUsuario();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, usuario.getNome());
			statement.setString(2, usuario.getLogin());
			statement.setString(3, usuario.getSenha());
			statement.setLong(4, usuario.getTipo_usuario().getCodTipo());

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

	public void delete(String codUsuario) {
		try {
			String sql = "delete from usuario where codUsuario = '" + codUsuario + "'";

			PreparedStatement statement = connection.prepareStatement(sql);
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

	public void pSalvar(BeanUsuario usuario) {
		String sql = "select insereUsuario(?, ?, ?, ?)";

		try {
			CallableStatement callableStatement = connection.prepareCall(sql);

			callableStatement.setString(1, usuario.getNome());
			callableStatement.setString(2, usuario.getLogin());
			callableStatement.setString(3, usuario.getSenha());
			callableStatement.setLong(4, usuario.getTipo_usuario().getCodTipo());
			callableStatement.execute();

			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();

			try {
				connection.rollback();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
