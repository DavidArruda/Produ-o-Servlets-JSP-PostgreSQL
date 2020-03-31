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

import beans.BeanOrdemServico;
import beans.BeanProduto;
import connection.SingleConnection;

/**
 * @author david
 *
 */
public class DaoOrdemServico {

	Connection connection;

	public DaoOrdemServico() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanOrdemServico os) {

		String sql = "insert into ordem_servico (data_emissao, data_entrega, quantidade, status, produto_id) values(?, ?, ?, ?, ?)";

		try {

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDate(1, new java.sql.Date(os.getDataEmissao().getTime()));
			statement.setDate(2, new java.sql.Date(os.getDataEntrega().getTime()));
			statement.setInt(3, os.getQuantidade());
			statement.setString(4, os.getStatus());
			statement.setLong(5, os.getProdutoId().getId());

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

	public List<BeanProduto> listaProdutos() throws Exception {
		List<BeanProduto> retorno = new ArrayList<BeanProduto>();

		String sql = "select id, pn from produto";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			BeanProduto produto = new BeanProduto();
			produto.setId(resultSet.getLong("id"));
			produto.setPn(resultSet.getString("pn"));

			retorno.add(produto);
		}

		return retorno;

	}

	public List<BeanOrdemServico> listar() throws Exception {
		List<BeanOrdemServico> lista = new ArrayList<BeanOrdemServico>();

		String sql = "select os.id, os.data_emissao, os.data_entrega, os.quantidade, os.status, pr.pn"
				+ " from ordem_servico os left outer join produto pr on os.produto_id = pr.id;";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			BeanOrdemServico os = new BeanOrdemServico();
			os.setId(resultSet.getLong("id"));
			os.setDataEmissao(resultSet.getDate("data_emissao"));
			os.setDataEntrega(resultSet.getDate("data_entrega"));
			os.setQuantidade(resultSet.getInt("quantidade"));
			os.setStatus(resultSet.getString("status"));
			os.getProdutoId().setPn(resultSet.getString("pn"));

			lista.add(os);

		}

		return lista;
	}

	public BeanOrdemServico consultar(String id) throws Exception { // consulta para atualização

		String sql = ("select * from ordem_servico where id ='" + id + "'");

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		BeanOrdemServico os = new BeanOrdemServico();
		
		if (resultSet.next()) {
			os.setId(resultSet.getLong("id"));
			os.setDataEmissao(resultSet.getDate("data_emissao"));
			os.setDataEntrega(resultSet.getDate("data_entrega"));
			os.setQuantidade(resultSet.getInt("quantidade"));
			os.setStatus(resultSet.getString("status"));
			os.getProdutoId().setId(resultSet.getLong("produto_id"));
		}
		
		return os;
	}
	
	public List<BeanOrdemServico> consultarPorProduto(String produtoId) throws Exception { // consulta para atualização

		String sql = ("select * from ordem_servico where produto_id ='" + produtoId + "'");

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		List<BeanOrdemServico > listaOs = new ArrayList<>();
		
		if (resultSet.next()) {
			BeanOrdemServico os = new BeanOrdemServico();
			os.setId(resultSet.getLong("id"));
			os.setDataEmissao(resultSet.getDate("data_emissao"));
			os.setDataEntrega(resultSet.getDate("data_entrega"));
			os.setQuantidade(resultSet.getInt("quantidade"));
			os.setStatus(resultSet.getString("status"));
			os.getProdutoId().setId(resultSet.getLong("produto_id"));
			
			listaOs.add(os);
		}
		
		return listaOs;
	}

	public void atualizar(BeanOrdemServico os) {
		String sql = "update ordem_servico set data_emissao = ?, data_entrega = ?, quantidade = ?, status = ?, produto_id = ? where id = "
				+ os.getId();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setDate(1, new java.sql.Date(os.getDataEmissao().getTime()));
			statement.setDate(2, new java.sql.Date(os.getDataEntrega().getTime()));
			statement.setInt(3, os.getQuantidade());
			statement.setString(4, os.getStatus());
			statement.setLong(5, os.getProdutoId().getId());

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
	
	public BeanOrdemServico consultaParaApontar(String idOs) throws Exception {
		
		DaoOrdemServico daoOrdemServico = new DaoOrdemServico();
		BeanOrdemServico beanOs = new BeanOrdemServico();
		try {
		BeanOrdemServico beanOrdemServico = daoOrdemServico.consultar(idOs);
		
		//List<BeanOrdemServico> listaOS = daoOrdemServico.consultarPorProduto(beanOrdemServico.getProdutoId().getId().toString());
		
		String sql = "SELECT os.produto_id, pr.pn, op.id, op.descricao, op.n_operacao \n" + 
				"FROM ordem_servico os\n" + 
				"LEFT OUTER JOIN produto pr ON pr.id = os.produto_id\n" + 
				"LEFT OUTER JOIN operacao op ON pr.id = op.produto_id\n" + 
				"WHERE os.produto_id = '" +beanOrdemServico.getProdutoId().getId()+ "'\n" + 
				"ORDER BY op.n_operacao;";
		
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				beanOs.getProdutoId().setId(resultSet.getLong("produto_id"));
				beanOs.getProdutoId().setPn(resultSet.getString("pn"));
				beanOs.getProdutoId().setDescricao(resultSet.getString("descricao"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}		
		return beanOs;
	}

	public void delete(String id) {
		try {
			String sql = "delete from ordem_servico where id = '" + id + "'";

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
