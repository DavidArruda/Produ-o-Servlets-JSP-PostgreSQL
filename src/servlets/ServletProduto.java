package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanProduto;
import daos.DaoProduto;

@WebServlet("/salvarProduto")
public class ServletProduto extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ServletProduto() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "listarTodos";
			String produto = request.getParameter("produto");

			DaoProduto daoProduto = new DaoProduto();

			RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");

			if (acao.equalsIgnoreCase("delete")) {
				daoProduto.delete(produto);
				request.setAttribute("produtos", daoProduto.listar());

			} else if (acao.equalsIgnoreCase("editar")) {
				BeanProduto beanproduto = daoProduto.consultar(produto);
				request.setAttribute("produto", beanproduto);

			} else if (acao.equalsIgnoreCase("listartodos")) {
				request.setAttribute("produtos", daoProduto.listar());
			}

			view.forward(request, response); // Confirmação do redirecionamento

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		DaoProduto daoProduto = new DaoProduto();

		if (acao != null && acao.equalsIgnoreCase("reset")) {

			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
				// request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			String id = request.getParameter("id");
			String pn = request.getParameter("pn");
			String cliente = request.getParameter("cliente");
			String descricao = request.getParameter("descricao");

			BeanProduto produto = new BeanProduto();
			produto.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			produto.setPn(pn);
			produto.setCliente(cliente);
			produto.setDescricao(descricao);

			try {
				// VALIDA SE OS CAMPOS EST�O PREENCHIDOS
				if (pn == null || pn.isEmpty()) {
					request.setAttribute("msg", "Campo PN está vazio");
					request.setAttribute("produto", produto);

				} else if (cliente.isEmpty()) {
					request.setAttribute("msg", "Campo Cliente está vazio");
					request.setAttribute("produto", produto);

				} else if (descricao.isEmpty()) {
					request.setAttribute("msg", "Campo descrição está vazio");
					request.setAttribute("produto", produto);

				} else if (id == null || id.isEmpty()) { // VERIFICA ID E SALVA PRODUTO
					daoProduto.salvar(produto);

					// } else if (codProduto == null || codProduto.isEmpty() &&
					// !daoProduto.validarNome(nome)) { // VALIDA NOME E SET MENSAGEM
					// PARA O USUARIO
					// request.setAttribute("msg", "Produto já existe com o mesmo nome");
					// request.setAttribute("produto", produto);

				} else { // ATUALIZA REGISTRO
					daoProduto.atualizar(produto);
				}

				// REDIRECIONA A P�GINA E LISTA OS PRODUTOS NOVAMENTE
				RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
