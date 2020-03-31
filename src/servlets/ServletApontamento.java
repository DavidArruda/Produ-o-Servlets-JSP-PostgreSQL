package servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanApontamento;
import beans.BeanOperacao;
import beans.BeanOrdemServico;
import beans.StatusOrdemServico;
import daos.DaoApontamento;
import daos.DaoOrdemServico;

/**
 * Servlet implementation class ServletApontamento
 */
@WebServlet("/salvarApontamento")
public class ServletApontamento extends HttpServlet {
	private static final long serialVersionUID = 1L;

	BeanApontamento apontamento = new BeanApontamento();
	DaoApontamento daoApontamento = new DaoApontamento();
	DaoOrdemServico daoOrdemServico = new DaoOrdemServico();
	
	public ServletApontamento() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String acao = request.getParameter("acao");
			String ordemServico = request.getParameter("os");
			String operacaoAtual = request.getParameter("operacaoAtual");
			
			RequestDispatcher view = request.getRequestDispatcher("/cadastroApontamento.jsp");
			
			if (acao.isEmpty() || acao == null) {
				request.setAttribute("apontamento", apontamento);
			}

			if (acao.equalsIgnoreCase("apontar")) {

				BeanOrdemServico os = new BeanOrdemServico();
				os = daoOrdemServico.consultar(ordemServico);

				BeanOperacao operacao = new BeanOperacao();
				operacao.setId(Long.parseLong(operacaoAtual));

				BeanApontamento apontamento = new BeanApontamento();
				
				apontamento.setOrdemServico(os);
				apontamento.setOperacao(operacao);
				
				request.setAttribute("apontamento", apontamento);
			}
			
			view.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {

			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroApontamento.jsp");
				// request.setAttribute("usuarios", daoUsuario.listar());
				// request.setAttribute("cargos", daoUsuario.listaTipoUsuario());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			try {
				String id = request.getParameter("id");
				String stringDataAp = request.getParameter("dataAp");
				String stringInicio = request.getParameter("inicio");
				String stringTermino = request.getParameter("termino");
				String usuario = request.getParameter("usuario");
				String qtdProduzida = request.getParameter("quantidade");
				String ordemServicoId = request.getParameter("ordemServico");
				String operacaoId = request.getParameter("operacao");
				String qtdOs = request.getParameter("qtdOs");
				String produto = request.getParameter("produto");


				apontamento.setId((!id.isEmpty() ? Long.parseLong(id) : null));
				
				DateFormat formato = new SimpleDateFormat("HH:mm");
				DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
				
				Time inicio = new java.sql.Time(formato.parse(stringInicio).getTime());
				Time termino =  new java.sql.Time(formato.parse(stringTermino).getTime());
				
				Date dataAp = new java.sql.Date(formatoData.parse(stringDataAp).getTime());
				
				apontamento.setInicio(inicio);
				apontamento.setTermino(termino);
				apontamento.setDataAp(dataAp);
				apontamento.setQuantidade(Integer.parseInt(qtdProduzida));
				apontamento.getUsuario().setCodUsuario(Long.parseLong(usuario));
				apontamento.getOrdemServico().setId(Long.parseLong(ordemServicoId));
				apontamento.getOperacao().setId(Long.parseLong(operacaoId));
				apontamento.getOrdemServico().setQuantidade(Integer.parseInt(qtdOs));
				apontamento.getOrdemServico().getProdutoId().setId(Long.parseLong(produto));
				
				
				StatusOrdemServico staOs = new StatusOrdemServico();
				staOs.setOperacao(Long.parseLong(operacaoId));
				staOs.setOrdemServico(Long.parseLong(ordemServicoId));
				
				List<StatusOrdemServico>listaStaOs = new ArrayList<>();
				listaStaOs.add(staOs);
				
				apontamento.getOrdemServico().setStatusOrdemServicos(listaStaOs);
				
				// VALIDA SE OS CAMPOS ESTÃƒO PREENCHIDOS
				if (stringDataAp == null || stringDataAp.isEmpty()) {
					request.setAttribute("msg", "Campo Data está vazio");
					request.setAttribute("apontamento", apontamento);
					
				} else if (stringTermino == null || stringTermino.isEmpty()) {
					request.setAttribute("msg", "Campo termino está vazio");
					request.setAttribute("apontamento", apontamento);

				} else if (stringTermino == null || stringTermino.isEmpty()) {
					request.setAttribute("msg", "Campo termino está vazio");
					request.setAttribute("apontamento", apontamento);

				} else if (usuario.isEmpty()) {
					request.setAttribute("msg", "Campo usuário está vazio");
					request.setAttribute("apontamento", apontamento);

				} else if (qtdProduzida.isEmpty()) {
					request.setAttribute("msg", "Campo Qtd produzidia está vazio");
					request.setAttribute("apontamento", apontamento);

				} else if (id == null || id.isEmpty()) { // VERIFICA ID E SALVA PRODUTO
					
					
					daoApontamento.salvar(apontamento);

				}//else { // ATUALIZA REGISTRO
					// daoUsuario.atualizar(usuario);
				//}

				// REDIRECIONA A PÁGINA E LISTA OS APONTAMENTOS ATUALIZADOS
				RequestDispatcher view = request.getRequestDispatcher("/cadastroApontamento.jsp");
				List<BeanApontamento> apontamentos = new ArrayList<>();
				apontamentos.add(apontamento);
				request.setAttribute("apontamentos", apontamentos);
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
