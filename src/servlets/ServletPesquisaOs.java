package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.StatusOrdemServico;
import daos.DaoStatusOrdemServico;

/**
 * Servlet implementation class ServletPesquisaOs
 */
@WebServlet("/servletPesquisaOs")
public class ServletPesquisaOs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoStatusOrdemServico daoStatusOrdemServico = new DaoStatusOrdemServico();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ordem_servico_id = request.getParameter("os");

		if (ordem_servico_id != null && !ordem_servico_id.trim().isEmpty()) { // TRIM: RETIRA OS ESPAÇOS DA STRING
			try {
				List<StatusOrdemServico> listaStatusOs = daoStatusOrdemServico.consultarPorId(Long.parseLong(ordem_servico_id));

				// REDIRECIONA A PÁGINA E LISTA OS STATUS
				RequestDispatcher view = request.getRequestDispatcher("/cadastroApontamento.jsp");
				request.setAttribute("statusOs", listaStatusOs);
				view.forward(request, response);

			} catch (SQLException e) {

				e.printStackTrace();
			}

		} else {
			// REDIRECIONA A P�GINA E LISTA OS USUARIOS NOVAMENTE
			RequestDispatcher view = request.getRequestDispatcher("/cadastroApontamento.jsp");
			view.forward(request, response);
		}

	}

}
