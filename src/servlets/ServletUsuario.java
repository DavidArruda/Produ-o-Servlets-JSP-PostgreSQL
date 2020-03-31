package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanUsuario;
import daos.DaoUsuario;



@WebServlet("/salvarUsuario")
public class ServletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletUsuario() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {

			String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "listarTodos";
			String usuario = request.getParameter("usuario");

			DaoUsuario daoUsuario = new DaoUsuario();

			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");

			if (acao.equalsIgnoreCase("delete")) {
				daoUsuario.delete(usuario);
				request.setAttribute("usuarios", daoUsuario.listar());

			} else if (acao.equalsIgnoreCase("editar")) {
				BeanUsuario beanUsuario = daoUsuario.consultar(usuario);
				request.setAttribute("usuario", beanUsuario);

			} else if (acao.equalsIgnoreCase("listartodos")) {
				request.setAttribute("usuarios", daoUsuario.listar());
			}
			
			request.setAttribute("cargos", daoUsuario.listaTipoUsuario());
			view.forward(request, response); // ConfirmaÃ§Ã£o do redirecionamento

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String acao = request.getParameter("acao");

		DaoUsuario daoUsuario = new DaoUsuario();

		if (acao != null && acao.equalsIgnoreCase("reset")) {

			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				 request.setAttribute("usuarios", daoUsuario.listar());
				 request.setAttribute("cargos", daoUsuario.listaTipoUsuario());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			String codUsuario = request.getParameter("codUsuario");
			String nome = request.getParameter("nome");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String tipo_usuario = request.getParameter("tipo_usuario");
			

			BeanUsuario usuario = new BeanUsuario();
			usuario.setCodUsuario(!codUsuario.isEmpty() ? Long.parseLong(codUsuario) : null);
			usuario.setNome(nome);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.getTipo_usuario().setCodTipo(Long.parseLong(tipo_usuario));

			try {
				// VALIDA SE OS CAMPOS ESTÃƒO PREENCHIDOS
				if (nome == null || nome.isEmpty()) {
					request.setAttribute("msg", "Campo Nome estÃ¡ vazio");
					request.setAttribute("usuario", usuario);

				} else if (login.isEmpty()) {
					request.setAttribute("msg", "Campo Login estÃ¡ vazio");
					request.setAttribute("usuario", usuario);

				} else if (senha.isEmpty()) {
					request.setAttribute("msg", "Campo senha estÃ¡ vazio");
					request.setAttribute("usuario", usuario);

				} else if (codUsuario == null || codUsuario.isEmpty()) { // VERIFICA ID E SALVA PRODUTO
					daoUsuario.salvar(usuario);

				} else { // ATUALIZA REGISTRO
					daoUsuario.atualizar(usuario);
				}

				// REDIRECIONA A PÃ�GINA E LISTA OS PRODUTOS NOVAMENTE
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				request.setAttribute("cargos", daoUsuario.listaTipoUsuario());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
	}

}
