package servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanDesenho;
import beans.BeanFichaPreparacao;
import beans.BeanOperacao;
import daos.DaoDesenho;
import daos.DaoFichaPreparecao;
import daos.DaoOperacao;
import daos.DaoProduto;

@WebServlet("/salvarOperacao")
@MultipartConfig
public class ServletOperacao extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DaoOperacao daoOperacao = new DaoOperacao();
	DaoProduto daoProduto = new DaoProduto();
	DaoDesenho daoDesenho = new DaoDesenho();
	DaoFichaPreparecao daoFichaPreparecao = new DaoFichaPreparecao();

	public ServletOperacao() {
		super();

	}

	@SuppressWarnings("static-access")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "listarTodos";
			String operacao = request.getParameter("operacao");

			RequestDispatcher view = request.getRequestDispatcher("/cadastroOperacao.jsp");

			if (acao.equalsIgnoreCase("delete")) {
				daoOperacao.delete(operacao);
				request.setAttribute("operacoes", daoOperacao.listar());
				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("editar")) {
				BeanOperacao beanOperacao = daoOperacao.consultar(operacao);
				request.setAttribute("operacao", beanOperacao);
				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("listartodos")) {
				request.setAttribute("operacoes", daoOperacao.listar());
				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);
				
			} else if (acao != null && acao.equalsIgnoreCase("download")) {
				BeanDesenho bBesenho = daoDesenho.consultarPorOperacao(operacao);
				BeanFichaPreparacao bFicha = daoFichaPreparecao.consultarPorOperacao(operacao);

				if (bBesenho != null) {

					String contentType = "";
					byte[] fileBytes = null;

					String tipo = request.getParameter("tipo");

					if (tipo.equalsIgnoreCase("desenho")) {
						contentType = bBesenho.getfContentType();
						fileBytes = new Base64().decodeBase64(bBesenho.getdBase64()); // converte a base64 da imagem do
																						// banco para byte[]

					} else if (tipo.equalsIgnoreCase("ficha")) {
						contentType = bFicha.getfContentType();
						fileBytes = new Base64().decodeBase64(bFicha.getfBase64()); // converte a
						// base64 do pdf do banco para byte[]
					}

					response.setHeader("Content-Disposition",
							"attachment;filename=arquivo." + contentType.split("\\/")[1]);

					// Coloca os bytes em objeto de entrada para processar
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

			} else {

				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response); // Confirmação do redirecionamento
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BeanFichaPreparacao ficha = new BeanFichaPreparacao();
		BeanDesenho bDesenho = new BeanDesenho();
		BeanOperacao operacao = new BeanOperacao();

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {

			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroOperacao.jsp");
				request.setAttribute("cargos", daoProduto.listar());
				request.setAttribute("produtos", daoProduto.listar());
				request.setAttribute("operacoes", daoOperacao.listar());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			try {

				String id = request.getParameter("id");
				String descricao = request.getParameter("descricao");
				String maquina = request.getParameter("maquina");
				String n_operacao = request.getParameter("n_operacao");
				String produto_id = request.getParameter("produto_id");

				String string_tempo_estimado = request.getParameter("tempo_estimado");
				DateFormat formato = new SimpleDateFormat("HH:mm");
				Time tempo_estimado = new java.sql.Time(formato.parse(string_tempo_estimado).getTime());

				operacao.setId(!id.isEmpty() ? Long.parseLong(id) : null);
				operacao.setDescricao(descricao);
				operacao.setMaquina(maquina);
				operacao.setTempo_estimado(tempo_estimado);
				operacao.setN_operacao(Integer.parseInt(n_operacao));
				operacao.getProduto_id().setId(Long.parseLong(produto_id));

				// file upload de imagens e pdf

				if (ServletFileUpload.isMultipartContent(request)) {

					Part desenho = request.getPart("desenho");

					if (desenho != null && desenho.getInputStream().available() > 0) {
						String desenhoBase64 = new Base64()
								.encodeBase64String(convertStreamParByte(desenho.getInputStream()));
						bDesenho.setdBase64(desenhoBase64);
						bDesenho.setfContentType(desenho.getContentType());

						// Inicio miniatura imagem

						/* Transformar em buferred image */
						byte[] imageByteDecode = new Base64().decodeBase64(desenhoBase64);
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));

						// Pegar o tipo da imagem
						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

						// Criar imagem em miniatura
						BufferedImage resizedImage = new BufferedImage(100, 100, type);
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, 100, 100, null);
						g.dispose(); /* finaliza processo */

						// Escrever imagem novamente
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "png", baos);

						String miniaturaBase64 = "data:image/png;base64,"
								+ DatatypeConverter.printBase64Binary(baos.toByteArray());

						bDesenho.setdBase64Miniatura(miniaturaBase64);
					}
					// Fim miniatura imagem

					// PROCESSA FICHA DE PREPARA��O
					Part fichaPreparacao = request.getPart("ficha_preparacao");

					if (fichaPreparacao != null && fichaPreparacao.getInputStream().available() > 0) {

						String curriculoBase64 = new Base64()
								.encodeBase64String(convertStreamParByte(fichaPreparacao.getInputStream()));

						ficha.setfBase64(curriculoBase64);
						ficha.setfContentType(fichaPreparacao.getContentType());
					}
				}
				// fim upload

				// VALIDA SE OS CAMPOS EST�O PREENCHIDOS
				if (n_operacao == null || n_operacao.isEmpty()) {
					request.setAttribute("msg", "Campo Nº está vazio");
					request.setAttribute("operacao", operacao);

				} else if (produto_id.isEmpty()) {
					request.setAttribute("msg", "Campo Produto está vazio");
					request.setAttribute("operacao", operacao);

				} else if (id == null || id.isEmpty()) { // VERIFICA ID E SALVA PRODUTO
					ficha.setOperacao_id(operacao);
					bDesenho.setOperacao_id(operacao);
					daoOperacao.salvar(operacao, ficha, bDesenho);

				} else { // ATUALIZA REGISTRO
					daoOperacao.atualizar(operacao);

					ficha.setOperacao_id(operacao);
					daoFichaPreparecao.salvar(ficha);

					bDesenho.setOperacao_id(operacao);
					daoDesenho.salvar(bDesenho);
				}

				// REDIRECIONA A P�GINA E LISTA OS PRODUTOS NOVAMENTE
				RequestDispatcher view = request.getRequestDispatcher("/cadastroOperacao.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				request.setAttribute("operacoes", daoOperacao.listar());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
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
