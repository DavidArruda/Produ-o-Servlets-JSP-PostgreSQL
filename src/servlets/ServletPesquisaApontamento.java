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

import beans.BeanApontamento;
import daos.DaoApontamento;

@WebServlet("/servletPesquisaApontamento")
public class ServletPesquisaApontamento extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoApontamento daoApontamento = new DaoApontamento();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ordemServicoId = request.getParameter("os");

		if (ordemServicoId != null && !ordemServicoId.trim().isEmpty()) { // TRIM: RETIRA OS ESPAÇOS DA STRING
			try {
				List<BeanApontamento> apontamentos = daoApontamento.consultaPorId(ordemServicoId);

				// REDIRECIONA A PÁGINA E LISTA OS STATUS
				RequestDispatcher view = request.getRequestDispatcher("/cadastroApontamento.jsp");
				request.setAttribute("apontamentos", apontamentos);
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
