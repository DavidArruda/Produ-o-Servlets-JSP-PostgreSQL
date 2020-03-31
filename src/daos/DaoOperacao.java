/**
 * 
 */
package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.BeanDesenho;
import beans.BeanFichaPreparacao;
import beans.BeanOperacao;
import beans.BeanProduto;
import connection.SingleConnection;

/**
 * @author david
 *
 */
public class DaoOperacao {

	Connection connection;

	public DaoOperacao() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanOperacao operacao, BeanFichaPreparacao fichaPreparacao, BeanDesenho desenho) {

		String sql = "insert into operacao (descricao, maquina, n_operacao,  tempo_estimado, produto_id) values(?, ?, ? ,? ,?)";

		try {
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, operacao.getDescricao());
			statement.setString(2, operacao.getMaquina());
			statement.setInt(3, operacao.getN_operacao());
			statement.setTime(4, operacao.getTempo_estimado());
			statement.setLong(5, operacao.getProduto_id().getId());
			statement.execute();

			final ResultSet rs = statement.getGeneratedKeys();

			DaoFichaPreparecao daoFichaPreparecao = new DaoFichaPreparecao();
			DaoDesenho daoDesenho = new DaoDesenho();

			if (fichaPreparacao.getfBase64() != null && desenho.getdBase64() != null) {
				if (rs.next()) {
					fichaPreparacao.getOperacao_id().setId(rs.getLong("id"));
					desenho.getOperacao_id().setId(rs.getLong("id"));
				}

				daoFichaPreparecao.salvar(fichaPreparacao);
				daoDesenho.salvar(desenho);

				connection.commit();

			} else if (fichaPreparacao.getfBase64() != null && desenho.getdBase64() == null) {
				if (rs.next()) {
					fichaPreparacao.getOperacao_id().setId(rs.getLong("id"));
				}

				daoFichaPreparecao.salvar(fichaPreparacao);

				connection.commit();

			} else if (fichaPreparacao.getfBase64() == null && desenho.getdBase64() != null) {
				if (rs.next()) {
					desenho.getOperacao_id().setId(rs.getLong("id"));
				}

				daoDesenho.salvar(desenho);

				connection.commit();

			} else {
				connection.commit();
			}

		} catch (SQLException e) {
			e.printStackTrace();

			try {
				connection.rollback();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<BeanProduto> listaProduto() throws Exception {
		List<BeanProduto> retorno = new ArrayList<BeanProduto>();

		String sql = "select * from produto";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			BeanProduto produto = new BeanProduto();
			produto.setId(resultSet.getLong("id"));
			produto.setPn(resultSet.getString("pn"));
			produto.setCliente(resultSet.getString("cliente"));
			produto.setDescricao(resultSet.getString("descricao"));

			retorno.add(produto);
		}

		return retorno;

	}

	public List<BeanOperacao> listar() throws Exception {
		List<BeanOperacao> lista = new ArrayList<BeanOperacao>();

		String sql = "select op.id, op.descricao, op.maquina, op.n_operacao, pr.pn \n" + 
				"	from operacao op left outer join produto pr on op.produto_id = pr.id\n" + 
				"	order by pr.pn, op.n_operacao";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			BeanOperacao operacao = new BeanOperacao();
			operacao.setId(resultSet.getLong("id"));
			operacao.setDescricao(resultSet.getString("descricao"));
			operacao.setMaquina(resultSet.getString("maquina"));
			operacao.setN_operacao(resultSet.getInt("n_operacao"));
			operacao.getProduto_id().setPn(resultSet.getString("pn"));

			lista.add(operacao);

		}

		return lista;
	}

	public BeanOperacao consultar(String id) throws Exception { // consulta para atualiza√ß√£o

		String sql = ("select * from operacao where id ='" + id + "'");

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			BeanOperacao operacao = new BeanOperacao();
			operacao.setId(resultSet.getLong("id"));
			operacao.setDescricao(resultSet.getString("descricao"));
			operacao.setMaquina(resultSet.getString("maquina"));
			operacao.setTempo_estimado(resultSet.getTime("tempo_estimado"));
			operacao.setN_operacao(resultSet.getInt("n_operacao"));
			operacao.getProduto_id().setId(resultSet.getLong("produto_id"));

			return operacao;
		}
		return null;
	}
	
	public List<BeanOperacao> listaOperacoes(Long produtoId) throws Exception { // consulta para atualiza√ß√£o

		String sql = ("select * from operacao where produto_id ='" + produtoId + "'");
		List<BeanOperacao> operacoes = new ArrayList<BeanOperacao>();

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while(resultSet.next()) {
			BeanOperacao operacao = new BeanOperacao();
			operacao.setId(resultSet.getLong("id"));
			operacao.setDescricao(resultSet.getString("descricao"));
			operacao.setMaquina(resultSet.getString("maquina"));
			operacao.setTempo_estimado(resultSet.getTime("tempo_estimado"));
			operacao.setN_operacao(resultSet.getInt("n_operacao"));
			operacao.getProduto_id().setId(resultSet.getLong("produto_id"));
			
			operacoes.add(operacao);

		}
		return operacoes;
	}
	
	
	/**Consulta prÛxima operaÁ„o do produto**/
	public Long consultarProximaOp(Long produtoId, Long idOperacaoAtual) throws SQLException {
		String sql = "select id from operacao where produto_id = '" +produtoId+ "' and id > '" +idOperacaoAtual+ "' limit 1;";
		
		PreparedStatement preparedStatement =connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		Long idOperacaoAtualizada = null;
		
		if (resultSet.next()) {
			idOperacaoAtualizada = resultSet.getLong("id");
		}
		
		return idOperacaoAtualizada;
	};
	
	

	public void atualizar(BeanOperacao operacao) {
		String sql = "update operacao set descricao = ?, maquina = ?,  n_operacao = ?, tempo_estimado = ?, produto_id = ? where id = "
				+ operacao.getId();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, operacao.getDescricao());
			statement.setString(2, operacao.getMaquina());
			statement.setInt(3, operacao.getN_operacao());
			statement.setTime(4, operacao.getTempo_estimado());
			statement.setLong(5, operacao.getProduto_id().getId());

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
			String sqlOperacao = "delete from operacao where id = '" + id + "'";
			
			DaoFichaPreparecao daoFicha = new DaoFichaPreparecao();
			DaoDesenho daoDesenho = new DaoDesenho();
			
			daoDesenho.delete(id);
			daoFicha.delete(id);

			PreparedStatement statement = connection.prepareStatement(sqlOperacao);
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
