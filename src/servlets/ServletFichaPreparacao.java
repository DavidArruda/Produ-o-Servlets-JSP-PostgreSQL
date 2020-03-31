package servlets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanFichaPreparacao;
import beans.BeanOperacao;
import daos.DaoFichaPreparecao;
import daos.DaoOperacao;

@MultipartConfig
@WebServlet("/salvarFicha")
public class ServletFichaPreparacao extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoFichaPreparecao daoFichaPreparacao = new DaoFichaPreparecao();
	private DaoOperacao daoOperacao = new DaoOperacao();

	public ServletFichaPreparacao() {
		super();

	}

	@SuppressWarnings("static-access")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao");
			String op = request.getParameter("operacao");
			String fichaPreparacao = request.getParameter("fichaPreparacao");

			RequestDispatcher view = request.getRequestDispatcher("/cadastroFichaPreparacao.jsp");

			if (op != null) {
				BeanOperacao operacao = daoOperacao.consultar(op);

				if (acao.equalsIgnoreCase("addFicha")) {

					request.getSession().setAttribute("fichaEscolhida", operacao);
					request.setAttribute("fichaEscolhida", operacao);
					request.setAttribute("fichas", daoFichaPreparacao.listar());
					view.forward(request, response);

				} else if (acao.equalsIgnoreCase("delete")) {
					String codFicha = request.getParameter("codFicha");

					daoFichaPreparacao.delete(codFicha);

					//BeanOperacao beanOperacao = (BeanOperacao) request.getSession().getAttribute("fichaEscolhida");

					request.setAttribute("fichas", daoFichaPreparacao.listar());
					request.setAttribute("msg", "Removido");
					view.forward(request, response);

				} else if (acao != null && acao.equalsIgnoreCase("download")) {

					BeanFichaPreparacao ficha = daoFichaPreparacao.consultar(fichaPreparacao);

					if (ficha != null) {

						String contentType = "";
						byte[] fileBytes = null;

						contentType = ficha.getfContentType();
						fileBytes = new Base64().decodeBase64(ficha.getfBase64()); // converte a base64 do pdf para
																					// byte[]

						response.setHeader("Content-Disposition",
								"attachment;filename=arquivo." + contentType.split("\\/")[1]);

						// Coloca os bytes em um objeto de entrada para processar
						InputStream is = new ByteArrayInputStream(fileBytes);

						// resposta ao navegador
						int read = 0;
						byte[] bytes = new byte[1024];
						OutputStream os = response.getOutputStream();

						while ((read = is.read(bytes)) != -1) {
							os.write(bytes, 0, read);
						}

						os.flush();
						os.close();
					}
				}

			} else {
				request.setAttribute("fichas", daoFichaPreparacao.listar());
				view.forward(request, response);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {

			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroFichaPreparacao.jsp");
				request.setAttribute("fichas", daoFichaPreparacao.listar());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			try {

				// Processa ficha

				String codFicha = request.getParameter("codFicha");

				RequestDispatcher view = request.getRequestDispatcher("/cadastroFichaPreparacao.jsp");

				BeanFichaPreparacao ficha = new BeanFichaPreparacao();
				ficha.setId(!codFicha.isEmpty() ? Long.parseLong(codFicha) : null);

				Part fichaPreparacao = request.getPart("fichaPreparacao");

				if (fichaPreparacao != null && fichaPreparacao.getInputStream().available() > 0) {
					@SuppressWarnings("static-access")
					String fBase64 = new Base64()
							.encodeBase64String(convertStreamParByte(fichaPreparacao.getInputStream()));

					ficha.setfBase64(fBase64);
					ficha.setfContentType(fichaPreparacao.getContentType());

				} else {
					ficha.setAtualizar(false);
				}

				if (codFicha == null || codFicha.isEmpty()) { // SALVA NOVO USUARIO
					daoFichaPreparacao.salvar(ficha);

				} else {
					daoFichaPreparacao.atualizar(ficha);
				}

				request.setAttribute("fichas", daoFichaPreparacao.listar());
				view.forward(request, response);

			} catch (Exception e) {

			}

		}
	}

	// Converte a entrada de fluxo de dados da imagem para byte[]
	private static byte[] convertStreamParByte(InputStream imagem) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int reads = imagem.read();

		while (reads != -1) { // enquanto tiver dados em reads
			baos.write(reads);
			reads = imagem.read();
		}

		return baos.toByteArray();

	}

}
