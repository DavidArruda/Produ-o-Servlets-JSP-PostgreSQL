package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanProduto;
import connection.SingleConnection;

public class DaoProduto {

	Connection connection;

	public DaoProduto() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanProduto produto) {

		String sql = "insert into produto (pn, cliente, descricao) values (?, ?, ?)";

		try {

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produto.getPn());
			statement.setString(2, produto.getCliente());
			statement.setString(3, produto.getDescricao());

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

	public List<BeanProduto> listar() throws Exception {
		List<BeanProduto> lista = new ArrayList<BeanProduto>();

		String sql = "select * from produto";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			BeanProduto produto = new BeanProduto();
			produto.setId(resultSet.getLong("id"));
			produto.setPn(resultSet.getString("pn"));
			produto.setCliente(resultSet.getString("cliente"));
			produto.setDescricao(resultSet.getString("descricao"));

			lista.add(produto);

		}

		return lista;
	}

	public BeanProduto consultar(String id) throws Exception { //consulta para atualização

		String sql = ("select * from produto where id ='" + id + "'");

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			BeanProduto produto = new BeanProduto();
			produto.setId(resultSet.getLong("id"));
			produto.setPn(resultSet.getString("pn"));
			produto.setCliente(resultSet.getString("cliente"));
			produto.setDescricao(resultSet.getString("descricao"));

			return produto;
		}
		return null;
	}

	public void atualizar(BeanProduto produto) {
		String sql = "update produto set pn = ?, cliente = ?, descricao = ? where id = " + produto.getId();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, produto.getPn());
			statement.setString(2, produto.getCliente());
			statement.setString(3, produto.getDescricao());

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

	public void delete(String id) {
		try {
			String sql = "delete from produto where id = '" + id + "'";

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

}
